package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Dto.LoginDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginVoConvert {
    LoginVoConvert INSTANCE = Mappers.getMapper(LoginVoConvert.class);

    default LoginDto Merchant(Merchant merchant){
        if ( merchant == null ) {
            return null;
        }
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(merchant.getPassword());
        loginDto.setPhone(merchant.getPhone());
        loginDto.setId(merchant.getId());
        return loginDto;

    }
}
