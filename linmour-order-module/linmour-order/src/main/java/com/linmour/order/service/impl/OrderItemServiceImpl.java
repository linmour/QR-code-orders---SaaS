package com.linmour.order.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.order.mapper.OrderItemMapper;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author linmour
* @description 针对表【r_order_preduct】的数据库操作Service实现
* @createDate 2023-08-16 15:25:39
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService {

    @Resource
    private OrderItemMapper orderItemMapper;


    @Override
    public void postSql() {
        List<String> orderIds  = this.listObjs(
                new LambdaQueryWrapper<OrderItem>()
                        .select(OrderItem::getOrderId)
                        .groupBy(OrderItem::getOrderId) // 根据订单ID分组，实现去重
                        .le(OrderItem::getCreateTime,DateUtil.offsetDay(DateUtil.date(), -7))
                        // 这个是用来作为一个标志位，进行全库全表路由拿到所有数据
                        .le(OrderItem::getUpdateTime, "2999-11-20")
                , String::valueOf
        );
        if (orderIds.size()>0)
            orderItemMapper.delete(new LambdaQueryWrapper<OrderItem>().in(orderIds.size()>0,OrderItem::getOrderId,orderIds).le(OrderItem::getUpdateTime, "2999-11-20"));

    }


}




