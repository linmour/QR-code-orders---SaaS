package com.linmour.order.mq;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.service.OrderInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Objects;

import static com.linmour.security.constant.MqConstant.CREATE_ORDER_TOPIC;
import static com.linmour.security.constant.MqConstant.ORDER_PAY_TIMEOUT_TOPIC;
import static com.linmour.security.utils.SecurityUtils.setShopId;
import static com.linmour.order.constants.OrderConstant.CANCEL_ORDER;
import static com.linmour.order.constants.OrderConstant.PAYMENT;

@Component
public class ConsumerMq {

    @Resource
    private OrderInfoService orderInfoService;
    @Resource
    private ProducerMq producerMq;



    @Service
    @RocketMQMessageListener(topic = ORDER_PAY_TIMEOUT_TOPIC, consumerGroup = "orderPayTimeOut")
    public class orderPayTimeOut implements RocketMQListener<String> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(String orderId) {
            OrderInfo orderInfo = orderInfoService.getById(orderId);
            if (!Objects.equals(orderInfo.getPayStatus(), PAYMENT)){
                orderInfo.setPayStatus(CANCEL_ORDER);
            }
            orderInfoService.updateById(orderInfo);
        }
    }

    @Service
    @RocketMQMessageListener(topic = CREATE_ORDER_TOPIC,consumerGroup = "createOrder" )
    public class createOrder implements RocketMQListener<CreateOrderDto>{

        @Override
        public void onMessage(CreateOrderDto createOrderDto) {
            setShopId(createOrderDto.getShopId());
            //todo 这里消息发送成功后，在创建订单时如果发生了异常，那么就会出现订单丢失的情况，目前这种处理方式是捕获异常，然后通知生产者重新再次生产
            try{
                orderInfoService.createOrder(createOrderDto);
            }catch (Exception e){
//                Map<String,Object> map = new HashMap<>();
//                map.put(CREATE_ORDER_TOPIC, createOrderDto);
//                producerMq.error(map);
            }
        }
    }


}
