package com.onepiece.api.controller;

import com.onepiece.api.model.Crew;
import com.onepiece.api.repository.CrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crews")
@CrossOrigin(origins = "*")
public class CrewController {
    
    @Autowired
    private CrewRepository crewRepository;
    
    // GET /api/crews - Obtener todas las tripulaciones
    @GetMapping
    public ResponseEntity<List<Crew>> getAllCrews() {
        List<Crew> crews = crewRepository.findAll();
        return ResponseEntity.ok(crews);
    }
    
    // GET /api/crews/{id} - Get crew by ID
    @GetMapping("/{id}")
    public ResponseEntity<Crew> getCrewById(@PathVariable Integer id) {
        Optional<Crew> crew = crewRepository.findById(id);
        return crew.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/crews - Create new crew
    @PostMapping
    public ResponseEntity<Crew> createCrew(@Valid @RequestBody Crew crew) {
        Crew savedCrew = crewRepository.save(crew);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCrew);
    }
    
    // PUT /api/crews/{id} - Update crew
    @PutMapping("/{id}")
    public ResponseEntity<Crew> updateCrew(@PathVariable Integer id, 
                                         @Valid @RequestBody Crew crewDetails) {
        Optional<Crew> optionalCrew = crewRepository.findById(id);
        if (optionalCrew.isPresent()) {
            Crew crew = optionalCrew.get();
            crew.setName(crewDetails.getName());
            crew.setShip(crewDetails.getShip());
            Crew updatedCrew = crewRepository.save(crew);
            return ResponseEntity.ok(updatedCrew);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/crews/{id} - Delete crew
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Integer id) {
        Optional<Crew> crew = crewRepository.findById(id);
        if (crew.isPresent()) {
            crewRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /api/crews/search?name={name} - Buscar por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Crew>> searchCrewsByName(@RequestParam String name) {
        List<Crew> crews = crewRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(crews);
    }
    
    // GET /api/crews/ship?ship={ship} - Buscar por barco
    @GetMapping("/ship")
    public ResponseEntity<List<Crew>> searchCrewsByShip(@RequestParam String ship) {
        List<Crew> crews = crewRepository.findByShipContainingIgnoreCase(ship);
        return ResponseEntity.ok(crews);
    }
    
    // GET /api/crews/ordered-by-members - Get crews ordered by number of members
    @GetMapping("/ordered-by-members")
    public ResponseEntity<List<Crew>> getCrewsOrderedByMemberCount() {
        List<Crew> crews = crewRepository.findAllOrderByMemberCount();
        return ResponseEntity.ok(crews);
    }
}

