package com.linmour.system.service;

import com.linmour.system.pojo.Do.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.system.pojo.Dto.ShopPage;
import com.linmour.security.dtos.Result;

import java.util.List;

/**
* @author linmour
* @description 针对表【system_shop】的数据库操作Service
* @createDate 2023-07-21 15:23:21
*/
public interface ShopService extends IService<Shop> {

    /**
     * 查询拥有的店铺
     * @param dto
     * @return
     */
    Result shopList(ShopPage dto);

    /**
     * 查询分店
     * @param dto
     * @return
     */
    Result SonShopList(ShopPage dto);


    Long save0rUpdateShop(Shop shop);

    List<Shop> getShopByIds(List<Long> ids);
}
