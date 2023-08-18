package com.linmour.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.order.pojo.Do.OrderProduct;
import com.linmour.order.service.OrderProductService;
import com.linmour.order.mapper.OrderProductMapper;
import org.springframework.stereotype.Service;

/**
* @author linmour
* @description 针对表【order_product】的数据库操作Service实现
* @createDate 2023-08-16 15:25:38
*/
@Service
public class OrderProductServiceImpl extends ServiceImpl<OrderProductMapper, OrderProduct>
    implements OrderProductService{

}




