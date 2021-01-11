package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.entity.Request;
import com.orioninc.combplanapplicationservice.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    //private final CustomConversionService conversionService;

    @Autowired
    public RequestServiceImpl(RequestRepository repository) {

        this.requestRepository = repository;
        //this.conversionService = conversionService;
    }

    @Override
    public RequestDto getRequestById(Long id) {
        /*
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request is not found"));
        return conversionService.convert(request, RequestDto.class);
        */
        return null;
    }

    @Override
    public List<RequestDto> getAll() {
        /*
        List<Request> requests = requestRepository.findAll();
        return requests.stream().map(request -> conversionService.convert(request, RequestDto.class)).collect(Collectors.toList());
        */
        return null;
    }

    @Override
    public RequestDto createRequest(RequestDto requestDto) {
        /*
        Request request = conversionService.convert(requestDto, Request.class);
        return conversionService.convert(requestRepository.save(request), RequestDto.class);
        */
        Request request = new Request();
        request.setTitle(requestDto.getTitle());
        request.setDescription(requestDto.getDescription());
        requestRepository.save(request);
        return requestDto;
    }

    @Override
    public RequestDto updateRequest(RequestDto requestDto) {
        /*
        Request request = requestRepository.findById(requestDto.getId()).orElseThrow(()->new RuntimeException("Request is not found"));
        request.setTitle(requestDto.getTitle());
        request.setDescription(requestDto.getDescription());
        return conversionService.convert(requestRepository.save(request), RequestDto.class);
         */
        return null;
    }

    @Override
    public void deleteRequest(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request is not found"));
        requestRepository.delete(request);
    }
}
