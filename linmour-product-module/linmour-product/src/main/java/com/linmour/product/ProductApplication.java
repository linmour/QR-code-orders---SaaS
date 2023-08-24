package com.linmour.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.linmour"})
@EnableDiscoveryClient
@MapperScan("com.linmour.product.mapper")
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class);
    }
}
