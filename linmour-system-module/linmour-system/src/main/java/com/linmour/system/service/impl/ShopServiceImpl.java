package com.linmour.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.linmour.security.dtos.LoginVo;
import com.linmour.security.dtos.PageResult;
import com.linmour.system.mapper.ShopMapper;
import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Do.Shop;
import com.linmour.system.pojo.Dto.ShopPage;

import com.linmour.system.service.ShopService;
import com.linmour.security.dtos.LoginUser;

import com.linmour.security.dtos.Result;
import com.linmour.security.exception.CustomException;
import com.linmour.security.exception.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author linmour
 * @description 针对表【system_shop】的数据库操作Service实现
 * @createDate 2023-07-21 15:23:21
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop>
        implements ShopService {
    @Resource
    private ShopMapper shopMapper;
    @Resource
    private ShopService shopService;

    @Override
    public Result shopList(ShopPage dto) {
        //从SecurityContextHolder中拿到用户信息
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginVo loginVo = user.getLoginVo();
        Long id = null;
        if (!loginVo.getType().equals(1))
            id = loginVo.getId();

//        Page<Shop> ShopListPage = page(new Page<Shop>(dto.getPageNo(), dto.getPageSize()),
//                new LambdaQueryWrapper<Shop>().eq(!ObjectUtil.isNull(id),Shop::getMerchantId, id)
//                        .eq(!ObjectUtil.isNull(dto.getAuditStatus()), Shop::getAuditStatus, dto.getAuditStatus())
//                        .like(!StringUtils.isBlank(dto.getName()), Shop::getName, dto.getName())
//                        .eq(!ObjectUtil.isNull(dto.getShopStatus()), Shop::getShopStatus, dto.getShopStatus()));

        List<ShopPage> shopPages1 = shopMapper.selectJoinList(ShopPage.class, new MPJLambdaWrapper<Shop>()
                .selectAll(Shop.class)
                .eq(!ObjectUtil.isNull(id), Shop::getMerchantId, id)
                .eq(!ObjectUtil.isNull(dto.getAuditStatus()), Shop::getAuditStatus, dto.getAuditStatus())
                .like(!StringUtils.isBlank(dto.getName()), Shop::getName, dto.getName())
                .eq(!ObjectUtil.isNull(dto.getShopStatus()), Shop::getShopStatus, dto.getShopStatus())
                .leftJoin(Merchant.class, Merchant::getId, Shop::getMerchantId)
                .select(Merchant::getRealName));
        if (ObjectUtil.isNull(shopPages1)) {
            throw new CustomException(AppHttpCodeEnum.SHOP_ERRPR);
        }

        List<ShopPage> shopPages = BeanUtil.copyToList(shopPages1, ShopPage.class);
        return Result.success(new PageResult<>(shopPages, (long) shopPages1.size()));

    }

    @Override
    public Result SonShopList(ShopPage dto) {
        Page<Shop> ShopListPage = page(new Page<Shop>(dto.getPageNo(), dto.getPageSize()),
                new LambdaQueryWrapper<Shop>().eq(Shop::getParentId, dto.getId())
                        .like(!StringUtils.isBlank(dto.getName()), Shop::getName, dto.getName())
                        .eq(!ObjectUtil.isNull(dto.getShopStatus()), Shop::getShopStatus, dto.getShopStatus()));

        if (ObjectUtil.isNull(ShopListPage.getRecords())) {
            throw new CustomException(AppHttpCodeEnum.SHOP_ERRPR);
        }

        List<ShopPage> shopPages = BeanUtil.copyToList(ShopListPage.getRecords(), ShopPage.class);
        return Result.success(new PageResult<>(shopPages, ShopListPage.getTotal()));
    }


    @Override
    public Long save0rUpdateShop(Shop shop) {
        shopService.saveOrUpdate(shop);
        return shop.getId();
    }

    @Override
    public List<Shop> getShopByIds(List<Long> ids) {
        return shopMapper.selectBatchIds(ids);
    }


}




