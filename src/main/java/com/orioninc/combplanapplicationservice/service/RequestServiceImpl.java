package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.entity.Request;
import com.orioninc.combplanapplicationservice.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository repository) {
        this.requestRepository = repository;
    }

    @Override
    public RequestDto getRequestById(Long id) {
        return null;
    }

    @Override
    public RequestDto createRequest(RequestDto requestDto) {
        Request request = new Request();
        request.setTitle(requestDto.getTitle());
        request.setDescription(requestDto.getDescription());
        requestRepository.save(request);
        return requestDto;
    }

    @Override
    public RequestDto updateRequest(RequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteRequest(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request is not found"));
        requestRepository.delete(request);
    }
}
