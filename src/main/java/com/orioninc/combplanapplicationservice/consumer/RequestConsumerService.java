package com.orioninc.combplanapplicationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class RequestConsumerService {
    private final ObjectMapper mapper;
    private final RequestService requestService;
    private final Properties properties;
    private volatile boolean doneConsuming = false;
    private ExecutorService executorService;

    @Value("${kafka.topic.request}")
    private String topic;

    @Autowired
    public RequestConsumerService(ObjectMapper mapper, RequestService requestService, Properties properties) {
        this.mapper = mapper;
        this.requestService = requestService;
        this.properties = properties;
    }

    @PostConstruct
    public void consume() {
        executorService = Executors.newSingleThreadExecutor();
        Runnable consumerThread = getConsumerThread(properties);
        executorService.submit(consumerThread);
    }

    private Runnable getConsumerThread(Properties properties) {
        return () -> {
            Consumer<Long, String> consumer = null;
            try {
                consumer = new KafkaConsumer<>(properties);
                consumer.subscribe(Collections.singletonList(topic));
                while (!doneConsuming) {
                    ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(5000));
                    for (ConsumerRecord<Long, String> record : records) {
                        String value = record.value();
                        log.info("=> consumed {}", value);
                        RequestDto requestDto = readValue(value);
                        requestService.createRequest(requestDto);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (consumer != null) {
                    consumer.close();
                }
            }
        };
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
