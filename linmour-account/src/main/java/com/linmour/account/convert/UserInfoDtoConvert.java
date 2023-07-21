package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfoDtoConvert {

    UserInfoDtoConvert INSTANCE = Mappers.getMapper(UserInfoDtoConvert.class);


    default UserInfoDto Merchant(Merchant merchant){
        if ( merchant == null ) {
            return null;
        }
        return UserInfoDto.builder()
                .sex(merchant.getSex())
                .avatar(merchant.getAvatar())
                .phone(merchant.getPhone())
                .id(merchant.getId())
                .realName(merchant.getRealName()).build();

    }
}
