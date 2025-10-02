package com.onepiece.api.service;

import com.onepiece.api.model.Character;
import com.onepiece.api.model.Crew;
import com.onepiece.api.model.DevilFruit;
import com.onepiece.api.repository.CharacterRepository;
import com.onepiece.api.repository.CrewRepository;
import com.onepiece.api.repository.DevilFruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CharacterService {
    
    @Autowired
    private CharacterRepository characterRepository;
    
    @Autowired
    private CrewRepository crewRepository;
    
    @Autowired
    private DevilFruitRepository devilFruitRepository;
    
    // Obtener todos los personajes
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }
    
    // Obtener personaje por ID
    public Optional<Character> getCharacterById(Integer id) {
        return characterRepository.findById(id);
    }
    
    // Crear nuevo personaje
    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }
    
    // Actualizar personaje
    public Character updateCharacter(Integer id, Character characterDetails) {
        Optional<Character> optionalCharacter = characterRepository.findById(id);
        if (optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            character.setName(characterDetails.getName());
            character.setBounty(characterDetails.getBounty());
            character.setCrew(characterDetails.getCrew());
            return characterRepository.save(character);
        }
        return null;
    }
    
    // Eliminar personaje
    public boolean deleteCharacter(Integer id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent()) {
            characterRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Buscar personajes por nombre
    public List<Character> searchCharactersByName(String name) {
        return characterRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Buscar personajes por tripulación
    public List<Character> getCharactersByCrew(Integer crewId) {
        return characterRepository.findByCrewId(crewId);
    }
    
    // Buscar personajes por nombre de tripulación
    public List<Character> getCharactersByCrewName(String crewName) {
        return characterRepository.findByCrewName(crewName);
    }
    
    // Buscar personajes por rango de recompensa
    public List<Character> getCharactersByBountyRange(BigDecimal minBounty, BigDecimal maxBounty) {
        return characterRepository.findByBountyBetween(minBounty, maxBounty);
    }
    
    // Buscar personajes con recompensa mayor a un valor
    public List<Character> getCharactersWithBountyGreaterThan(BigDecimal bounty) {
        return characterRepository.findByBountyGreaterThan(bounty);
    }
    
    // Buscar personajes sin recompensa
    public List<Character> getCharactersWithoutBounty() {
        return characterRepository.findByBountyIsNull();
    }
    
    // Buscar personajes por fruta del diablo
    public List<Character> searchCharactersByDevilFruit(String devilFruitName) {
        return characterRepository.findByDevilFruitNameContaining(devilFruitName);
    }
    
    // Buscar personajes por tipo de fruta del diablo
    public List<Character> getCharactersByDevilFruitType(String typeName) {
        return characterRepository.findByDevilFruitType(typeName);
    }
    
    // Obtener personajes ordenados por recompensa
    public List<Character> getCharactersOrderedByBounty() {
        return characterRepository.findAllOrderByBountyDesc();
    }
    
    // Asignar tripulación a personaje
    public Character assignCrewToCharacter(Integer characterId, Integer crewId) {
        Optional<Character> characterOpt = characterRepository.findById(characterId);
        Optional<Crew> crewOpt = crewRepository.findById(crewId);
        
        if (characterOpt.isPresent() && crewOpt.isPresent()) {
            Character character = characterOpt.get();
            character.setCrew(crewOpt.get());
            return characterRepository.save(character);
        }
        return null;
    }
    
    // Asignar fruta del diablo a personaje
    public Character assignDevilFruitToCharacter(Integer characterId, Integer fruitId) {
        Optional<Character> characterOpt = characterRepository.findById(characterId);
        Optional<DevilFruit> fruitOpt = devilFruitRepository.findById(fruitId);
        
        if (characterOpt.isPresent() && fruitOpt.isPresent()) {
            Character character = characterOpt.get();
            DevilFruit fruit = fruitOpt.get();
            character.addDevilFruit(fruit);
            return characterRepository.save(character);
        }
        return null;
    }
    
    // Remover fruta del diablo de personaje
    public Character removeDevilFruitFromCharacter(Integer characterId, Integer fruitId) {
        Optional<Character> characterOpt = characterRepository.findById(characterId);
        Optional<DevilFruit> fruitOpt = devilFruitRepository.findById(fruitId);
        
        if (characterOpt.isPresent() && fruitOpt.isPresent()) {
            Character character = characterOpt.get();
            DevilFruit fruit = fruitOpt.get();
            character.removeDevilFruit(fruit);
            return characterRepository.save(character);
        }
        return null;
    }
}
