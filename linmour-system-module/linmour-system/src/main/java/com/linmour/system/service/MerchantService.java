package com.linmour.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.system.pojo.Do.Merchant;


import com.linmour.system.pojo.Dto.UserInfoDto;
import com.linmour.common.dtos.LoginVo;
import com.linmour.common.dtos.Result;
import org.springframework.web.multipart.MultipartFile;

/**
* @author linmour
* @description 针对表【system_merchant】的数据库操作Service
* @createDate 2023-07-17 20:05:36
*/
public interface MerchantService extends IService<Merchant> {

    Result login(LoginVo loginVo);

    Result logout(Long id);

    UserInfoDto getUserInfo(Long userId);

    Result uploadAvatar(MultipartFile multipartFile);

    Result ocr(MultipartFile multipartFile);



}
