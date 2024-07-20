package com.linmour.dataAnaly.service;

import com.linmour.dataAnaly.pojo.Do.OrderSummaryTimePeriod;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
* @author linmour
* @description 针对表【order_summary_time_period】的数据库操作Service
* @createDate 2024-04-03 16:11:59
*/
@Transactional(rollbackFor = Exception.class)
public interface OrderSummaryTimePeriodService extends IService<OrderSummaryTimePeriod> {

}
