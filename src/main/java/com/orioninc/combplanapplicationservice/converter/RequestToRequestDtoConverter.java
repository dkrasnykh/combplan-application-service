package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.entity.Request;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestToRequestDtoConverter implements Converter<Request, RequestDto> {
    @Override
    public RequestDto convert(Request request) {
        RequestDto target = new RequestDto();
        target.setId(request.getId());
        target.setTitle(request.getTitle());
        target.setDescription(request.getDescription());
        return target;
    }
}
