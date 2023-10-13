package com.linmour.system.convert;

import com.linmour.system.pojo.Do.Merchant;
import com.linmour.common.dtos.LoginVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginVoConvert {
    LoginVoConvert INSTANCE = Mappers.getMapper(LoginVoConvert.class);

    LoginVo MerchantToLoginVo(Merchant merchant);
}
