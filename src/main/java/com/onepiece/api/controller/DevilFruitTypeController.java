package com.onepiece.api.controller;

import com.onepiece.api.model.DevilFruitType;
import com.onepiece.api.repository.DevilFruitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devil-fruit-types")
@CrossOrigin(origins = "*")
public class DevilFruitTypeController {
    
    @Autowired
    private DevilFruitTypeRepository devilFruitTypeRepository;
    
    // GET /api/devil-fruit-types - Obtener todos los tipos de frutas del diablo
    @GetMapping
    public ResponseEntity<List<DevilFruitType>> getAllDevilFruitTypes() {
        List<DevilFruitType> devilFruitTypes = devilFruitTypeRepository.findAll();
        return ResponseEntity.ok(devilFruitTypes);
    }
    
    // GET /api/devil-fruit-types/{id} - Obtener tipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<DevilFruitType> getDevilFruitTypeById(@PathVariable Integer id) {
        Optional<DevilFruitType> devilFruitType = devilFruitTypeRepository.findById(id);
        return devilFruitType.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/devil-fruit-types - Crear nuevo tipo
    @PostMapping
    public ResponseEntity<DevilFruitType> createDevilFruitType(@Valid @RequestBody DevilFruitType devilFruitType) {
        DevilFruitType savedDevilFruitType = devilFruitTypeRepository.save(devilFruitType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevilFruitType);
    }
    
    // PUT /api/devil-fruit-types/{id} - Actualizar tipo
    @PutMapping("/{id}")
    public ResponseEntity<DevilFruitType> updateDevilFruitType(@PathVariable Integer id, 
                                                           @Valid @RequestBody DevilFruitType devilFruitTypeDetails) {
        Optional<DevilFruitType> optionalDevilFruitType = devilFruitTypeRepository.findById(id);
        if (optionalDevilFruitType.isPresent()) {
            DevilFruitType devilFruitType = optionalDevilFruitType.get();
            devilFruitType.setName(devilFruitTypeDetails.getName());
            DevilFruitType updatedDevilFruitType = devilFruitTypeRepository.save(devilFruitType);
            return ResponseEntity.ok(updatedDevilFruitType);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/devil-fruit-types/{id} - Eliminar tipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevilFruitType(@PathVariable Integer id) {
        Optional<DevilFruitType> devilFruitType = devilFruitTypeRepository.findById(id);
        if (devilFruitType.isPresent()) {
            devilFruitTypeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /api/devil-fruit-types/search?name={name} - Buscar por nombre
    @GetMapping("/search")
    public ResponseEntity<List<DevilFruitType>> searchDevilFruitTypesByName(@RequestParam String name) {
        List<DevilFruitType> devilFruitTypes = devilFruitTypeRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(devilFruitTypes);
    }
}

