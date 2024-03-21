package com.linmour.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.security.dtos.PageResult;
import com.linmour.security.dtos.Result;
import com.linmour.security.exception.CustomException;
import com.linmour.security.exception.enums.AppHttpCodeEnum;
import com.linmour.common.service.FileStorageService;
import com.linmour.product.convert.*;
import com.linmour.product.mapper.*;
import com.linmour.product.pojo.Do.*;
import com.linmour.product.pojo.Dto.*;
import com.linmour.product.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private FileStorageService fileStorageService;
    @Resource
    private NonValueSpecMapper nonValueSpecMapper;
    @Resource
    private ProductInventoryService productInventoryService;

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

    @Resource
    private ProductInventoryMapper productInventoryMapper;
@Resource
private ProductSortService productSortService;

    @Override
    public Result getProductPage(ProductInfoPageDto dto) {
        List<ProductInfo> productInfos = productInfoMapper.selectList(new LambdaQueryWrapper<ProductInfo>()
                .eq(!ObjectUtil.isNull(dto.getSortId()), ProductInfo::getSortId, dto.getSortId()));
        //因为前台的缘故，第一次没传sort值，所以要默认拿到第一个sort
        if (dto.getSortId() == null && productInfos.size() > 0) {
            Long sortId1 = productInfos.get(0).getSortId();
            dto.setSortId(sortId1);
        }
        //这个是新店没有分类会报空指针
        if (dto.getSortId() == null) {
            return success();

        }

        Page<ProductInfo> productInfoPage = page(new Page<ProductInfo>(dto.getPageNo(), dto.getPageSize()), new LambdaQueryWrapper<ProductInfo>()
                .eq(StringUtils.isNotBlank(dto.getSortId().toString()), ProductInfo::getSortId, dto.getSortId())
        );
        if (ObjectUtil.isNull(productInfoPage.getRecords())) {
            throw new CustomException(AppHttpCodeEnum.PRODUCT_ERROR);
        }
        List<ProductInfoPageDto> productInfoPageDtos = ProductInfoConvert.IN.ProductInfoToProductInfoPageDto(productInfoPage.getRecords());
        return success(new PageResult<>(productInfoPageDtos, productInfoPage.getTotal()));
    }

    @Override
    public void changeProduct(Long id, Integer status) {
        productInfoMapper.update(null, new LambdaUpdateWrapper<ProductInfo>().eq(ProductInfo::getId, id).set(ProductInfo::getStatus, status));
    }


    @Override
    public List<ProductDetailDto> getProductDetails(List<Long> productIds) {
        List<ProductInfo> productInfos = productInfoMapper.selectBatchIds(productIds);
        List<ProductDetailDto> productDetailDtos = ProductInfoConvert.IN.ProductInfoToProductDetailDto(productInfos);
        //拼接查询需要的参数
        StringBuilder stringBuilder = new StringBuilder();
        for (Long productId : productIds) {
            stringBuilder.append(productId).append(",");
        }
        String string = stringBuilder.toString();
        //库存的操作
        //这里用了两种处理方式，一个是拼接字符串变成in条件，一个是sql里循环
        List<ProductInventory> inventorys = productInventoryMapper.getInventory(string.substring(0, string.length() - 1));
        for (ProductDetailDto productDetailDto : productDetailDtos) {
            List<InventoryDto> inventoryDtos = new ArrayList<>();
            for (ProductInventory inventory : inventorys) {
                if (productDetailDto.getId() == inventory.getProductId()) {
                    inventoryDtos.add(ProductInfoConvert.IN.InventoryDtoToProductInventory(inventory));
                }
            }
            productDetailDto.setInventoryList(inventoryDtos);
        }

        List<Map<String, Object>> sorts = productInfoMapper.getSort(productIds);
        List<ProductInfo> productInfoSpec = productInfos.stream().filter(m -> (m.getNonValueSpec() != 0 && m.getValueSpec() != 0)).collect(Collectors.toList());
        for (ProductDetailDto productDetailDto : productDetailDtos) {
            Long dtoId = productDetailDto.getId();
            for (Map<String, Object> sort : sorts) {
                Long sortId = Long.parseLong(sort.get("id").toString());
                if (dtoId.equals(sortId)) {
                    productDetailDto.setSort(sort.get("sort").toString());
                }
            }
        }
        if (productInfoSpec.size() == 0) {
            return productDetailDtos;
        }
        /*
            [
                {"sort":"大小","spec":["份","小份"],"price":[5,3]},
                {"sort":"种类","spec":["乌鸡","普通鸡"],"price":[18,13]}
            ]
         */

        //价值选项的商品
        List<ProductInfo> valueSpecs = productInfos.stream().filter(m -> m.getValueSpec() == 1).collect(Collectors.toList());
        if (valueSpecs.size() > 0) {
            //每个商品自己的规格
            valueSpecs.stream().forEach(m -> {

                //找到自己所对应的规格选项
                List<Long> valueId = rProductValueSpecService.getValueId(m.getId());
                //商品的规格选项
                List<ValueSpec> valueSpecsList = new ArrayList<>();
                if (valueId.size() > 0)
                    valueSpecsList = valueSpecMapper.selectBatchIds(valueId);
                //按sort分类
                Map<Long, List<ValueSpec>> collect = valueSpecsList.stream().collect(Collectors.groupingBy(ValueSpec::getSortId));
                List<List<ValueSpec>> result = new ArrayList<>(collect.values());
                //这个就是上面的 例子 的数组
                List<ValueDto> valueList = new ArrayList<>();
                //同一分类的放一起
                result.stream().forEach(p -> {

                    //这两个就是一个 例子 数组里的spec和price
                    List<String> specList = new ArrayList<>();
                    List<BigDecimal> priceList = new ArrayList<>();

                    Long sortId = p.get(0).getSortId();
                    String sort = specSortMapper.selectById(sortId).getName();

                    p.stream().forEach(i -> {
                        specList.add(i.getName());
                        priceList.add(i.getPrice());

                    });
                    ValueDto valueDto = new ValueDto();
                    valueDto.setSpec(specList);
                    valueDto.setPrice(priceList);
                    valueDto.setSort(sort);
                    valueList.add(valueDto);
                });

                ProductDetailDto productDetailDto = productDetailDtos.stream()
                        .filter(o -> o.getId() == m.getId())
                        .findFirst()
                        .orElse(null);
                productDetailDto.setValueList(valueList);
            });

        }


        //普通选项的商品
        List<ProductInfo> nonValueSpecs = productInfos.stream().filter(m -> m.getValueSpec() == 1).collect(Collectors.toList());
        if (nonValueSpecs.size() > 0) {
            //每个商品自己的规格
            nonValueSpecs.stream().forEach(m -> {
                //找到自己所对应的规格选项
                List<Long> valueId = rProductNonValueSpecService.getNonValueId(m.getId());
                //商品的规格选项
                List<NonValueSpec> nonValueSpecsList = new ArrayList<>();
                if (valueId.size() > 0)
                    nonValueSpecsList = nonValueSpecMapper.selectBatchIds(valueId);
                Map<Long, List<NonValueSpec>> collect = nonValueSpecsList.stream().collect(Collectors.groupingBy(NonValueSpec::getSortId));
                List<List<NonValueSpec>> result = new ArrayList<>(collect.values());
                List<NonValueDto> nonValueDtoList = new ArrayList<>();
                result.stream().forEach(p -> {
                    List<String> specList = new ArrayList<>();
                    Long sortId = p.get(0).getSortId();
                    String sort = specSortMapper.selectById(sortId).getName();

                    p.stream().forEach(i -> {
                        specList.add(i.getName());

                    });
                    NonValueDto nonValueDto = new NonValueDto();
                    nonValueDto.setSpec(specList);
                    nonValueDto.setSort(sort);
                    nonValueDtoList.add(nonValueDto);
                });
                ProductDetailDto productDetailDto = productDetailDtos.stream()
                        .filter(o -> o.getId() == m.getId())
                        .findFirst()
                        .orElse(null);
                productDetailDto.setNonValueList(nonValueDtoList);
            });

        }

        return productDetailDtos;

    }

    @Override
    public void addProduct(AddProductDto product) {
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
        //需要规格
        List<Long> valueIds = null;
        List<Long> nonValueIds = null;
        if (!product.getValueList().isEmpty()) {
            //价值选项
            valueIds = valueMethod(product.getValueList(), productInfo);
            productInfo.setValueSpec((byte) 1);
        }
        if (!product.getNonValueList().isEmpty()) {
            //todo  这里可以用NonValueDto对象来接收
            //普通选项
            nonValueIds = nonValueMethod(product.getNonValueList());
            productInfo.setNonValueSpec((byte) 1);
        }

        productInfoService.saveOrUpdate(productInfo);

        rProductSpec(nonValueIds, valueIds, productInfo.getId());

        //库存
        List<ProductInventory> list = ProductInfoConvert.IN.inventoryDtoListToProductInventoryList(product.getInventoryList(), productInfo.getId());
        productInventoryService.saveOrUpdateBatch(list);
    }

    //关系表
    private void rProductSpec(List<Long> nonValueIds, List<Long> valueIds, Long productId) {
        if (nonValueIds != null) {
            List<RProductNonValueSpec> list = new ArrayList<>();
            for (Long nonValueId : nonValueIds) {
                RProductNonValueSpec rProductNonValueSpec = new RProductNonValueSpec();
                rProductNonValueSpec.setNonValueId(nonValueId);
                rProductNonValueSpec.setProductId(productId);
                list.add(rProductNonValueSpec);
            }
            rProductNonValueSpecMapper.insertBatchSomeColumn(list);
        }

        if (valueIds != null) {
            List<RProductValueSpec> list = new ArrayList<>();
            for (Long valueId : valueIds) {
                RProductValueSpec rProductValueSpec = new RProductValueSpec();
                rProductValueSpec.setProductId(productId);
                rProductValueSpec.setValueSpecId(valueId);
                list.add(rProductValueSpec);
            }
            rProductValueSpecMapper.insertBatchSomeColumn(list);

        }


    }

    private List<Long> valueMethod(List<ValueDto> valueList, ProductInfo productInfo) {
        List<Long> valueIds = new ArrayList<>();
        //找出最小价钱
        valueList.stream().forEach(m -> {
            Long specSortId = specSortMethod(m);
            for (int i = 0; i < m.getSpec().size(); i++) {
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(m.getPrice().get(i).toString()));
                String spec = m.getSpec().get(i);
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


    private List<Long> nonValueMethod(List<NonValueDto> nonValueList) {
        List<Long> nonValueIds = new ArrayList<>();

        nonValueList.stream().forEach(m -> {
            Long specSortId = specSortMethod(m);
            List list = m.getSpec();
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

    private <T> Long specSortMethod(T obj) {
        SpecSort specSort = new SpecSort();
        try {
            // 使用反射获取字段值
            Field field = obj.getClass().getDeclaredField("sort");
            field.setAccessible(true);  // 设置字段可访问
            Object value = field.get(obj);

            // 设置 SpecSort 的名称属性
            specSort.setName(value.toString());

            // 执行插入操作
            specSortMapper.insert(specSort);

            // 返回 SpecSort 的 ID
            return specSort.getId();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
//        specSort.setName(obj.get);
//        specSortMapper.insert(specSort);
//        return specSort.getId();
    }


    @Override
    public void updateProduct(AddProductDto product) {
        Long productId = product.getId();
        ProductInfo productInfo = productInfoMapper.selectById(productId);
        Byte valueSpec = productInfo.getValueSpec();
        Byte nonValueSpec = productInfo.getNonValueSpec();
        //判断原来是否有规格，如果没有就可以直接调用新增接口
        if (valueSpec == 0 && nonValueSpec == 0) {
             addProduct(product);
        }

        if (valueSpec == 1) {
            specSortMapper.deleteValue(productId);
            rProductValueSpecMapper.deleteValue(productId);
        }
        if (nonValueSpec == 1) {
            specSortMapper.deleteNonValue(productId);
            rProductNonValueSpecMapper.deleteNonValue(productId);
        }

         addProduct(product);
    }

    @Override
    public List<AppProductSort>  getProductList() {
        List<AppProductSort> sorts = ProductInfoConvert.IN.convert(productSortService.getProductSort());
        List<AppProductDto> productDtos = ProductInfoConvert.IN.convertProductInfo(productInfoMapper.selectList(null));
        productDtos.forEach(m -> m.setSelectNum(0));
        sorts.forEach(m ->{
            List<AppProductDto> appProductDtos = new ArrayList<>();
            productDtos.forEach(n ->{
                if (m.getId() == n.getSortId())
                    appProductDtos.add(n);
            });
            m.setList(appProductDtos);

        });
        return sorts;
    }
}



