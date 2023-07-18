package com.linmour.account.service;

import com.linmour.account.pojo.Do.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.account.pojo.Vo.LoginVo;
import com.linmour.common.dtos.Result;

/**
* @author linmour
* @description 针对表【system_merchant】的数据库操作Service
* @createDate 2023-07-17 20:05:36
*/
public interface MerchantService extends IService<Merchant> {

    Result login(LoginVo loginVo);
}
