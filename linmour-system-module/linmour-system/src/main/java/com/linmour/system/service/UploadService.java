package com.linmour.system.service;

import com.linmour.security.dtos.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Result uploadImg(MultipartFile file,String prefix);

    Result uploadImgs(MultipartFile[] file,String prefix);
}
