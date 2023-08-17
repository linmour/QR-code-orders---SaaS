package com.linmour.order.mq;

import com.linmour.order.convert.OrderInfoConvert;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.service.OrderInfoService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

import static com.linmour.order.constants.constant.CANCEL_ORDER;
import static com.linmour.order.constants.constant.PAYMENT;

@Component
public class ConsumerMq {

    @Resource
    private OrderInfoService orderInfoService;

    @Service
    @RocketMQMessageListener(topic = "LINMOUR_SCANORDER_TOPIC", consumerGroup = "Order_Group")
    public class ConsumerSend implements RocketMQListener<OrderInfoDto> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(OrderInfoDto dto) {
            OrderInfo orderInfo = OrderInfoConvert.IN.OrderInfoDtoToOrderInfo(dto);
            if (!Objects.equals(orderInfo.getPayStatus(), PAYMENT)){
                orderInfo.setPayStatus(CANCEL_ORDER);
            }
            orderInfoService.updateById(orderInfo);
        }
    }


}