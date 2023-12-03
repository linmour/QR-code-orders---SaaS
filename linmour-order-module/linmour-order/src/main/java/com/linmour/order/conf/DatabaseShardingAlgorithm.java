package com.linmour.order.conf;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseShardingAlgorithm  implements ComplexKeysShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        Range<String> updateTime = complexKeysShardingValue.getColumnNameAndRangeValuesMap().get("update_time");

        if (updateTime != null && updateTime.contains("2999-11-20"))
            return collection;

            String shopId = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get("shop_id").stream().toArray().toString();
            int i = Math.abs(shopId.hashCode()) % 2;
            Collection<String> dbs = new ArrayList<>();
            dbs.add("ds"+i);
            return dbs;

    }
}
