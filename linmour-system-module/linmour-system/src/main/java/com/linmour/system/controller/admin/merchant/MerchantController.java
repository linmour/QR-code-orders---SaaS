package com.linmour.system.controller.admin.merchant;

import com.linmour.system.convert.MerchantConvert;
import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Dto.UserInfoDto;
import com.linmour.system.service.MerchantService;
import com.linmour.common.dtos.Result;
import com.linmour.common.exception.CustomException;
import com.linmour.common.exception.enums.AppHttpCodeEnum;
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
            return Result.success(merchantService.updateById(MerchantConvert.INSTANCE.userInfoDtoToMerchant(userInfo)));
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException){
                throw new CustomException(AppHttpCodeEnum.PHONENUMBER_EXIST);
            }
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public Result register(Merchant merchant){

        return merchantService.register(merchant);
    }
}
