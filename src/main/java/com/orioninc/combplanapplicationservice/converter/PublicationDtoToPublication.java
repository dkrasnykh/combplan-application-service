package com.orioninc.combplanapplicationservice.converter;

import com.orioninc.combplanapplicationservice.dto.PublicationDto;
import com.orioninc.combplanapplicationservice.dto.UserDto;
import com.orioninc.combplanapplicationservice.entity.Publication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PublicationDtoToPublication implements Converter<PublicationDto, Publication> {
    @Override
    public Publication convert(PublicationDto source) {
        Publication target = new Publication();
        target.setId(source.getId());
        target.setAuthor(source.getAuthor().getId());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        Set<Long> coAuthors = source.getCoAuthors().stream()
                .map(UserDto::getId)
                .collect(Collectors.toSet());
        target.setCoAuthors(coAuthors);
        return target;
    }
}
