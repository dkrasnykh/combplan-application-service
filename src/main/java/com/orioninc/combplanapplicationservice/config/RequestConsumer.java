package com.orioninc.combplanapplicationservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@Component
public class RequestConsumer {
    private final ObjectMapper objectMapper;
    private final RequestService requestService;

    @Value("${kafka.group.id}")
    private String kafkaGroupId;

    @Autowired
    public RequestConsumer(ObjectMapper objectMapper, RequestService requestService) {
        this.objectMapper = objectMapper;
        this.requestService = requestService;
    }

    @PostConstruct
    public void consume() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("application-service.request"));

        while (true) {
            ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<Long, String> record : records) {
                String value = record.value();
                log.info("=> consumed {}", value);
                RequestDto requestDto = readValue(value);
                requestService.createRequest(requestDto);
            }
            consumer.commitSync();
        }
    }

    private RequestDto readValue(String value) {
        try {
            return objectMapper.readValue(value, RequestDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to RequestDto failed: " + value);
        }
    }
}
