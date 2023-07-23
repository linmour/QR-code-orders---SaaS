package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Vo.LoginVo;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-23T15:34:23+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class LoginVoConvertImpl implements LoginVoConvert {

    @Override
    public LoginVo MerchantToLoginVo(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        LoginVo loginVo = new LoginVo();

        loginVo.setId( merchant.getId() );
        loginVo.setPhone( merchant.getPhone() );
        loginVo.setPassword( merchant.getPassword() );
        loginVo.setStatus( merchant.getStatus() );

        return loginVo;
    }
}
