package com.linmour.system.convert;

import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MerchantConvert {
    MerchantConvert INSTANCE = Mappers.getMapper(MerchantConvert.class);

    Merchant userInfoDtoToMerchant(UserInfoDto userInfoDto);
}
