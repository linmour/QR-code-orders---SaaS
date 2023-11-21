package com.linmour.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.service.OrderItemService;
import com.linmour.order.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author linmour
* @description 针对表【r_order_preduct】的数据库操作Service实现
* @createDate 2023-08-16 15:25:39
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService {


}




