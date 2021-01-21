package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.converter.CustomConversionService;
import com.orioninc.combplanapplicationservice.dto.PublicationDto;
import com.orioninc.combplanapplicationservice.dto.UserDto;
import com.orioninc.combplanapplicationservice.entity.Publication;
import com.orioninc.combplanapplicationservice.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository repository;
    private final CustomConversionService conversionService;

    public PublicationServiceImpl(PublicationRepository publicationRepository, CustomConversionService conversionService) {
        this.repository = publicationRepository;
        this.conversionService = conversionService;
    }

    @Override
    public PublicationDto getPublicationById(Long id) {
        Publication publication = repository.findById(id).orElseThrow(() -> new RuntimeException("Publication is not found"));
        return conversionService.convert(publication, PublicationDto.class);
    }

    @Override
    public PublicationDto getPublicationByAuthorIdTitle(Long authorId, String title) {
        Publication publication = repository.findByAuthorAndTitle(authorId, title);
        return conversionService.convert(publication, PublicationDto.class);
    }

    @Override
    public PublicationDto createPublication(PublicationDto publicationDto) {
        Publication publication = conversionService.convert(publicationDto, Publication.class);
        return conversionService.convert(repository.save(publication), PublicationDto.class);
    }

    @Override
    public PublicationDto updatePublication(PublicationDto publicationDto) {
        Publication publication = repository.findById(publicationDto.getId()).orElseThrow(() -> new RuntimeException("Publication is not found"));
        publication.setId(publicationDto.getId());
        publication.setAuthor(publicationDto.getAuthor().getId());
        publication.setTitle(publicationDto.getTitle());
        publication.setDescription(publicationDto.getDescription());
        Set<Long> coAuthhors = publicationDto.getCoAuthors()
                .stream()
                .map(UserDto::getId)
                .collect(Collectors.toSet());
        publication.setCoAuthors(coAuthhors);
        return conversionService.convert(repository.save(publication), PublicationDto.class);
    }

    @Override
    public void deletePublication(Long id) {
        Publication publication = repository.findById(id).orElseThrow(() -> new RuntimeException("Publication is not found"));
        repository.delete(publication);
    }
}
