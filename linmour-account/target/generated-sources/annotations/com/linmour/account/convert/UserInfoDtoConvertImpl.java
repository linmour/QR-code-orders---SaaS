package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Dto.UserInfoDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-25T20:41:05+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class UserInfoDtoConvertImpl implements UserInfoDtoConvert {

    @Override
    public UserInfoDto MerchantToUserInfoDto(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        UserInfoDto.UserInfoDtoBuilder userInfoDto = UserInfoDto.builder();

        userInfoDto.id( merchant.getId() );
        userInfoDto.realName( merchant.getRealName() );
        userInfoDto.phone( merchant.getPhone() );
        userInfoDto.sex( merchant.getSex() );
        userInfoDto.avatar( merchant.getAvatar() );
        userInfoDto.type( merchant.getType() );
        userInfoDto.idCard( merchant.getIdCard() );
        userInfoDto.idCardUrl( merchant.getIdCardUrl() );

        return userInfoDto.build();
    }
}
