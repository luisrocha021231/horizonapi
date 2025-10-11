package com.horizon.api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horizon.api.entitites.VCharactersEntity;

@Repository
public interface VCharactersRepository extends JpaRepository<VCharactersEntity, Long> {
    
    Optional<VCharactersEntity> findBySlug(String name);

    //List<VCharactersEntity> findByNameContainingIgnoreCase(String name);
    Page<VCharactersEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
