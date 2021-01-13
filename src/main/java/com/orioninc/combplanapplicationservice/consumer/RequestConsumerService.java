package com.orioninc.combplanapplicationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;

@Slf4j
@Service
public class RequestConsumerService {
    private final ObjectMapper mapper;
    private final RequestService requestService;
    private final KafkaConsumer<Long, String> consumer;

    @Value("${kafka.topic.request}")
    private String topic;

    @Autowired
    public RequestConsumerService(ObjectMapper mapper, RequestService requestService, KafkaConsumer<Long, String> consumer) {
        this.mapper = mapper;
        this.requestService = requestService;
        this.consumer = consumer;
    }

    @PostConstruct
    public void consume() {
        consumer.subscribe(Collections.singletonList(topic));
        try {
            while (true) {
                ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<Long, String> record : records) {
                    String value = record.value();
                    log.info("=> consumed {}", value);
                    RequestDto requestDto = readValue(value);
                    requestService.createRequest(requestDto);
                }
            }
        } finally {
            consumer.close();
        }
    }

    private RequestDto readValue(String value) {
        try {
            return mapper.readValue(value, RequestDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to RequestDto failed: " + value);
        }
    }
}
