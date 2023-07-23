package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MerchantConvert {
    MerchantConvert INSTANCE = Mappers.getMapper(MerchantConvert.class);

    Merchant userInfoDtoToMerchant(UserInfoDto userInfoDto);
}
