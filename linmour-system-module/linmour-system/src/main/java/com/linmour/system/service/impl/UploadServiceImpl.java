package com.linmour.system.service.impl;

import cn.hutool.core.lang.UUID;
import com.linmour.security.dtos.LoginUser;
import com.linmour.security.dtos.Result;
import com.linmour.system.service.UploadService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.linmour.security.dtos.Result.success;
import static com.linmour.security.utils.SecurityUtils.getShopId;

@Service
public class UploadServiceImpl implements UploadService {



    @Override
    public Result uploadImg(MultipartFile file,String prefix) {
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        String fileName = id.toString();
        return Result.success();
    }

    @Override
    public Result uploadImgs(MultipartFile[] file,String prefix) {
        String shopId = getShopId().toString();
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        List<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String fileName = id.toString() + "/" + shopId + "/" + UUID.randomUUID().toString().replace("-", "");
        }
        return success();
    }
}
