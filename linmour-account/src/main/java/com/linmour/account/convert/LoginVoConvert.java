package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Vo.LoginVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginVoConvert {
    LoginVoConvert INSTANCE = Mappers.getMapper(LoginVoConvert.class);

    default LoginVo Merchant(Merchant merchant){
        if ( merchant == null ) {
            return null;
        }
        LoginVo loginVo = new LoginVo();
        loginVo.setPassword(merchant.getPassword());
        loginVo.setPhone(merchant.getPhone());
        loginVo.setId(merchant.getId());
        return loginVo;

    }
}
