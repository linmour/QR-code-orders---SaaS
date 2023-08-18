package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfoDtoConvert {

    UserInfoDtoConvert INSTANCE = Mappers.getMapper(UserInfoDtoConvert.class);


    UserInfoDto MerchantToUserInfoDto(Merchant merchant);
}
