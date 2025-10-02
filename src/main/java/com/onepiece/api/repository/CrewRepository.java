package com.onepiece.api.repository;

import com.onepiece.api.model.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Integer> {
    
    // Search by name (case insensitive)
    List<Crew> findByNameContainingIgnoreCase(String name);
    
    // Search by exact name
    Optional<Crew> findByName(String name);
    
    // Search by ship
    List<Crew> findByShipContainingIgnoreCase(String ship);
    
    // Custom query to get crews with most members
    @Query("SELECT c FROM Crew c LEFT JOIN c.characters ch GROUP BY c.id ORDER BY COUNT(ch.id) DESC")
    List<Crew> findAllOrderByMemberCount();
    
    // Query to get crews with a minimum number of members
    @Query("SELECT c FROM Crew c LEFT JOIN c.characters ch GROUP BY c.id HAVING COUNT(ch.id) >= :minMembers")
    List<Crew> findByMinMemberCount(@Param("minMembers") Long minMembers);
}

