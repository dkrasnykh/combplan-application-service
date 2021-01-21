package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.OrganizationDto;
import com.orioninc.combplanapplicationservice.dto.PublicationDto;
import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.dto.UserDto;
import com.orioninc.combplanapplicationservice.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestToRequestDto implements Converter<Request, RequestDto> {
    private final CustomConversionService conversionService;

    @Autowired
    public RequestToRequestDto(CustomConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public RequestDto convert(Request source) {
        RequestDto target = new RequestDto();
        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setApplicant(new UserDto(source.getId()));
        target.setOrganization(conversionService.convert(source.getOrganization(), OrganizationDto.class));
        target.setPublication(conversionService.convert(source.getPublication(), PublicationDto.class));
        return target;
    }
}
