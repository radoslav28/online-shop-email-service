package com.onlineshop.email.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic sendEmailTopic() {
        return TopicBuilder
                .name("sendEmail")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
