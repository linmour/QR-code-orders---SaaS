package com.linmour.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.security.dtos.Result;
import com.linmour.websocket.grpc.Object;
import com.linmour.websocket.grpc.OrderGrpc;
import com.linmour.websocket.grpc.Result1;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

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
public class GrpcClientService3 {

    @GrpcClient("order-grpc")
    private OrderGrpc.OrderBlockingStub orderBlockingStub;
    ObjectMapper objectMapper = new ObjectMapper();
    public Result createOrder(final CreateOrderDto createOrderDto) throws Exception {

        String jsonString = objectMapper.writeValueAsString(createOrderDto);
        Object a = Object.newBuilder()
                .setData((jsonString))
                .build();
        System.out.println(a);

        Result1 result = orderBlockingStub.createOrder(a);
        return new Result<>(result.getCode(), result.getMsg(), result.getData());
    }


}


