package com.linmour.system.convert;

import com.linmour.security.dtos.LoginVo;
import com.linmour.system.pojo.Do.Menu;
import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Dto.MenuListDto;
import com.linmour.system.pojo.Dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MerchantConvert {
    MerchantConvert IN = Mappers.getMapper(MerchantConvert.class);

    Merchant userInfoDtoToMerchant(UserInfoDto userInfoDto);
    List<MenuListDto> MenuToMenuList(List<Menu> menu);
    LoginVo MerchantToLoginVo(Merchant merchant);
    UserInfoDto MerchantToUserInfoDto(Merchant merchant);


}
