package com.linmour.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.product.mapper.ProductInfoMapper;
import com.linmour.product.mapper.RProductInventotyMapper;
import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.pojo.Do.RProductInventoty;
import com.linmour.product.pojo.Dto.ProductInventoryAllDto;
import com.linmour.product.service.ProductInventoryService;
import com.linmour.product.mapper.ProductInventoryMapper;
import com.linmour.security.dtos.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.linmour.security.utils.SecurityUtils.getShopId;

/**
 * @author linmour
 * @description 针对表【product_inventory】的数据库操作Service实现
 * @createDate 2023-08-12 18:34:04
 */
@Service
public class ProductInventoryServiceImpl extends ServiceImpl<ProductInventoryMapper, ProductInventory>
        implements ProductInventoryService {

    @Resource
    private ProductInventoryMapper productInventoryMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private RProductInventotyMapper rProductInventotyMapper;
    @Resource
    private ProductInventoryService productInventoryService;

    @Override
    public PageResult getProductInventory(ProductInventoryAllDto dto) {
        List<ProductInventory> productInventories = productInventoryMapper.selectList(new LambdaQueryWrapper<ProductInventory>().eq(!ObjectUtil.isNull(getShopId()),ProductInventory::getShopId, getShopId()));
        List<ProductInventoryAllDto> productInventoryAllDtos = BeanUtil.copyToList(productInventories, ProductInventoryAllDto.class);
        List<Long> inventorieIds = productInventories.stream().map(ProductInventory::getId).collect(Collectors.toList());
        List<RProductInventoty> rProductInventoties = rProductInventotyMapper.selectList(new LambdaQueryWrapper<RProductInventoty>().in(RProductInventoty::getInventoryId, inventorieIds));
        List<Long> productIds = rProductInventoties.stream().map(RProductInventoty::getProductId).collect(Collectors.toList());
        List<ProductInfo> productInfos = productInfoMapper.selectBatchIds(productIds);
        productInventoryAllDtos.forEach(n -> {
            List<ProductInfo> productInfos1 = new ArrayList<>();
            rProductInventoties.forEach(o -> {
                productInfos.forEach(l -> {
                    if (o.getProductId().equals(l.getId()) && o.getInventoryId().equals(n.getId()))
                        productInfos1.add(l);
                });
            });
            n.setProductInfo(productInfos1);

        });

        return new PageResult<>(productInventoryAllDtos, Long.valueOf(productInventoryAllDtos.size()));

    }

    @Override
    public void createInventory(ProductInventoryAllDto dto) {
        productInventoryMapper.insert(BeanUtil.copyProperties(dto, ProductInventory.class));
    }

    @Override
    public void deletedInventory(Long id) {
        productInventoryMapper.deleteById(id);
    }

    @Override
    public ProductInventory getProductInventoryById(Long id) {
        return productInventoryMapper.selectById(id);
    }

    @Override
    public void updateInventory(ProductInventory productInventory) {
        productInventoryMapper.updateById(productInventory);
    }

    @Override
    public void reduceInventories(List<Long> productIds) {
        List<RProductInventoty> rProductInventoties = rProductInventotyMapper.selectList(new LambdaQueryWrapper<RProductInventoty>().in(RProductInventoty::getProductId, productIds));
        if (rProductInventoties.isEmpty())
            return;
        List<Long> inventoryIds = rProductInventoties.stream().map(RProductInventoty::getInventoryId).collect(Collectors.toList());
        List<ProductInventory> productInventories = productInventoryMapper.selectBatchIds(inventoryIds);
        productInventories.forEach(n ->{
            rProductInventoties.forEach(p ->{
                if (n.getId().equals(p.getInventoryId()))
                    n.setNum(n.getNum()-p.getNum());
            });
        });
        productInventoryService.saveOrUpdateBatch(productInventories);

    }
}




