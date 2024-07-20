package com.linmour.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.product.convert.ProductInfoConvert;
import com.linmour.product.mapper.*;
import com.linmour.product.pojo.Do.*;
import com.linmour.product.pojo.Dto.*;
import com.linmour.product.service.*;
import com.linmour.security.dtos.PageResult;
import com.linmour.security.dtos.Result;
import com.linmour.security.exception.CustomException;
import com.linmour.security.exception.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.linmour.security.dtos.Result.success;

/**
 * @author linmour
 * @description 针对表【product_info】的数据库操作Service实现
 * @createDate 2023-08-12 18:34:04
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo>
        implements ProductInfoService {

    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private ProductInventoryService productInventoryService;
    @Resource
    private ProductSpecSortMapper productSpecSortMapper;
    @Resource
    private ProductSpecSortService productSpecSortService;
    @Resource
    private ProductInventoryMapper productInventoryMapper;
    @Resource
    private ProductSortService productSortService;
    @Resource
    private ProductSortMapper productSortMapper;
    @Resource
    private ProductSpecOptionMapper productSpecOptionMapper;
    @Resource
    private ProductSpecOptionService productSpecOptionService;
    @Resource
    private RProductInventotyMapper rProductInventotyMapper;
    @Resource
    private RProductInventotyService rProductInventotyService;


    @Override
    public Result getProductPage(ProductInfoPageDto dto) {
        List<ProductInfo> productInfos = productInfoMapper.selectList(new LambdaQueryWrapper<ProductInfo>()
                .eq(!ObjectUtil.isNull(dto.getSortId()), ProductInfo::getSortId, dto.getSortId()));
        //因为前台的缘故，第一次没传sort值，所以要默认拿到第一个sort
        if (dto.getSortId() == null && productInfos.size() > 0)
            dto.setSortId(productInfos.get(0).getSortId());

        //这个是新店没有分类会报空指针
        if (dto.getSortId() == null)
            return success();
        List<ProductDetailDto> productDetails = getProductDetails(productInfos.stream().map(ProductInfo::getId).collect(Collectors.toList()));
        Page<ProductInfo> productInfoPage = page(new Page<ProductInfo>(dto.getPageNo(), dto.getPageSize()), new LambdaQueryWrapper<ProductInfo>()
                .eq(StringUtils.isNotBlank(dto.getSortId().toString()), ProductInfo::getSortId, dto.getSortId())
        );
        if (ObjectUtil.isNull(productInfoPage.getRecords()))
            throw new CustomException(AppHttpCodeEnum.PRODUCT_ERROR);


        return success(new PageResult<>(productDetails, productInfoPage.getTotal()));
    }

    @Override
    public void changeProduct(Long id, Integer status) {
        productInfoMapper.update(null, new LambdaUpdateWrapper<ProductInfo>().eq(ProductInfo::getId, id).set(ProductInfo::getStatus, status));
    }


    @Override
    public List<ProductDetailDto> getProductDetails(List<Long> productIds) {
        if (productIds.isEmpty())
            return new ArrayList<>();
        List<ProductInfo> productInfos = productInfoMapper.selectBatchIds(productIds);
        List<ProductDetailDto> productDetailDtos = ProductInfoConvert.IN.ProductInfoToProductDetailDto(productInfos);
        //查询规格分类
        List<ProductSpecSort> productSpecSorts = productSpecSortMapper.selectList(new LambdaQueryWrapper<ProductSpecSort>().in(productIds.size() > 0, ProductSpecSort::getProductId, productIds));
        List<Long> productSpecSortIds = productSpecSorts.stream().map(ProductSpecSort::getId).collect(Collectors.toList());
        //查询规格选项
        List<ProductSpecOption> productSpecOptions = productSpecOptionMapper.selectList(new LambdaQueryWrapper<ProductSpecOption>().in(productSpecSortIds.size() > 0, ProductSpecOption::getSpecSortId, productSpecSortIds));
        List<Long> sortIds = productInfos.stream().map(ProductInfo::getSortId).collect(Collectors.toList());
                //查出商品分类
        List<ProductSort> productSorts = productSortMapper.selectBatchIds(sortIds);
        productDetailDtos.forEach(m -> {
            //材料
            List<RProductInventoty> rProductInventoty = rProductInventotyMapper.selectList(new LambdaQueryWrapper<RProductInventoty>().in(RProductInventoty::getProductId,m.getId()));
            List<ProductInventoryAllDto> productInventoryAllDtos = BeanUtil.copyToList(rProductInventoty, ProductInventoryAllDto.class);
            if (!rProductInventoty.isEmpty()){
                List<Long> inventoryIds = rProductInventoty.stream().map(RProductInventoty::getInventoryId).collect(Collectors.toList());
                List<ProductInventory> productInventories = productInventoryMapper.selectList(new LambdaQueryWrapper<ProductInventory>().in(ProductInventory::getId, inventoryIds));
                productInventories.forEach(p ->{
                    productInventoryAllDtos.forEach(l ->{
                        if (p.getId().equals(l.getInventoryId())){
                            l.setName(p.getName());
                            l.setUnit(p.getUnit());
                        }
                    });
                });
                m.setInventoryAllList(productInventoryAllDtos);
            }

            List<ProductSortAndOption> productSortAndOptions = new ArrayList<>();
            productSorts.forEach(n -> {
                if (n.getId().equals(m.getSortId())) {
                    m.setSort(n.getSort());
                }
            });
            productSpecSorts.forEach(i -> {
                if (m.getId().equals(i.getProductId())) {
                    ProductSortAndOption productSortAndOption = new ProductSortAndOption();
                    productSortAndOption.setProductSpecSort(i);
                    List<ProductSpecOption> productSpecOption = new ArrayList<>();
                    productSpecOptions.forEach(j -> {
                        if (j.getSpecSortId().equals(i.getId())) {
                            productSpecOption.add(j);
                        }
                    });
                    productSortAndOption.setProductSpecOptions(productSpecOption);
                    productSortAndOptions.add(productSortAndOption);
                }
            });
            m.setProductSortAndOptions(productSortAndOptions);
        });


        return productDetailDtos;

    }

    @Override
    public void saveOrUpdateProduct(AddProductDto product) {
        ProductInfo productInfo = ProductInfoConvert.IN.AddProductDtoToProductInfo(product);
        productInfoService.saveOrUpdate(productInfo);
        List<ProductSortAndOption> specSortAndOption = product.getSpecSortAndOption();
        List<Long> collect;

        //库存关系更新
        rProductInventotyMapper.delete(new LambdaQueryWrapper<RProductInventoty>().eq(RProductInventoty::getProductId,productInfo.getId()));
        List<RProductInventoty> rProductInventoties = BeanUtil.copyToList(product.getInventoryAllList(), RProductInventoty.class);
        rProductInventotyService.saveOrUpdateBatch(rProductInventoties);

        if (!specSortAndOption.isEmpty()) {
            collect = specSortAndOption.stream()
                    .map(ProductSortAndOption::getProductSpecSort)
                    .map(ProductSpecSort::getId)
                    .collect(Collectors.toList());
        } else {
            collect = new ArrayList<>();
        }


        //这些是要删除的规格
        List<ProductSpecSort> productSpecSorts = productSpecSortMapper.selectList(new LambdaQueryWrapper<ProductSpecSort>().eq(ProductSpecSort::getProductId, productInfo.getId()));
        if (productSpecSorts.isEmpty())
            return;
        List<Long> specSortIds = productSpecSorts.stream().map(ProductSpecSort::getId).collect(Collectors.toList());
        List<Long> missingValues = specSortIds.stream()
                .filter(value -> !collect.contains(value))
                .collect(Collectors.toList());
        if (!missingValues.isEmpty()) {
            productSpecSortMapper.deleteBatchIds(missingValues);
            productSpecOptionMapper.delete(new LambdaQueryWrapper<ProductSpecOption>().in(ProductSpecOption::getSpecSortId, missingValues));
        }

        //插入新增的

        specSortAndOption.forEach(n -> {
            ProductSpecSort productSpecSort = n.getProductSpecSort();
            productSpecSort.setProductId(productInfo.getId());
            productSpecSortService.saveOrUpdate(productSpecSort);
            List<ProductSpecOption> productSpecOptions = n.getProductSpecOptions();
            productSpecOptions.forEach(m -> {
                m.setSpecSortId(productSpecSort.getId());
            });
            productSpecOptionService.saveOrUpdateBatch(productSpecOptions);
        });


    }


    @Override
    public List<AppProductSort> getProductList() {
        List<AppProductSort> sorts = ProductInfoConvert.IN.convert(productSortService.getProductSort());

        List<ProductDetailDto> productDetails = getProductDetails(productInfoMapper.selectList(new LambdaQueryWrapper<ProductInfo>().ne(ProductInfo::getStatus, 0)).stream()
                .map(ProductInfo::getId).collect(Collectors.toList()));
        sorts.forEach(m -> {
            List<ProductDetailDto> productDetailDtos = new ArrayList<>();
            productDetails.forEach(n -> {
                if (m.getId().equals(n.getSortId()))
                    productDetailDtos.add(n);
            });
            m.setList(productDetailDtos);

        });
        return sorts;
    }

    @Override
    public void deletedProduct(Long id) {
        productInfoMapper.deleteById(id);
    }
}



