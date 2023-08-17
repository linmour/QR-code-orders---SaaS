package com.linmour.order.mq;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProducerMq {

    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;

    // 建议正常规模项目统一用一个TOPIC
    private static final String topic = "LINMOUR_SCANORDER_TOPIC";

    // 直接注入使用，用于发送消息到broker服务器
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送延时消息（上面的发送同步消息，delayLevel的值就为0，因为不延时）
     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */

    public void orderDelay(OrderInfoDto msgBody, int delayLevel){
                                                                                                        // 超时时间    延时等级
        SendResult result = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msgBody).build(),3000,delayLevel);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            // 消息发送成功的处理逻辑
            System.out.println("消息发送成功，消息ID: " + result.getMsgId());
        } else {
            // 消息发送失败的处理逻辑
            System.out.println("消息发送失败，错误信息: " + result.getSendStatus().name());
        }

    }
}
