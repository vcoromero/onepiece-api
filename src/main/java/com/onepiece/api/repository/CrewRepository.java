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
    
    // Buscar por nombre (case insensitive)
    List<Crew> findByNameContainingIgnoreCase(String name);
    
    // Buscar por nombre exacto
    Optional<Crew> findByName(String name);
    
    // Buscar por barco
    List<Crew> findByShipContainingIgnoreCase(String ship);
    
    // Query personalizada para obtener tripulaciones con más miembros
    @Query("SELECT c FROM Crew c LEFT JOIN c.characters ch GROUP BY c.id ORDER BY COUNT(ch.id) DESC")
    List<Crew> findAllOrderByMemberCount();
    
    // Query para obtener tripulaciones con un número mínimo de miembros
    @Query("SELECT c FROM Crew c LEFT JOIN c.characters ch GROUP BY c.id HAVING COUNT(ch.id) >= :minMembers")
    List<Crew> findByMinMemberCount(@Param("minMembers") Long minMembers);
}

