package com.orioninc.combplanapplicationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orioninc.combplanapplicationservice.dto.PublicationDto;
import com.orioninc.combplanapplicationservice.service.PublicationService;
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
public class PublicationConsumerService {
    private final ObjectMapper mapper;
    private final Properties properties;
    private final PublicationService publicationService;

    @Value("${kafka.topic.publication}")
    private String topic;

    @Autowired
    public PublicationConsumerService(ObjectMapper mapper, Properties properties, PublicationService publicationService) {
        this.mapper = mapper;
        this.properties = properties;
        this.publicationService = publicationService;
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
                consumer.subscribe(Collections.singletonList("application-service.publication"));
                while (true) {
                    ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(5000));
                    for (ConsumerRecord<Long, String> record : records) {
                        String value = record.value();
                        log.info("=> consumed {}", value);
                        PublicationDto publicationDto = readValue(value);
                        if (Objects.isNull(publicationDto.getId())) {
                            publicationService.createPublication(publicationDto);
                        } else {
                            publicationService.updatePublication(publicationDto);
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

    private PublicationDto readValue(String value) {
        try {
            return mapper.readValue(value, PublicationDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to PublicationDto failed: " + value);
        }
    }
}
