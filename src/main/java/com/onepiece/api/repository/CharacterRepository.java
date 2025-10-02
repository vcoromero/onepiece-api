package com.onepiece.api.repository;

import com.onepiece.api.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    
    // Buscar por nombre (case insensitive)
    List<Character> findByNameContainingIgnoreCase(String name);
    
    // Buscar por nombre exacto
    Optional<Character> findByName(String name);
    
    // Buscar por tripulación
    List<Character> findByCrewId(Integer crewId);
    
    // Buscar por nombre de tripulación
    List<Character> findByCrewName(String crewName);
    
    // Buscar por rango de recompensa
    List<Character> findByBountyBetween(BigDecimal minBounty, BigDecimal maxBounty);
    
    // Buscar personajes con recompensa mayor a un valor
    List<Character> findByBountyGreaterThan(BigDecimal bounty);
    
    // Buscar personajes con recompensa menor a un valor
    List<Character> findByBountyLessThan(BigDecimal bounty);
    
    // Buscar personajes sin recompensa
    List<Character> findByBountyIsNull();
    
    // Query personalizada para buscar personajes con frutas del diablo
    @Query("SELECT DISTINCT c FROM Character c JOIN c.devilFruits df WHERE df.name LIKE %:fruitName%")
    List<Character> findByDevilFruitNameContaining(@Param("fruitName") String fruitName);
    
    // Query para buscar personajes con frutas de un tipo específico
    @Query("SELECT DISTINCT c FROM Character c JOIN c.devilFruits df WHERE df.type.name = :typeName")
    List<Character> findByDevilFruitType(@Param("typeName") String typeName);
    
    // Query para obtener personajes ordenados por recompensa (descendente)
    @Query("SELECT c FROM Character c WHERE c.bounty IS NOT NULL ORDER BY c.bounty DESC")
    List<Character> findAllOrderByBountyDesc();
    
    // Query para obtener el top N personajes con mayor recompensa
    @Query("SELECT c FROM Character c WHERE c.bounty IS NOT NULL ORDER BY c.bounty DESC")
    List<Character> findTopByBountyOrderByBountyDesc();
}
