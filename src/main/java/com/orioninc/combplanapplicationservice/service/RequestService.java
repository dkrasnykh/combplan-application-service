package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.dto.RequestDto;

public interface RequestService {
    RequestDto getRequestById(Long id);

    RequestDto createRequest(RequestDto requestDto);

    RequestDto updateRequest(RequestDto requestDto);

    void deleteRequest(Long id);
}
