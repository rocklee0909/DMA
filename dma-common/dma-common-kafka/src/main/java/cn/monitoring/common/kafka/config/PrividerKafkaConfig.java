package cn.monitoring.common.kafka.config;

import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class PrividerKafkaConfig {

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Bean
    public KafkaAdminClient getKafkaAdminClient() {
        return (KafkaAdminClient)KafkaAdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

}
