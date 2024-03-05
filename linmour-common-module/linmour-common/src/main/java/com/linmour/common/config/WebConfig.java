package com.linmour.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @BelongsProject: demo
 * @Author: DanBrown
 * @CreateTime: 2020-03-28 14:33
 * @description: TODO
 */
//url映射本地路径
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Value("${upload.path}")
//    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //  /file/**为前端URL访问路径  后面 file:xxxx为本地磁盘映射 file:/C:/aaa/bbb/
        registry.addResourceHandler("/file/**").addResourceLocations("file:D://b/" );
    }

}
