package com.onepiece.api.controller;

import com.onepiece.api.model.Character;
import com.onepiece.api.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "*")
public class CharacterController {
    
    @Autowired
    private CharacterService characterService;
    
    // GET /api/characters - Obtener todos los personajes
    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/{id} - Obtener personaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Integer id) {
        Optional<Character> character = characterService.getCharacterById(id);
        return character.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/characters - Crear nuevo personaje
    @PostMapping
    public ResponseEntity<Character> createCharacter(@Valid @RequestBody Character character) {
        Character savedCharacter = characterService.createCharacter(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
    }
    
    // PUT /api/characters/{id} - Actualizar personaje
    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Integer id, 
                                                   @Valid @RequestBody Character characterDetails) {
        Character updatedCharacter = characterService.updateCharacter(id, characterDetails);
        if (updatedCharacter != null) {
            return ResponseEntity.ok(updatedCharacter);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/characters/{id} - Eliminar personaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer id) {
        boolean deleted = characterService.deleteCharacter(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /api/characters/search?name={name} - Buscar por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Character>> searchCharactersByName(@RequestParam String name) {
        List<Character> characters = characterService.searchCharactersByName(name);
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/crew/{crewId} - Search by crew
    @GetMapping("/crew/{crewId}")
    public ResponseEntity<List<Character>> getCharactersByCrew(@PathVariable Integer crewId) {
        List<Character> characters = characterService.getCharactersByCrew(crewId);
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/crew-name/{crewName} - Search by crew name
    @GetMapping("/crew-name/{crewName}")
    public ResponseEntity<List<Character>> getCharactersByCrewName(@PathVariable String crewName) {
        List<Character> characters = characterService.getCharactersByCrewName(crewName);
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/bounty-range?min={min}&max={max} - Buscar por rango de recompensa
    @GetMapping("/bounty-range")
    public ResponseEntity<List<Character>> getCharactersByBountyRange(
            @RequestParam BigDecimal min, 
            @RequestParam BigDecimal max) {
        List<Character> characters = characterService.getCharactersByBountyRange(min, max);
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/bounty-greater-than?bounty={bounty} - Buscar por recompensa mayor
    @GetMapping("/bounty-greater-than")
    public ResponseEntity<List<Character>> getCharactersWithBountyGreaterThan(@RequestParam BigDecimal bounty) {
        List<Character> characters = characterService.getCharactersWithBountyGreaterThan(bounty);
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/no-bounty - Buscar personajes sin recompensa
    @GetMapping("/no-bounty")
    public ResponseEntity<List<Character>> getCharactersWithoutBounty() {
        List<Character> characters = characterService.getCharactersWithoutBounty();
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/devil-fruit?fruit={fruit} - Buscar por fruta del diablo
    @GetMapping("/devil-fruit")
    public ResponseEntity<List<Character>> searchCharactersByDevilFruit(@RequestParam String fruit) {
        List<Character> characters = characterService.searchCharactersByDevilFruit(fruit);
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/devil-fruit-type?type={type} - Buscar por tipo de fruta del diablo
    @GetMapping("/devil-fruit-type")
    public ResponseEntity<List<Character>> getCharactersByDevilFruitType(@RequestParam String type) {
        List<Character> characters = characterService.getCharactersByDevilFruitType(type);
        return ResponseEntity.ok(characters);
    }
    
    // GET /api/characters/ordered-by-bounty - Obtener personajes ordenados por recompensa
    @GetMapping("/ordered-by-bounty")
    public ResponseEntity<List<Character>> getCharactersOrderedByBounty() {
        List<Character> characters = characterService.getCharactersOrderedByBounty();
        return ResponseEntity.ok(characters);
    }
    
    // POST /api/characters/{id}/assign-crew/{crewId} - Assign crew
    @PostMapping("/{id}/assign-crew/{crewId}")
    public ResponseEntity<Character> assignCrewToCharacter(@PathVariable Integer id, @PathVariable Integer crewId) {
        Character character = characterService.assignCrewToCharacter(id, crewId);
        if (character != null) {
            return ResponseEntity.ok(character);
        }
        return ResponseEntity.notFound().build();
    }
    
    // POST /api/characters/{id}/assign-fruit/{fruitId} - Asignar fruta del diablo
    @PostMapping("/{id}/assign-fruit/{fruitId}")
    public ResponseEntity<Character> assignDevilFruitToCharacter(@PathVariable Integer id, @PathVariable Integer fruitId) {
        Character character = characterService.assignDevilFruitToCharacter(id, fruitId);
        if (character != null) {
            return ResponseEntity.ok(character);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/characters/{id}/remove-fruit/{fruitId} - Remover fruta del diablo
    @DeleteMapping("/{id}/remove-fruit/{fruitId}")
    public ResponseEntity<Character> removeDevilFruitFromCharacter(@PathVariable Integer id, @PathVariable Integer fruitId) {
        Character character = characterService.removeDevilFruitFromCharacter(id, fruitId);
        if (character != null) {
            return ResponseEntity.ok(character);
        }
        return ResponseEntity.notFound().build();
    }
}
