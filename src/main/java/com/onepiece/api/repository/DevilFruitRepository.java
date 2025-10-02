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
    
    // Search by name (case insensitive)
    List<DevilFruit> findByNameContainingIgnoreCase(String name);
    
    // Search by exact name
    Optional<DevilFruit> findByName(String name);
    
    // Search by type
    List<DevilFruit> findByTypeId(Integer typeId);
    
    // Search by type name
    List<DevilFruit> findByTypeName(String typeName);
    
    // Search fruits with description
    List<DevilFruit> findByDescriptionIsNotNull();
    
    // Search fruits without description
    List<DevilFruit> findByDescriptionIsNull();
    
    // Custom query to search fruits by description
    @Query("SELECT df FROM DevilFruit df WHERE df.description LIKE %:description%")
    List<DevilFruit> findByDescriptionContaining(@Param("description") String description);
    
    // Query to get most popular fruits (with most users)
    @Query("SELECT df FROM DevilFruit df LEFT JOIN df.characters ch GROUP BY df.id ORDER BY COUNT(ch.id) DESC")
    List<DevilFruit> findAllOrderByUserCount();
}

