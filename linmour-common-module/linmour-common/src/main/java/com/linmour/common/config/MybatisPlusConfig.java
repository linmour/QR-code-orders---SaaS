package com.linmour.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.linmour.security.utils.SecurityUtils.getShopId;


@Configuration
@Component
public class MybatisPlusConfig  {
    @Bean
    public InsertBatchSqlInjector easySqlInjector () {
        return new InsertBatchSqlInjector();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                Long shopId = getShopId();
                if (shopId == null){
                    shopId = null;
                }
                return new LongValue(shopId);
            }

            @Override
            public String getTenantIdColumn() {

                return "shop_id";
            }

            @Override
            public boolean ignoreTable(String tableName) {
                MappedStatement mappedStatement;
                //忽略掉这张表
                String[] tables = {"system_shop", "system_merchant", "system_menu", "system_customer","order_item",
                "non_value_spec","product_spec","r_product_non_value_spec","r_product_value_spec","spec_sort","value_spec",
                "system_dict_type","system_dict_data"};
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
}