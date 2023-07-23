package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Vo.LoginVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginVoConvert {
    LoginVoConvert INSTANCE = Mappers.getMapper(LoginVoConvert.class);

    LoginVo MerchantToLoginVo(Merchant merchant);
}
