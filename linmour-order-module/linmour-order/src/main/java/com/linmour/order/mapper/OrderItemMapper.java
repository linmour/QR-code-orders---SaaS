package com.linmour.order.mapper;

import com.linmour.order.pojo.Do.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author linmour
* @description 针对表【r_order_preduct】的数据库操作Mapper
* @createDate 2023-08-16 15:25:39
* @Entity com.linmour.order.pojo.Do.OrderItem
*/
public interface OrderItemMapper extends BaseMapper<OrderItem> {


    void postSql();

}




