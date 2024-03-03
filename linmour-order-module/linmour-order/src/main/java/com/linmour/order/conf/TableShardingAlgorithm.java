//package com.linmour.order.conf;
//
//import com.google.common.collect.Range;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@Slf4j
//public class TableShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {
//
//
//
//    @Override
//    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<String> complexKeysShardingValue) {
//
//        Range<String> updateTime = complexKeysShardingValue.getColumnNameAndRangeValuesMap().get("update_time");
//
//        if (updateTime != null && updateTime.contains("2999-11-20"))
//            return collection;
//
//
//            String orderId = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get("order_id").stream().toArray()[0].toString();
//            int i = Math.abs(orderId.hashCode()) % 2;
//            Collection<String> tables = new ArrayList<>();
//            tables.add("order_item_"+i);
//            return tables;
//
//    }
//}