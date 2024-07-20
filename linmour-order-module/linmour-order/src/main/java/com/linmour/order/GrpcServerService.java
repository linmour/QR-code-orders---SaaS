package com.linmour.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.linmour.common.SerializationUtil;
import com.linmour.order.grpc.Object;
import com.linmour.order.grpc.OrderGrpc;
import com.linmour.order.grpc.Result1;
import com.linmour.order.grpc.Result2;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.security.dtos.Result;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.annotation.Resource;


/**
 * GrpcServerService.java中有几处需要注意：
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
public class GrpcServerService extends OrderGrpc.OrderImplBase {

    @Resource
    private OrderInfoService orderInfoService;

    @Override
    public void createOrder(Object request, StreamObserver<Result1> responseObserver) {
        log.info("接收客户端数据{}", request);

        try {
            byte[] data1 = request.getData().toByteArray();
            CreateOrderDto createOrderDto1 = (CreateOrderDto) SerializationUtil.deserializeObject(data1);
            System.out.println(createOrderDto1);
//            byte[] data = SerializationUtil.serializeObject(orderInfoService.createOrder(createOrderDto1));
            Result order = orderInfoService.createOrder(createOrderDto1);
            System.out.println(order);
            Result1 response = Result1.newBuilder().setCode(200).setMsg("success").setData(ByteString.copyFromUtf8(order.getData().toString())).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}