package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.OrganizationDto;
import com.orioninc.combplanapplicationservice.entity.Organization;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganizationToOrganizationDto implements Converter<Organization, OrganizationDto> {
    @Override
    public OrganizationDto convert(Organization source) {
        OrganizationDto target = new OrganizationDto();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }
}
