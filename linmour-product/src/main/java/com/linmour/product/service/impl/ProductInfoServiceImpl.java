package com.linmour.product.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.LoginUser;
import com.linmour.common.dtos.PageResult;
import com.linmour.common.dtos.Result;
import com.linmour.common.exception.CustomException;
import com.linmour.common.exception.enums.AppHttpCodeEnum;
import com.linmour.common.service.FileStorageService;
import com.linmour.product.convert.ProductDetailDtoConvert;
import com.linmour.product.convert.ProductInfoConvert;
import com.linmour.product.convert.ProductInfoPageDtoConvert;
import com.linmour.product.mapper.*;
import com.linmour.product.pojo.Do.*;
import com.linmour.product.pojo.Dto.*;
import com.linmour.product.service.ProductInfoService;
import com.linmour.product.service.RProductNonValueSpecService;
import com.linmour.product.service.RProductValueSpecService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private FileStorageService fileStorageService;
    @Resource
    private NonValueSpecMapper nonValueSpecMapper;
    @Resource
    private ProductSortMapper productSortMapper;
    @Resource
    private ProductSpecMapper productSpecMapper;
    @Resource
    private RProductNonValueSpecMapper rProductNonValueSpecMapper;
    @Resource
    private RProductValueSpecMapper rProductValueSpecMapper;
    @Resource
    private SpecSortMapper specSortMapper;
    @Resource
    private ValueSpecMapper valueSpecMapper;
    @Resource
    private RProductNonValueSpecService rProductNonValueSpecService;
    @Resource
    private RProductValueSpecService rProductValueSpecService;

    @Override
    public Result getProductList(ProductInfoPageDto dto) {
        List<ProductInfo> productInfos = productInfoMapper.selectList(new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getShopId, dto.getShopId())
                .eq(!ObjectUtil.isNull(dto.getSortId()), ProductInfo::getSortId, dto.getSortId()));
        //因为前台的缘故，第一次没传sort值，所以要默认拿到第一个sort
        if (dto.getSortId() == null && productInfos.size() > 0) {
            Long sortId1 = productInfos.get(0).getSortId();
            dto.setSortId(sortId1);
        }
        //这个是新店没有分类会报空指针
        if (dto.getSortId() == null) {
            return Result.success();

        }

        Page<ProductInfo> productInfoPage = page(new Page<ProductInfo>(dto.getPageNo(), dto.getPageSize()), new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getShopId, dto.getShopId())
                .eq(StringUtils.isNotBlank(dto.getSortId().toString()), ProductInfo::getSortId, dto.getSortId())
        );
        if (ObjectUtil.isNull(productInfoPage.getRecords())) {
            throw new CustomException(AppHttpCodeEnum.PRODUCT_ERROR);
        }
        List<ProductInfoPageDto> productInfoPageDtos = ProductInfoPageDtoConvert.INSTANCE.ProductInfoToProductInfoPageDto(productInfoPage.getRecords());
        return Result.success(new PageResult<>(productInfoPageDtos, productInfoPage.getTotal()));
    }

    @Override
    public Result changeProduct(ProductInfoPageDto productInfo) {
        ProductInfo productInfo1 = ProductInfoConvert.IN.ProductInfoPageDtoToProductInfo(productInfo);
        productInfoMapper.updateById(productInfo1);
        return Result.success();
    }

    @Override
    public Result uploadProductImg(MultipartFile[] file, String shopId) {
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        List<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String fileName = id.toString() + "/" + shopId + "/" + UUID.randomUUID().toString().replace("-", "");
            String url = fileStorageService.uploadPicture(multipartFile, "product", fileName);
            list.add(url);
        }
        return Result.success(list);
    }

    @Override
    public Result getProductDetails(Long productId) {
        //商品基本信息
        ProductInfo productInfo = productInfoMapper.selectOne(new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getId, productId));
        ProductDetailDto productDetailDto = ProductDetailDtoConvert.IN.ProductInfoToProductDetailDto(productInfo);
        String sort = productInfoMapper.getSort(productId);
        if (productInfo.getSpecId() != 0) {
            ProductSpec productSpec = productSpecMapper.selectOne(new LambdaQueryWrapper<ProductSpec>().eq(ProductSpec::getId, productInfo.getSpecId()));
            if (productSpec.getValueSpec() == 1) {
                List<Long> valueId = rProductValueSpecService.getNonValueId(productInfo.getSpecId());
                List<ValueSpec> valueSpecs = valueSpecMapper.selectBatchIds(valueId);
                List<Long> sortIdList = valueSpecs.stream().map(ValueSpec::getSortId).distinct().collect(Collectors.toList());
                List<ValueDto> valueDtoList = new ArrayList<>();
                sortIdList.forEach(m -> {
                    String name = specSortMapper.selectOne(new LambdaQueryWrapper<SpecSort>().eq(SpecSort::getId, m)).getName();
                    ValueDto valueDto = new ValueDto();
                    ArrayList<String> spec = new ArrayList<>();
                    ArrayList<BigDecimal> price = new ArrayList<>();
                    valueSpecs.stream().filter(o -> {
                        return o.getSortId() == m;
                    }).collect(Collectors.toList()).forEach(p -> {
                        spec.add(p.getName());
                        price.add(p.getPrice());
                    });

                    valueDto.setSpec(spec);
                    valueDto.setSort(name);
                    valueDto.setPrice(price);
                    valueDtoList.add(valueDto);
                });
                productDetailDto.setValueList(valueDtoList);
            }
            if (productSpec.getNonValueSpec() == 1) {
                List<Long> nonValueId = rProductNonValueSpecService.getNonValueId(productInfo.getSpecId());
                List<NonValueSpec> nonValueSpecs = nonValueSpecMapper.selectBatchIds(nonValueId);
                List<Long> sortIdList = nonValueSpecs.stream().map(NonValueSpec::getSortId).distinct().collect(Collectors.toList());
                List<NonValueDto> nonValueDtoList = new ArrayList<>();
                sortIdList.forEach(m -> {
                    String name = specSortMapper.selectOne(new LambdaQueryWrapper<SpecSort>().eq(SpecSort::getId, m)).getName();
                    NonValueDto nonValueDto = new NonValueDto();

                    ArrayList<String> list = new ArrayList<>();
                    nonValueSpecs.stream().filter(o -> {
                        return o.getSortId() == m;
                    }).collect(Collectors.toList()).forEach(p -> {
                        list.add(p.getName());
                    });

                    nonValueDto.setSpec(list);
                    nonValueDto.setSort(name);
                    nonValueDtoList.add(nonValueDto);
                });
                productDetailDto.setNonValueList(nonValueDtoList);
            }
        }

        productDetailDto.setSort(sort);
        return Result.success(productDetailDto);
    }

    @Override
    public Result addProduct(AddProductDto product) {
        ProductInfo productInfo = ProductInfoConvert.IN.AddProductDtoToProductInfo(product);
        //图片
        String pictureList = "";
        for (String s : product.getUrlList()) {
            pictureList = pictureList + s + ",";
        }
        if (pictureList.endsWith(",")) {
            pictureList = pictureList.substring(0, pictureList.length() - 1);
        }
        productInfo.setPicture(pictureList);
        if (!(product.getNonValueList().isEmpty() && product.getValueList().isEmpty())) {
            //插入规格的方法
            Long productSpecId = productSpecMethod(product);
            productInfo.setSpecId(productSpecId);
            //todo  这里可以用NonValueDto对象来接收
            //普通选项
            List<Long> nonValueIds = nonValueMethod(product.getNonValueList());
            //价值选项
            List<Long> valueIds = valueMethod(product.getValueList(), productInfo);

            rProductSpec(nonValueIds, valueIds, productSpecId);
        }
        productInfoService.saveOrUpdate(productInfo);
        return Result.success();
    }

    //关系表
    private void rProductSpec(List<Long> nonValueIds, List<Long> valueIds, Long productSpecId) {
        for (Long nonValueId : nonValueIds) {
            RProductNonValueSpec rProductNonValueSpec = new RProductNonValueSpec();
            rProductNonValueSpec.setNonValueId(nonValueId);
            rProductNonValueSpec.setProductSpecId(productSpecId);
            rProductNonValueSpecMapper.insert(rProductNonValueSpec);
        }
        for (Long valueId : valueIds) {
            RProductValueSpec rProductValueSpec = new RProductValueSpec();
            rProductValueSpec.setProductSpecId(productSpecId);
            rProductValueSpec.setValueSpecId(valueId);
            rProductValueSpecMapper.insert(rProductValueSpec);
        }

    }

    private List<Long> valueMethod(List<HashMap> valueList, ProductInfo productInfo) {
        List<Long> valueIds = new ArrayList<>();
        //找出最小价钱
        valueList.stream().forEach(m -> {
            List list = getMap(m);
            Long specSortId = specSortMethod(m);
            for (int i = 0; i < ((List) list.get(0)).size(); i++) {
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(((List) list.get(0)).get(i).toString()));
                String spec = ((List) list.get(1)).get(i).toString();
                ValueSpec valueSpec = new ValueSpec();
                valueSpec.setSortId(specSortId);
                valueSpec.setName(spec);
                valueSpec.setPrice(price);
                valueSpecMapper.insert(valueSpec);
                valueIds.add(valueSpec.getId());
            }

        });
        return valueIds;

    }

    private List<List<String>> getMap(HashMap map) {
        List a = (ArrayList) map.get("price");
        List b = (ArrayList) map.get("spec");

        List<List<String>> array = new ArrayList<>();
        array.add(a);
        array.add(b);
        return array;
    }

    private List<Long> nonValueMethod(List<HashMap> nonValueList) {
        List<Long> nonValueIds = new ArrayList<>();

        nonValueList.stream().forEach(m -> {
            Long specSortId = specSortMethod(m);
            List list = (ArrayList) m.get("spec");
            for (Object o : list) {
                NonValueSpec nonValueSpec = new NonValueSpec();
                nonValueSpec.setSortId(specSortId);
                nonValueSpec.setName(o.toString());
                nonValueSpecMapper.insert(nonValueSpec);
                nonValueIds.add(nonValueSpec.getId());
            }
        });

        return nonValueIds;
    }

    private Long specSortMethod(Map map) {
        SpecSort specSort = new SpecSort();
        specSort.setName(map.get("sort").toString());
        specSortMapper.insert(specSort);
        return specSort.getId();
    }


    private Long productSpecMethod(AddProductDto product) {
        List nonValueList = product.getNonValueList();
        List valueList = product.getValueList();
        ProductSpec productSpec = new ProductSpec();
        if (!nonValueList.isEmpty()) {
            productSpec.setNonValueSpec(1);
        } else {
            productSpec.setNonValueSpec(0);
        }
        if (!valueList.isEmpty()) {
            productSpec.setValueSpec(1);
        }
        productSpecMapper.insert(productSpec);
        return productSpec.getId();
    }

    @Override
    public Result updateProduct(AddProductDto product) {
        Long productId = product.getId();
        ProductInfo productInfo = productInfoMapper.selectById(productId);
        Long productSpecId = productInfo.getSpecId();
        //判断原来是否有规格，如果没有就可以直接调用新增接口
        if (productSpecId == 0) {
            return addProduct(product);
        }
        //有规格把原来的关系表全删了，然后在用新增接口
        ProductSpec productSpec = productSpecMapper.selectById(productSpecId);

        if (productSpec.getValueSpec() == 1) {
            specSortMapper.deleteValue(productId);
            rProductValueSpecMapper.deleteValue(productId);
        }
        if (productSpec.getNonValueSpec() == 1) {
            specSortMapper.deleteNonValue(productId);
            rProductNonValueSpecMapper.deleteNonValue(productId);
        }

        //这个有的删除是把deleted变成1
        productSpecMapper.deleteById(productSpecId);
        product.setSpecId(0L);
        return addProduct(product);
    }
}




