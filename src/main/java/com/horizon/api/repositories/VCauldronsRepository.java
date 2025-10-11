package com.horizon.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horizon.api.entitites.VCauldronEntity;

@Repository
public interface VCauldronsRepository extends JpaRepository<VCauldronEntity, Long> {
 
    List<VCauldronEntity> findByNameContainingIgnoreCase(String name);
}
