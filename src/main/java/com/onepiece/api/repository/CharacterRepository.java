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
    
    // Search by name (case insensitive)
    List<Character> findByNameContainingIgnoreCase(String name);
    
    // Search by exact name
    Optional<Character> findByName(String name);
    
    // Search by crew
    List<Character> findByCrewId(Integer crewId);
    
    // Search by crew name
    List<Character> findByCrewName(String crewName);
    
    // Search by bounty range
    List<Character> findByBountyBetween(BigDecimal minBounty, BigDecimal maxBounty);
    
    // Search characters with bounty greater than a value
    List<Character> findByBountyGreaterThan(BigDecimal bounty);
    
    // Search characters with bounty less than a value
    List<Character> findByBountyLessThan(BigDecimal bounty);
    
    // Search characters without bounty
    List<Character> findByBountyIsNull();
    
    // Custom query to search characters with devil fruits
    @Query("SELECT DISTINCT c FROM Character c JOIN c.devilFruits df WHERE df.name LIKE %:fruitName%")
    List<Character> findByDevilFruitNameContaining(@Param("fruitName") String fruitName);
    
    // Query to search characters with fruits of a specific type
    @Query("SELECT DISTINCT c FROM Character c JOIN c.devilFruits df WHERE df.type.name = :typeName")
    List<Character> findByDevilFruitType(@Param("typeName") String typeName);
    
    // Query to get characters ordered by bounty (descending)
    @Query("SELECT c FROM Character c WHERE c.bounty IS NOT NULL ORDER BY c.bounty DESC")
    List<Character> findAllOrderByBountyDesc();
    
    // Query to get top N characters with highest bounty
    @Query("SELECT c FROM Character c WHERE c.bounty IS NOT NULL ORDER BY c.bounty DESC")
    List<Character> findTopByBountyOrderByBountyDesc();
}
