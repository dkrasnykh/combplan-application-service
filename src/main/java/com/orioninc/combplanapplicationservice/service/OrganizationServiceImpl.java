package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.converter.CustomConversionService;
import com.orioninc.combplanapplicationservice.dto.OrganizationDto;
import com.orioninc.combplanapplicationservice.entity.Organization;
import com.orioninc.combplanapplicationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;
    private final CustomConversionService conversionService;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository repository, CustomConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    public OrganizationDto getOrganizationById(Long id) {
        Organization organization = repository.findById(id).orElseThrow(() -> new RuntimeException("Organization is not found"));
        return conversionService.convert(organization, OrganizationDto.class);
    }

    @Override
    public OrganizationDto getOrganizationByName(String name) {
        Organization organization = repository.findByName(name);
        return conversionService.convert(organization, OrganizationDto.class);
    }

    @Override
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        if (!Objects.isNull(getOrganizationByName(organizationDto.getName()))) {
            return organizationDto;
        }
        Organization organization = conversionService.convert(organizationDto, Organization.class);
        assert organization != null;
        return conversionService.convert(repository.save(organization), OrganizationDto.class);
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto organizationDto) {
        Organization organization = repository.findById(organizationDto.getId()).orElseThrow(() -> new RuntimeException("Organization is not found"));
        organization.setName(organizationDto.getName());
        return conversionService.convert(repository.save(organization), OrganizationDto.class);
    }

    @Override
    public void deleteOrganization(Long id) {
        Organization organization = repository.findById(id).orElseThrow(() -> new RuntimeException("Organization is not found"));
        repository.delete(organization);
    }
}
