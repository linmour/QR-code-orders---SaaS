package com.linmour.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.linmour.product.grpc.LongList;
import com.linmour.product.grpc.ProductGrpc;
import com.linmour.product.grpc.Result1;
import com.linmour.product.grpc.Result2;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import com.linmour.product.service.ProductInfoService;
import com.linmour.product.service.ProductInventoryService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.annotation.Resource;
import java.util.List;


/**
 * GrpcServerService2.java中有几处需要注意：
 * <p>
 * 是使用@GrpcService注解，再继承SimpleImplBase，这样就可以借助grpc-server-spring-boot-starter库将oneToOne暴露为gRPC服务；
 * <p>
 * SimpleImplBase是前文中根据maven compile编译 proto文件自动生成的java代码，
 * <p>
 * oneToOne方法中处理完毕业务逻辑后，调用responseObserver.onNext方法填入返回内容；
 * <p>
 * 调用responseObserver.onCompleted方法表示本次gRPC服务完成；
 */
@GrpcService
@Slf4j
public class GrpcServerService2 extends ProductGrpc.ProductImplBase {

    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private ProductInventoryService productInventoryService;
    // 将集合转换为 JSON 字符串，然后转换为字节数组
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void getProductDetails(LongList request, StreamObserver<Result1> responseObserver) {
        log.info("接收客户端数据{}", request);

        try {
            List<ProductDetailDto> productDetails = productInfoService.getProductDetails(request.getValuesList());
           byte[] byteArray = (objectMapper.writeValueAsString(productDetails)).getBytes();
            Result1 response = Result1.newBuilder().setCode(200).setMsg("success").setData(ByteString.copyFrom(byteArray)).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void reduceInventories(LongList request, StreamObserver<Result2> responseObserver) {
        log.info("接收客户端数据{}", request);
        productInventoryService.reduceInventories(request.getValuesList());
        Result2 response = Result2.newBuilder().setCode(200).setMsg("success").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}