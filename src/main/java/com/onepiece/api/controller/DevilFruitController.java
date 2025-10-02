package com.onepiece.api.controller;

import com.onepiece.api.model.DevilFruit;
import com.onepiece.api.repository.DevilFruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devil-fruits")
@CrossOrigin(origins = "*")
public class DevilFruitController {
    
    @Autowired
    private DevilFruitRepository devilFruitRepository;
    
    // GET /api/devil-fruits - Obtener todas las frutas del diablo
    @GetMapping
    public ResponseEntity<List<DevilFruit>> getAllDevilFruits() {
        List<DevilFruit> devilFruits = devilFruitRepository.findAll();
        return ResponseEntity.ok(devilFruits);
    }
    
    // GET /api/devil-fruits/{id} - Obtener fruta del diablo por ID
    @GetMapping("/{id}")
    public ResponseEntity<DevilFruit> getDevilFruitById(@PathVariable Integer id) {
        Optional<DevilFruit> devilFruit = devilFruitRepository.findById(id);
        return devilFruit.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/devil-fruits - Crear nueva fruta del diablo
    @PostMapping
    public ResponseEntity<DevilFruit> createDevilFruit(@Valid @RequestBody DevilFruit devilFruit) {
        DevilFruit savedDevilFruit = devilFruitRepository.save(devilFruit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevilFruit);
    }
    
    // PUT /api/devil-fruits/{id} - Actualizar fruta del diablo
    @PutMapping("/{id}")
    public ResponseEntity<DevilFruit> updateDevilFruit(@PathVariable Integer id, 
                                                     @Valid @RequestBody DevilFruit devilFruitDetails) {
        Optional<DevilFruit> optionalDevilFruit = devilFruitRepository.findById(id);
        if (optionalDevilFruit.isPresent()) {
            DevilFruit devilFruit = optionalDevilFruit.get();
            devilFruit.setName(devilFruitDetails.getName());
            devilFruit.setType(devilFruitDetails.getType());
            devilFruit.setDescription(devilFruitDetails.getDescription());
            DevilFruit updatedDevilFruit = devilFruitRepository.save(devilFruit);
            return ResponseEntity.ok(updatedDevilFruit);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/devil-fruits/{id} - Eliminar fruta del diablo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevilFruit(@PathVariable Integer id) {
        Optional<DevilFruit> devilFruit = devilFruitRepository.findById(id);
        if (devilFruit.isPresent()) {
            devilFruitRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /api/devil-fruits/search?name={name} - Buscar por nombre
    @GetMapping("/search")
    public ResponseEntity<List<DevilFruit>> searchDevilFruitsByName(@RequestParam String name) {
        List<DevilFruit> devilFruits = devilFruitRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(devilFruits);
    }
    
    // GET /api/devil-fruits/type/{typeId} - Buscar por tipo
    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<DevilFruit>> getDevilFruitsByType(@PathVariable Integer typeId) {
        List<DevilFruit> devilFruits = devilFruitRepository.findByTypeId(typeId);
        return ResponseEntity.ok(devilFruits);
    }
    
    // GET /api/devil-fruits/type-name/{typeName} - Buscar por nombre de tipo
    @GetMapping("/type-name/{typeName}")
    public ResponseEntity<List<DevilFruit>> getDevilFruitsByTypeName(@PathVariable String typeName) {
        List<DevilFruit> devilFruits = devilFruitRepository.findByTypeName(typeName);
        return ResponseEntity.ok(devilFruits);
    }
    
    // GET /api/devil-fruits/with-description - Search fruits with description
    @GetMapping("/with-description")
    public ResponseEntity<List<DevilFruit>> getDevilFruitsWithDescription() {
        List<DevilFruit> devilFruits = devilFruitRepository.findByDescriptionIsNotNull();
        return ResponseEntity.ok(devilFruits);
    }
    
    // GET /api/devil-fruits/without-description - Search fruits without description
    @GetMapping("/without-description")
    public ResponseEntity<List<DevilFruit>> getDevilFruitsWithoutDescription() {
        List<DevilFruit> devilFruits = devilFruitRepository.findByDescriptionIsNull();
        return ResponseEntity.ok(devilFruits);
    }
    
    // GET /api/devil-fruits/ordered-by-users - Get fruits ordered by number of users
    @GetMapping("/ordered-by-users")
    public ResponseEntity<List<DevilFruit>> getDevilFruitsOrderedByUserCount() {
        List<DevilFruit> devilFruits = devilFruitRepository.findAllOrderByUserCount();
        return ResponseEntity.ok(devilFruits);
    }
}

