package com.horizon.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horizon.api.entitites.VAreasEntity;

@Repository
public interface VAreasRepository extends JpaRepository<VAreasEntity, Long> {
    
    List<VAreasEntity> findByNameContainingIgnoreCase(String name);
}
