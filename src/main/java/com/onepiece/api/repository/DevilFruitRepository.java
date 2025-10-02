package com.onepiece.api.repository;

import com.onepiece.api.model.DevilFruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevilFruitRepository extends JpaRepository<DevilFruit, Integer> {
    
    // Buscar por nombre (case insensitive)
    List<DevilFruit> findByNameContainingIgnoreCase(String name);
    
    // Buscar por nombre exacto
    Optional<DevilFruit> findByName(String name);
    
    // Buscar por tipo
    List<DevilFruit> findByTypeId(Integer typeId);
    
    // Buscar por nombre de tipo
    List<DevilFruit> findByTypeName(String typeName);
    
    // Buscar frutas con descripción
    List<DevilFruit> findByDescriptionIsNotNull();
    
    // Buscar frutas sin descripción
    List<DevilFruit> findByDescriptionIsNull();
    
    // Query personalizada para buscar frutas por descripción
    @Query("SELECT df FROM DevilFruit df WHERE df.description LIKE %:description%")
    List<DevilFruit> findByDescriptionContaining(@Param("description") String description);
    
    // Query para obtener frutas más populares (con más usuarios)
    @Query("SELECT df FROM DevilFruit df LEFT JOIN df.characters ch GROUP BY df.id ORDER BY COUNT(ch.id) DESC")
    List<DevilFruit> findAllOrderByUserCount();
}

