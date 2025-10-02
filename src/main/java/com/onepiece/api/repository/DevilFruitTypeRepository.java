package com.onepiece.api.repository;

import com.onepiece.api.model.DevilFruitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevilFruitTypeRepository extends JpaRepository<DevilFruitType, Integer> {
    
    // Buscar por nombre (case insensitive)
    List<DevilFruitType> findByNameContainingIgnoreCase(String name);
    
    // Buscar por nombre exacto
    Optional<DevilFruitType> findByName(String name);
}

