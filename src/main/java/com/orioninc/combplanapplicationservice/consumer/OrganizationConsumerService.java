package com.orioninc.combplanapplicationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orioninc.combplanapplicationservice.dto.OrganizationDto;
import com.orioninc.combplanapplicationservice.service.OrganizationService;
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
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class OrganizationConsumerService {
    private final ObjectMapper mapper;
    private final Properties properties;
    private final OrganizationService organizationService;

    @Value("${kafka.topic.organization}")
    private String topic;

    @Autowired
    public OrganizationConsumerService(ObjectMapper mapper, Properties properties, OrganizationService service) {
        this.mapper = mapper;
        this.properties = properties;
        this.organizationService = service;
    }

    @PostConstruct
    public void consume() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable consumerThread = getConsumerThread(properties);
        executorService.submit(consumerThread);
    }

    private Runnable getConsumerThread(Properties properties) {
        return () -> {
            Consumer<Long, String> consumer = null;
            try {
                consumer = new KafkaConsumer<>(properties);
                consumer.subscribe(Collections.singletonList("application-service.organization"));
                while (true) {
                    ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(5000));
                    for (ConsumerRecord<Long, String> record : records) {
                        String value = record.value();
                        log.info("=> consumed {}", value);
                        OrganizationDto organizationDto = readValue(value);
                        if (Objects.isNull(organizationDto.getId())) {
                            organizationService.createOrganization(organizationDto);
                        } else {
                            organizationService.updateOrganization(organizationDto);
                        }
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

    private OrganizationDto readValue(String value) {
        try {
            return mapper.readValue(value, OrganizationDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to OrganizationDto failed: " + value);
        }
    }
}
