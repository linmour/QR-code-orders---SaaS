//package com.linmour.order.conf;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.common.SpringBootPropertiesConfigurationProperties;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.encrypt.SpringBootEncryptRuleConfigurationProperties;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.masterslave.SpringBootMasterSlaveRuleConfigurationProperties;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.shadow.SpringBootShadowRuleConfigurationProperties;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
//import org.apache.shardingsphere.transaction.spring.ShardingTransactionTypeScanner;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ComponentScan("org.apache.shardingsphere.spring.boot.converter")
//@EnableConfigurationProperties({
//        SpringBootShardingRuleConfigurationProperties.class,
//        SpringBootMasterSlaveRuleConfigurationProperties.class, SpringBootEncryptRuleConfigurationProperties.class,
//        SpringBootPropertiesConfigurationProperties.class, SpringBootShadowRuleConfigurationProperties.class})
//@ConditionalOnProperty(prefix = "spring.shardingsphere", name = "enabled", havingValue = "true", matchIfMissing = true)
//@AutoConfigureBefore(DataSourceAutoConfiguration.class)
//@RequiredArgsConstructor
//public class CustomShardingSphereAutoConfiguration {
//
//
//    /**
//     * Create transaction type scanner.
//     *
//     * @return transaction type scanner
//     */
//    @Bean
//    public ShardingTransactionTypeScanner transactionTypeScanner() {
//        return new ShardingTransactionTypeScanner();
//    }
//}
