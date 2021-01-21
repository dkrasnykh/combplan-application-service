package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.entity.Organization;
import com.orioninc.combplanapplicationservice.entity.Publication;
import com.orioninc.combplanapplicationservice.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoToRequest implements Converter<RequestDto, Request> {
    private final CustomConversionService conversionService;

    @Autowired
    public RequestDtoToRequest(CustomConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Request convert(RequestDto source) {
        Request target = new Request();
        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setApplicantId(source.getApplicant().getId());
        target.setOrganization(conversionService.convert(source.getOrganization(), Organization.class));
        target.setPublication(conversionService.convert(source.getPublication(), Publication.class));
        return target;
    }
}
