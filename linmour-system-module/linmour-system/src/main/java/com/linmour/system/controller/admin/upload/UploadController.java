package com.linmour.system.controller.admin.upload;


import com.linmour.common.dtos.Result;
import com.linmour.system.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
@RestController
@RequestMapping("/system/upload")
public class UploadController {

    @Resource
    private UploadService uploadService;
    @PostMapping("/uploadImg")
//    @RequestParam("prefix") String prefix 接收前端的参数 prefix 表示上传的是头像或者是菜品等
    public Result uploadImg(@RequestParam("file") MultipartFile file,@RequestParam("prefix") String prefix){
        return uploadService.uploadImg(file,prefix);
    }

    @PostMapping("/uploadImgs")
    public Result uploadImgs(@RequestParam("file") MultipartFile[] file,@RequestParam("prefix") String prefix){
        return uploadService.uploadImgs(file,prefix);
    }

}
