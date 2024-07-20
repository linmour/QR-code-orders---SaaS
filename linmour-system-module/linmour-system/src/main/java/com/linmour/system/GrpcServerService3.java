package com.linmour.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;

import com.linmour.system.grpc.LongList;
import com.linmour.system.grpc.Result1;
import com.linmour.system.grpc.SystemGrpc;
import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Do.Shop;
import com.linmour.system.service.MerchantService;
import com.linmour.system.service.ShopService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.annotation.Resource;
import java.util.List;


/**
 * GrpcServerService3.java中有几处需要注意：
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
public class GrpcServerService3 extends SystemGrpc.SystemImplBase {

    @Resource
    private MerchantService merchantService;
    @Resource
    private ShopService shopService;

    // 将集合转换为 JSON 字符串，然后转换为字节数组
    ObjectMapper objectMapper = new ObjectMapper();

    public void getShopByIds(LongList request, StreamObserver<Result1> responseObserver) {
        log.info("接收客户端数据{}", request);

        List<Shop> shopByIds = shopService.getShopByIds(request.getValuesList());
        Result1 response = null;
        try {
            response = Result1.newBuilder().setCode(200).setMsg("success").setData(ByteString.copyFrom((objectMapper.writeValueAsString(shopByIds)).getBytes())).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getMerchantByIds(LongList request, StreamObserver<Result1> responseObserver) {
        log.info("接收客户端数据{}", request);
        List<Merchant> merchantByIds = merchantService.getMerchantByIds(request.getValuesList());
        Result1 response = null;
        try {
            response = Result1.newBuilder().setCode(200).setMsg("success").setData(ByteString.copyFrom((objectMapper.writeValueAsString(merchantByIds)).getBytes())).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}