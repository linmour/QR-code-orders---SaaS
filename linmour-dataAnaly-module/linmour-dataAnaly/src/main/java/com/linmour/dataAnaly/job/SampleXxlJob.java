package com.linmour.dataAnaly.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linmour.dataAnaly.mapper.OrderSummaryTimePeriodMapper;
import com.linmour.dataAnaly.mapper.ProductSummaryDayMapper;
import com.linmour.dataAnaly.pojo.Do.OrderSummaryDay;
import com.linmour.dataAnaly.pojo.Do.OrderSummaryTimePeriod;
import com.linmour.dataAnaly.pojo.Do.ProductSummaryDay;
import com.linmour.dataAnaly.service.OrderSummaryDayService;
import com.linmour.dataAnaly.service.OrderSummaryTimePeriodService;
import com.linmour.dataAnaly.service.ProductSummaryDayService;
import com.linmour.security.utils.RedisCache;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
public class SampleXxlJob {

    @Resource
    private RedisCache a;

    //注入为空
    public static RedisCache redisCache;

    @PostConstruct
    public void b() {
        redisCache = this.a;
    }

    @Resource
    private OrderSummaryDayService orderSummaryDayService;
    @Resource
    private OrderSummaryTimePeriodService orderSummaryTimePeriodService;
    @Resource
    private ProductSummaryDayService productSummaryDayService;


    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
        List<String> matchingKeys = redisCache.getMatchingKeys("dataAnaly:productSummaryDay:*");
        List<String> matchingKeys1 = redisCache.getMatchingKeys("dataAnaly:orderSummaryDay:*");
        List<String> matchingKeys2 = redisCache.getMatchingKeys("dataAnaly:orderSummaryTimePeriod:*");

        a(matchingKeys);
        a(matchingKeys1);
        a(matchingKeys2);

        return ReturnT.SUCCESS;
    }

    public void a(List<String> matchingKeys) {
        matchingKeys.forEach(m -> {
            String[] segments = m.split(":");
            String shopId = "";
            String timePeriod = "";
            if (segments.length >= 3)
                shopId = segments[2];
            if (segments.length >= 4)
                timePeriod = segments[3];

            Map<String, Object> allHash1 = redisCache.getAllHash(m);
            String s = JSON.toJSONString(allHash1);
            if (m.contains("productSummaryDay")) {
                ArrayList<ProductSummaryDay> productSummaryDays = new ArrayList<>();
                for (String key : allHash1.keySet()) {
                    Object value = allHash1.get(key);
                    ProductSummaryDay productSummaryDay = new ProductSummaryDay();
                    productSummaryDay.setName(key);
                    productSummaryDay.setNum((Integer) value);
                    productSummaryDay.setShopId(Long.valueOf(shopId));
                    productSummaryDay.setDate(new Date());
                    productSummaryDays.add(productSummaryDay);
                }
                productSummaryDayService.saveBatch(productSummaryDays);

            } else if (m.contains("orderSummaryDay")) {
                OrderSummaryDay orderSummaryDay = JSON.parseObject(s, OrderSummaryDay.class);
                orderSummaryDay.setDate(new Date());
                orderSummaryDay.setShopId(Long.valueOf(shopId));
                orderSummaryDayService.save(orderSummaryDay);
            } else if (m.contains("orderSummaryTimePeriod")) {
                OrderSummaryTimePeriod orderSummaryTimePeriod = JSON.parseObject(s, OrderSummaryTimePeriod.class);
                orderSummaryTimePeriod.setShopId(Long.valueOf(shopId));
                orderSummaryTimePeriod.setTimePeriod(timePeriod);
                orderSummaryTimePeriodService.save(orderSummaryTimePeriod);
            }
        });
        //todo 保证插入和删除的统一
        List<String> matchingKeys2 = redisCache.getMatchingKeys("dataAnaly:*");
        matchingKeys2.forEach(m ->{
            redisCache.deleteObject(m);
        });
    }


}

