package com.linmour.system.controller.admin.merchant;

import com.linmour.security.dtos.Result;


import com.linmour.security.exception.CustomException;
import com.linmour.security.exception.enums.AppHttpCodeEnum;
import com.linmour.system.convert.MerchantConvert;
import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Do.Shop;
import com.linmour.system.pojo.Dto.MerchantPage;
import com.linmour.system.pojo.Dto.UserInfoDto;
import com.linmour.system.service.MerchantService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/merchant")
public class MerchantController {

    @Resource
    private MerchantService merchantService;

    @PostMapping("/userInfo")
    public Result userInfo(Long userId) {
        return Result.success(merchantService.getUserInfo(userId));
    }

    @PostMapping("/saveOrUpdateMerchant")
    public Result saveOrUpdateMerchant(@RequestBody Merchant merchant) {
        return Result.success(merchantService.saveOrUpdateMerchant(merchant));
    }

    @PostMapping("/ocr")
    public Result ocr(@RequestParam("multipartFile") MultipartFile multipartFile) {
        return merchantService.ocr(multipartFile);
    }

    @GetMapping("/getMerchantPage")
    public Result getMerchantPage( MerchantPage merchantPage) {
        return Result.success(merchantService.getMerchantPage(merchantPage));
    }
    @GetMapping("/getMerchantByIds")
    public List<Merchant> getMerchantByIds( List<Long> ids) {
        return merchantService.getMerchantByIds(ids);
    }

    @PostMapping("/save0rUpdateMerchant")
    public Result save0rUpdateMerchant(@RequestBody Merchant merchant) {
        return Result.success(merchantService.save0rUpdateMerchant(merchant));
    }
    @PostMapping("/update")
    public Result update(@RequestBody UserInfoDto userInfoDto) {

        try {
            return Result.success(merchantService.updateById(MerchantConvert.IN.userInfoDtoToMerchant(userInfoDto)));
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new CustomException(AppHttpCodeEnum.PHONENUMBER_EXIST);
            }
            throw new RuntimeException(e);
        }
    }
}
