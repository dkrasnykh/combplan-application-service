package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.converter.CustomConversionService;
import com.orioninc.combplanapplicationservice.dto.OrganizationDto;
import com.orioninc.combplanapplicationservice.dto.PublicationDto;
import com.orioninc.combplanapplicationservice.dto.RequestDto;
import com.orioninc.combplanapplicationservice.entity.Organization;
import com.orioninc.combplanapplicationservice.entity.Publication;
import com.orioninc.combplanapplicationservice.entity.Request;
import com.orioninc.combplanapplicationservice.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final OrganizationService organizationService;
    private final PublicationService publicationService;
    private final CustomConversionService conversionService;

    @Autowired
    public RequestServiceImpl(RequestRepository repository, OrganizationService organizationService, PublicationService publicationService, CustomConversionService conversionService) {
        this.requestRepository = repository;
        this.organizationService = organizationService;
        this.publicationService = publicationService;
        this.conversionService = conversionService;
    }

    @Override
    public RequestDto getRequestById(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request is not found"));
        return conversionService.convert(request, RequestDto.class);
    }

    @Override
    public RequestDto createRequest(RequestDto requestDto) {
        Request request = new Request();
        OrganizationDto organizationDto = Objects.isNull(requestDto.getOrganization()
                .getId()) ? organizationService.createOrganization(requestDto.getOrganization()) : requestDto.getOrganization();
        request.setOrganization(conversionService.convert(organizationDto, Organization.class));
        PublicationDto publicationDto = Objects.isNull(requestDto.getPublication()
                .getId()) ? publicationService.createPublication(requestDto.getPublication()) : requestDto.getPublication();
        request.setPublication(conversionService.convert(publicationDto, Publication.class));
        request.setStatus(requestDto.getStatus());
        request.setApplicantId(requestDto.getApplicant().getId());
        return conversionService.convert(requestRepository.save(request), RequestDto.class);
    }

    @Override
    public RequestDto updateRequest(RequestDto requestDto) {
        Request request = requestRepository.findById(requestDto.getId()).orElseThrow(() -> new RuntimeException("Request is not found"));
        OrganizationDto organizationDto = Objects.isNull(requestDto.getOrganization()
                .getId()) ? organizationService.createOrganization(requestDto.getOrganization()) : requestDto.getOrganization();
        request.setOrganization(conversionService.convert(organizationDto, Organization.class));
        PublicationDto publicationDto = Objects.isNull(requestDto.getPublication()) ? publicationService.createPublication(requestDto.getPublication()) : requestDto.getPublication();
        request.setPublication(conversionService.convert(publicationDto, Publication.class));
        request.setStatus(requestDto.getStatus());
        request.setApplicantId(requestDto.getApplicant().getId());
        return conversionService.convert(requestRepository.save(request), RequestDto.class);
    }

    @Override
    public void deleteRequest(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request is not found"));
        requestRepository.delete(request);
    }
}
