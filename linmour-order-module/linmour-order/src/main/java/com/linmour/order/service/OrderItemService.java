package com.linmour.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.order.pojo.Do.OrderItem;

/**
* @author linmour
* @description 针对表【r_order_preduct】的数据库操作Service
* @createDate 2023-08-16 15:25:39
*/
//@ShardingTransactionType(TransactionType.BASE)
public interface OrderItemService extends IService<OrderItem> {

    void postSql();

}
