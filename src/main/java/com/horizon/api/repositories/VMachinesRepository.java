package com.horizon.api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horizon.api.entitites.VMachinesEntity;

@Repository
public interface VMachinesRepository extends JpaRepository<VMachinesEntity, Long> {

    Optional<VMachinesEntity> findBySlug(String slug);

    Page<VMachinesEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
