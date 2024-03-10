package com.linmour.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.security.dtos.PageResult;
import com.linmour.system.convert.ShopListDtoConvert;
import com.linmour.system.mapper.ShopMapper;
import com.linmour.system.pojo.Do.Shop;
import com.linmour.system.pojo.Dto.ShopPageDto;

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

    @Override
    public Result shopList(ShopPageDto dto) {
        //从SecurityContextHolder中拿到用户信息
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();

        Page<Shop> ShopListPage = page(new Page<Shop>(dto.getPageNo(),dto.getPageSize()),
                new LambdaQueryWrapper<Shop>().eq(Shop::getMerchantId, id)
                        .like(!StringUtils.isBlank(dto.getName()),Shop::getName,dto.getName())
                        .eq(!ObjectUtil.isNull(dto.getStatus()),Shop::getStatus,dto.getStatus()));

        if (ObjectUtil.isNull(ShopListPage.getRecords())){
            throw new CustomException(AppHttpCodeEnum.SHOP_ERRPR);
        }

        List<ShopPageDto> shopPageDtos = ShopListDtoConvert.INSTANCE.shopListToShopPageDtoList(ShopListPage.getRecords());
        return Result.success(new PageResult<>(shopPageDtos,ShopListPage.getTotal()));

    }

    @Override
    public Result SonShopList(ShopPageDto dto) {
        Page<Shop> ShopListPage = page(new Page<Shop>(dto.getPageNo(),dto.getPageSize()),
                new LambdaQueryWrapper<Shop>().eq(Shop::getParentId,dto.getId())
                        .like(!StringUtils.isBlank(dto.getName()),Shop::getName,dto.getName())
                        .eq(!ObjectUtil.isNull(dto.getStatus()),Shop::getStatus,dto.getStatus()));

        if (ObjectUtil.isNull(ShopListPage.getRecords())){
            throw new CustomException(AppHttpCodeEnum.SHOP_ERRPR);
        }

        List<ShopPageDto> shopPageDtos = ShopListDtoConvert.INSTANCE.shopListToShopPageDtoList(ShopListPage.getRecords());
        return Result.success(new PageResult<>(shopPageDtos,ShopListPage.getTotal()));
    }

    @Override
    public Result register(Shop shop) {
        shopMapper.insert(shop);
        return Result.success();
    }


}




