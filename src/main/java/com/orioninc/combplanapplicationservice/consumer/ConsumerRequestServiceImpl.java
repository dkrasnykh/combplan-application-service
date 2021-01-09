package com.orioninc.combplanapplicationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerRequestServiceImpl implements ConsumerRequestService {
    private final KafkaTemplate<Long, RequestDto> kafkaRequestTemplate;
    private final ObjectMapper objectMapper;
    private final RequestService requestService;

    @Autowired
    public ConsumerRequestServiceImpl(KafkaTemplate<Long, RequestDto> kafkaRequestTemplate, ObjectMapper objectMapper, RequestService requestService) {
        this.kafkaRequestTemplate = kafkaRequestTemplate;
        this.objectMapper = objectMapper;
        this.requestService = requestService;
    }

    @Override
    @KafkaListener(id = "Request", topics = {"application-service.request"}, containerFactory = "singleFactory")
    public void consume(RequestDto dto) {
        log.info("=> consumed {}", writeValueAsString(dto));
        requestService.createRequest(dto);
    }

    private String writeValueAsString(RequestDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
