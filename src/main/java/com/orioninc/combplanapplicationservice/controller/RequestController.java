package com.orioninc.combplanapplicationservice.controller;

import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(value = "/{id}")
    public RequestDto getRequestById(@PathVariable("id") Long id){
        return requestService.getRequestById(id);
    }

    @PostMapping
    public RequestDto createRequest(@RequestBody RequestDto requestDto){
        return requestService.createRequest(requestDto);
    }

    @GetMapping
    public List<RequestDto> getAll(){
        return requestService.getAll();
    }

    @PutMapping
    public RequestDto updateRequest(@RequestBody RequestDto requestDto){
        return requestService.updateRequest(requestDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRequest(@PathVariable("id") Long id){
        requestService.deleteRequest(id);
    }
}
