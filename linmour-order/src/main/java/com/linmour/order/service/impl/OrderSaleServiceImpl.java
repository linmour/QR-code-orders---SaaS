package com.linmour.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.order.pojo.Do.OrderSale;
import com.linmour.order.service.OrderSaleService;
import com.linmour.order.mapper.OrderSaleMapper;
import org.springframework.stereotype.Service;

/**
* @author linmour
* @description 针对表【order_sale】的数据库操作Service实现
* @createDate 2023-08-16 15:25:39
*/
@Service
public class OrderSaleServiceImpl extends ServiceImpl<OrderSaleMapper, OrderSale>
    implements OrderSaleService{

}




