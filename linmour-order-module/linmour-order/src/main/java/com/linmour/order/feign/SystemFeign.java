package com.linmour.order.feign;

import com.linmour.order.feign.fallback.ProductFeignFallback;
import com.linmour.security.dtos.Result;
import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Do.Shop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "linmour-system")
public interface SystemFeign {
    @GetMapping("/system/shop/getShopByIds")
    List<Shop> getShopByIds(@RequestParam List<Long> ids) ;

    @GetMapping("/getMerchantByIds")
    List<Merchant> getMerchantByIds(List<Long> ids);
}
