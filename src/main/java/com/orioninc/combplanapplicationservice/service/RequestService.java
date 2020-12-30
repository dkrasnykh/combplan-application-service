package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto getRequestById(Long id);
    List<RequestDto> getAll();
    RequestDto createRequest(RequestDto requestDto);
    RequestDto updateRequest(RequestDto requestDto);
    void deleteRequest(Long id);
}
