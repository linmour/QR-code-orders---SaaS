package com.linmour.order;

import com.linmour.order.mapper.OrderInfoMapper;
import com.linmour.order.pojo.Do.OrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ta {


    @Autowired
    private OrderInfoMapper b;


    @Test
    public void a(){
        OrderInfo orderInfo = new OrderInfo();
        OrderInfo orderInfo1 = new OrderInfo();
        orderInfo.setId("11111111111111111");
        orderInfo1.setId("22222222222222222");
        b.insert(orderInfo);
        b.insert(orderInfo1);
    }

}
