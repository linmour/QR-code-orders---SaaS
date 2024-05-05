package com.linmour.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class MybatisPlusConfig {

    @Bean
    public InsertBatchSqlInjector easySqlInjector() {
        return new InsertBatchSqlInjector();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                Long shopId = getShopId();
                if (shopId != null) {
                    return new LongValue(shopId);
                }
                return new LongValue(0);
            }

            @Override
            public String getTenantIdColumn() {
                if (getShopId() != null) {
                    return "shop_id"; // 返回真实的字段名
                }
                return "deleted"; // 返回一个不存在的列名或空字符串
            }

            @Override
            public boolean ignoreTable(String tableName) {
                // 忽略掉这张表
                String[] tables = {"system_shop", "system_merchant", "system_menu", "system_customer", "order_item",
                        "product_spec", "spec_sort", "system_dict_type", "system_dict_data", "product_spec_option",
                        "product_spec_sort", "r_product_inventoty"};
                for (String table : tables) {
                    if (table.equalsIgnoreCase(tableName)) {
                        return true;
                    }
                }
                return false;
            }
        }));
        return interceptor;
    }

    private Long getShopId() {
        // 返回你获取 shopId 的逻辑，例如从当前用户的上下文中获取
        // 如果 shopId 不为 null，执行自动拼接 SQL；否则不进行租户拼接
        return null;
    }
}