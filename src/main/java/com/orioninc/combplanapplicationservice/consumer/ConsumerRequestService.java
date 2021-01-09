package com.orioninc.combplanapplicationservice.consumer;

import com.orioninc.combplanapplicationservice.dto.RequestDto;

public interface ConsumerRequestService {
    void consume(RequestDto dto);
}
