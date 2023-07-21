package com.linmour.account.service;

import com.linmour.account.pojo.Do.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.account.pojo.Dto.ShopDto;
import com.linmour.common.dtos.Result;

/**
* @author linmour
* @description 针对表【system_shop】的数据库操作Service
* @createDate 2023-07-21 15:23:21
*/
public interface ShopService extends IService<Shop> {

    Result shopList(ShopDto dto);
}
