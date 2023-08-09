package com.linmour.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.lang.UUID;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
* @author linmour
* @description 针对表【product_info】的数据库操作Service实现
* @createDate 2023-07-25 18:45:50
*/
@Service
@Transactional
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo>
    implements ProductInfoService{
    @Resource
    private FileStorageService fileStorageService;
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private ProductSortMapper productSortMapper;
    @Resource
    private ProductSizeMapper productSizeMapper;
    @Resource
    private ProductSpecMapper productSpecMapper;
    @Resource
    private ProductTasteMapper productTasteMapper;
    @Resource
    private RProductSizeMapper rProductSizeMapper;
    @Resource
    private RProductSpecMapper rProductSpecMapper;
    @Resource
    private RProductTasteMapper rProductTasteMapper;
    @Resource
    private ProductInfoService productInfoService;

    @Override
    public Result getProductList(ProductInfoPageDto dto) {
        List<ProductInfo> productInfos = productInfoMapper.selectList(new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getShopId,dto.getShopId())
                .eq(!ObjectUtil.isNull(dto.getSortId()), ProductInfo::getSortId, dto.getSortId()));
        //因为前台的缘故，第一次没传sort值，所以要默认拿到第一个sort
        if (dto.getSortId() == null && productInfos.size() >0) {
            Long sortId1 = productInfos.get(0).getSortId();
            dto.setSortId(sortId1);
        }
        //这个是新店没有分类会报空指针
        if (dto.getSortId() == null ){
            return Result.success();

        }

        Page<ProductInfo> productInfoPage = page(new Page<ProductInfo>(dto.getPageNo(), dto.getPageSize()),new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getShopId,dto.getShopId())
                .eq(StringUtils.isNotBlank(dto.getSortId().toString()),ProductInfo::getSortId,dto.getSortId())
        );
        if (ObjectUtil.isNull(productInfoPage.getRecords())){
            throw new CustomException(AppHttpCodeEnum.PRODUCT_ERROR);
        }
        List<ProductInfoPageDto> productInfoPageDtos = ProductInfoPageDtoConvert.INSTANCE.ProductInfoToProductInfoPageDto(productInfoPage.getRecords());
        return Result.success(new PageResult<>(productInfoPageDtos,productInfoPage.getTotal()));
    }

    @Override
    public Result changeProduct(ProductInfoPageDto productInfo) {
        ProductInfo productInfo1 = ProductInfoConvert.IN.ProductInfoPageDtoToProductInfo(productInfo);
        productInfoMapper.updateById(productInfo1);
        return Result.success();
    }

    @Override
    public Result getProductDetails(Long productId) {
        //商品基本信息
        ProductInfo productInfo = productInfoMapper.selectOne(new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getId, productId));
        ProductDetailDto productDetailDto = ProductDetailDtoConvert.IN.ProductInfoToProductDetailDto(productInfo);
        String sort = productInfoMapper.getSort(productId);
        ProductSpec productSpec =  productInfoMapper.getspecId(productId);
        //有规格等选项
        if (productSpec != null){
            String isSize = productSpec.getSize();
            String isTaste = productSpec.getTaste();
            List<String> size;
            List<BigDecimal> priceList;
            List<Integer> sizeStatus;
            List<Long> sizeId;
            List<Long> tasteId;
            List<ProductTaste> tasteList = null;
            List<Integer> tasteStatus;
            List<String> taste;
            List<ProductSizeAndPrice> optionPriceList = null;
            List<TasteAndId> tasteAndIdList = null;
            if (isSize.equals("1")  ){
                List<ProductSize> sizeList = productInfoMapper.getsize(productId);
                //已选中的规格的价钱
                sizeId = sizeList.stream().map(ProductSize::getId).collect(Collectors.toList());
                size = sizeList.stream().map(ProductSize::getOptions).collect(Collectors.toList());
                sizeStatus = sizeList.stream().map(ProductSize::getStatus).collect(Collectors.toList());
                priceList = sizeList.stream().map(ProductSize::getPrice).collect(Collectors.toList());
                optionPriceList = IntStream.range(0, size.size())
                        .mapToObj(i -> new ProductSizeAndPrice(size.get(i), priceList.get(i),sizeStatus.get(i),sizeId.get(i)))
                        .collect(Collectors.toList());
            }
            if (isTaste.equals("1")){
                tasteList = productInfoMapper.gettaste(productId);
                tasteId = tasteList.stream().map(ProductTaste::getId).collect(Collectors.toList());
                tasteStatus = tasteList.stream().map(ProductTaste::getStatus).collect(Collectors.toList());
                taste = tasteList.stream().map(ProductTaste::getOptions).collect(Collectors.toList());
                tasteAndIdList = IntStream.range(0, tasteList.size()).mapToObj(i -> new TasteAndId(tasteId.get(i), taste.get(i), tasteStatus.get(i))).collect(Collectors.toList());

            }


            productDetailDto.setProductSizeAndPriceList(optionPriceList);
            productDetailDto.setTasteAndId(tasteAndIdList);
        }

        productDetailDto.setSort(sort);
        return Result.success(productDetailDto);

    }

    @Override
    public Result addProduct(AddProductDto product) {
        //商品
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
        //如果不需要规格的话，那么其他的表的不需要插入
        if (product.getSize().size() != 0 || product.getTaste().size() != 0){
            ProductSpec productSpec = new ProductSpec();
            if (product.getSize().size() != 0){
                productInfo.setSpec(1);
                productSpec.setSize("1");
            }
            if (product.getTaste().size() != 0){
                productInfo.setSpec(1);
                productSpec.setTaste("1");
            }
            productInfoService.saveOrUpdate(productInfo);
            //商品规格
            productSpecMapper.insert(productSpec);
            rProductSpecMapper.insert(new RProductSpec(productInfo.getId(),productSpec.getId()));
            //尺寸和价格
            builderProductSize(product.getSize(),product.getPrice(),productInfo.getId());
            //口味
            builderProductTaste(product.getTaste(),productInfo.getId());
        }
        return Result.success();
    }

    @Override
    public Result uploadProductImg(MultipartFile[] file,String shopId) {
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        List<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String fileName =  id.toString() +"/"+ shopId + "/" + UUID.randomUUID().toString().replace("-", "");
            String url = fileStorageService.uploadPicture(multipartFile, "product", fileName);
            list.add(url);
        }
        return Result.success(list);
    }

    @Override
    public Result updateProduct(AddProductDto product) {
        Long productId = product.getId();
        ProductInfo productInfo = productInfoMapper.selectById(productId);
        //判断原来是否有规格，如果没有就可以直接调用新增接口
        if (productInfo.getSpec() == 0){
            return addProduct(product);
        }
        ProductSpec productSpec =  productInfoMapper.getspecId(productId);
        productSpecMapper.deleteSpec(productId);
        if (productSpec.getSize().equals("1")){
            productSizeMapper.deleteSize(productId);
        }
        if (productSpec.getTaste().equals("1")){
            productTasteMapper.deleteTaste(productId);

        }
        return addProduct(product);
    }

    private void builderProductTaste(List<String> taste, Long productId) {
        for (String item : taste) {
            ProductTaste productTaste = new ProductTaste(item);
            productTasteMapper.insert(productTaste);
            rProductTasteMapper.insert(new RProductTaste(productTaste.getId(),productId));
        }
    }

    private void builderProductSize(List<String> size, List<BigDecimal> price,Long productId) {
        if ((size.size() + price.size()) % 2 != 0){
            throw new CustomException(null);
        }
        for (int i = 0; i < size.size(); i++) {
            ProductSize productSize = new ProductSize();
            productSize.setPrice(price.get(i));
            productSize.setOptions(size.get(i));
            productSizeMapper.insert(productSize);
            RProductSize rProductSize = new RProductSize();
            rProductSize.setSizeId(productSize.getId());
            rProductSize.setProductId(productId);
            rProductSizeMapper.insert(rProductSize);

        }
    }


}




