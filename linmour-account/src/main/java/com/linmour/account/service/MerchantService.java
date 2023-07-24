package com.linmour.account.service;

import com.linmour.account.pojo.Do.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.account.pojo.Vo.LoginVo;
import com.linmour.account.pojo.Dto.UserInfoDto;
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

    Result uploadPicture(MultipartFile multipartFile,String prefix);

    Result ocr(MultipartFile multipartFile);



}
