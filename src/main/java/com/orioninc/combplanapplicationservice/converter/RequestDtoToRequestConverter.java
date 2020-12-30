package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.entity.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoToRequestConverter implements Converter<RequestDto, Request> {
    @Override
    public Request convert(RequestDto requestDto) {
        Request target = new Request();
        //target.setId(requestDto.getId());
        target.setTitle(requestDto.getTitle());
        target.setDescription(requestDto.getDescription());
        return target;
    }
}
