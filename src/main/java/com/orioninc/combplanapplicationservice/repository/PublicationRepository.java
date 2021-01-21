package com.orioninc.combplanapplicationservice.repository;

import com.orioninc.combplanapplicationservice.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Publication findByAuthorAndTitle(Long authorId, String title);
}
