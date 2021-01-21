package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.PublicationDto;
import com.orioninc.combplanapplicationservice.dto.UserDto;
import com.orioninc.combplanapplicationservice.entity.Publication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PublicationToPublicationDto implements Converter<Publication, PublicationDto> {
    @Override
    public PublicationDto convert(Publication source) {
        PublicationDto target = new PublicationDto();
        target.setId(source.getId());
        target.setAuthor(new UserDto(source.getAuthor()));
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        Set<UserDto> coAuthors = source.getCoAuthors()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toSet());
        target.setCoAuthors(coAuthors);
        return target;
    }
}
