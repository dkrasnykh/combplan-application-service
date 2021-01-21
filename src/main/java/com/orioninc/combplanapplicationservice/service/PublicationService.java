package com.orioninc.combplanapplicationservice.service;

import com.orioninc.combplanapplicationservice.dto.PublicationDto;

public interface PublicationService {
    PublicationDto getPublicationById(Long id);

    PublicationDto getPublicationByAuthorIdTitle(Long authorId, String title);

    PublicationDto createPublication(PublicationDto publicationDto);

    PublicationDto updatePublication(PublicationDto publicationDto);

    void deletePublication(Long id);
}
