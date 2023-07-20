package com.linmour.account;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

// 这个是为了扫描到全局异常类的包，要不然捕获不到
@SpringBootApplication(scanBasePackages = {"com.linmour"})
@EnableDiscoveryClient
@MapperScan("com.linmour.account.mapper")

public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class,args);
    }



}
