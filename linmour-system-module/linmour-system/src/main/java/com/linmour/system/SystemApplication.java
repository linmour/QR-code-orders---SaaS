package com.linmour.system;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 这个是为了扫描到全局异常类的包，要不然捕获不到
@SpringBootApplication(scanBasePackages = {"com.linmour"})


@MapperScan("com.linmour.system.mapper")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }

}
