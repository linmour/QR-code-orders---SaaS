package com.linmour.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linmour.order.grpc.*;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * GrpcClientService类有几处要注意的地方：
 * <p>
 * 用@Service将GrpcClientService注册为spring的普通bean实例；
 * <p>
 * 用@GrpcClient修饰SimpleBlockingStub，这样就可以通过grpc-client-spring-boot-starter库发起gRPC调用，被调用的服务端信息来自名为nacos-grpc服务端配置；
 * <p>
 * SimpleBlockingStub来自前文中根据helloworld.proto生成的java代码；
 * <p>
 * SimpleBlockingStub.oneToOne方法会远程调用nacos-grpc应用的gRPC服务；
 */
@Service
@Slf4j
public class GrpcClientService {

    @GrpcClient("nacos-grpc")
    private ProductGrpc.ProductBlockingStub productBlockingStub;
    @GrpcClient("another-service")
    private SystemGrpc.SystemBlockingStub systemBlockingStub;
    ObjectMapper objectMapper = new ObjectMapper();


    public void getProductDetails(final List<Long> ids) throws IOException {

        final Result1 productDetails = productBlockingStub.getProductDetails(LongList.newBuilder().addAllValues(ids).build());
        List<ProductDetailDto> productDe = objectMapper.readValue(productDetails.getData().toByteArray(), new TypeReference<List<ProductDetailDto>>() {
        });
        System.out.println(productDe);
        System.out.println("---------------------------");


    }

    public void reduceInventories(final List<Long> ids) throws IOException {
        Result2 result2 = productBlockingStub.reduceInventories(LongList.newBuilder().addAllValues(ids).build());
        System.out.println(result2);
        System.out.println("++++++++++++++++++++");
    }

    public void getShopByIds(final List<Long> ids) throws IOException {
        Result1 result = systemBlockingStub.getShopByIds(LongList.newBuilder().addAllValues(ids).build());
        System.out.println(result);
        System.out.println("++++++++++++++++++++");
    }

    public void getMerchantByIds(final List<Long> ids) throws IOException {
        Result1 result = systemBlockingStub.getMerchantByIds(LongList.newBuilder().addAllValues(ids).build());
        System.out.println(result);
        System.out.println("++++++++++++++++++++");
    }
}


