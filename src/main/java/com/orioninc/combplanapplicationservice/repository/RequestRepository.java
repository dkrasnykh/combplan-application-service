package com.orioninc.combplanapplicationservice.repository;

import com.orioninc.combplanapplicationservice.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}
