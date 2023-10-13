package com.linmour.system.service;

import com.linmour.common.dtos.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Result uploadImg(MultipartFile file,String prefix);

    Result uploadImgs(MultipartFile[] file,String prefix);
}
