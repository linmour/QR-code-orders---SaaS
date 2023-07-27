package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Dto.UserInfoDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-27T17:33:27+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class MerchantConvertImpl implements MerchantConvert {

    @Override
    public Merchant userInfoDtoToMerchant(UserInfoDto userInfoDto) {
        if ( userInfoDto == null ) {
            return null;
        }

        Merchant merchant = new Merchant();

        merchant.setId( userInfoDto.getId() );
        merchant.setRealName( userInfoDto.getRealName() );
        merchant.setPhone( userInfoDto.getPhone() );
        merchant.setIdCard( userInfoDto.getIdCard() );
        merchant.setIdCardUrl( userInfoDto.getIdCardUrl() );
        merchant.setType( userInfoDto.getType() );
        merchant.setSex( userInfoDto.getSex() );
        merchant.setAvatar( userInfoDto.getAvatar() );

        return merchant;
    }
}
