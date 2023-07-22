package com.linmour.account.controller.merchant;

import com.linmour.account.convert.MerchantConvert;
import com.linmour.account.pojo.Dto.UserInfoDto;
import com.linmour.account.service.MerchantService;
import com.linmour.common.dtos.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
@RestController
@RequestMapping("/account/merchant")
public class merchantController {

    @Resource
    private MerchantService merchantService;
    @PostMapping("/userInfo")
    public Result userInfo(Long userId){
        return Result.success(merchantService.userInfo(userId));
    }

    @PostMapping("/upload_picture")
    public Result uploadPicture(@RequestParam("multipartFile")MultipartFile multipartFile, @RequestParam String prefix ){
        return merchantService.uploadPicture(multipartFile,prefix);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UserInfoDto userInfoDto){

        return Result.success(merchantService.updateById(MerchantConvert.INSTANCE.UserInfoDto(userInfoDto)));
    }
}
