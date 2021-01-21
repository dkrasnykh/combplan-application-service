package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.OrganizationDto;
import com.orioninc.combplanapplicationservice.entity.Organization;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganizationDtoToOrganization implements Converter<OrganizationDto, Organization> {
    @Override
    public Organization convert(OrganizationDto source) {
        Organization organization = new Organization();
        organization.setId(source.getId());
        organization.setName(source.getName());
        return organization;
    }
}
