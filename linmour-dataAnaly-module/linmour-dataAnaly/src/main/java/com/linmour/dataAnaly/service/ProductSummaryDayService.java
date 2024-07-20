package com.linmour.dataAnaly.service;

import com.linmour.dataAnaly.pojo.Do.ProductSummaryDay;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
* @author linmour
* @description 针对表【product_summary_day】的数据库操作Service
* @createDate 2024-04-03 16:11:59
*/
@Transactional(rollbackFor = Exception.class)
public interface ProductSummaryDayService extends IService<ProductSummaryDay> {

}
