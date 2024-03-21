package com.linmour.system.controller.admin.merchant;

import com.linmour.security.dtos.Result;


import com.linmour.security.exception.CustomException;
import com.linmour.security.exception.enums.AppHttpCodeEnum;
import com.linmour.system.convert.MerchantConvert;
import com.linmour.system.pojo.Dto.UserInfoDto;
import com.linmour.system.service.MerchantService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/system/merchant")
public class MerchantController {

    @Resource
    private MerchantService merchantService;
    @PostMapping("/userInfo")
    public Result userInfo(Long userId){
        return Result.success(merchantService.getUserInfo(userId));
    }


    @PostMapping("/ocr")
    public Result ocr(@RequestParam("multipartFile")MultipartFile multipartFile){
        return merchantService.ocr(multipartFile);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,UserInfoDto>  map){
        UserInfoDto userInfo = map.get("userInfo");

        try {
            return Result.success(merchantService.updateById(MerchantConvert.IN.userInfoDtoToMerchant(userInfo)));
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException){
                throw new CustomException(AppHttpCodeEnum.PHONENUMBER_EXIST);
            }
            throw new RuntimeException(e);
        }
    }
}
