package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.dto.OrganizationDto;

public interface OrganizationService {
    OrganizationDto getOrganizationById(Long id);

    OrganizationDto getOrganizationByName(String name);

    OrganizationDto createOrganization(OrganizationDto organizationDto);

    OrganizationDto updateOrganization(OrganizationDto organizationDto);

    void deleteOrganization(Long id);
}
