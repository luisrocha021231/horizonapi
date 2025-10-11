package com.horizon.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horizon.api.entitites.VRegionsEntity;

@Repository
public interface VRegionsRepository extends JpaRepository<VRegionsEntity, Long> {
    
    List<VRegionsEntity> findByNameContainingIgnoreCase(String name);
}
