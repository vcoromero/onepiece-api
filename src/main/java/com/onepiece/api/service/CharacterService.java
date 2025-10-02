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
    
    // Get all characters
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }
    
    // Get character by ID
    public Optional<Character> getCharacterById(Integer id) {
        return characterRepository.findById(id);
    }
    
    // Create new character
    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }
    
    // Update character
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
    
    // Delete character
    public boolean deleteCharacter(Integer id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent()) {
            characterRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Search characters by name
    public List<Character> searchCharactersByName(String name) {
        return characterRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Search characters by crew
    public List<Character> getCharactersByCrew(Integer crewId) {
        return characterRepository.findByCrewId(crewId);
    }
    
    // Search characters by crew name
    public List<Character> getCharactersByCrewName(String crewName) {
        return characterRepository.findByCrewName(crewName);
    }
    
    // Search characters by bounty range
    public List<Character> getCharactersByBountyRange(BigDecimal minBounty, BigDecimal maxBounty) {
        return characterRepository.findByBountyBetween(minBounty, maxBounty);
    }
    
    // Search characters with bounty greater than a value
    public List<Character> getCharactersWithBountyGreaterThan(BigDecimal bounty) {
        return characterRepository.findByBountyGreaterThan(bounty);
    }
    
    // Search characters without bounty
    public List<Character> getCharactersWithoutBounty() {
        return characterRepository.findByBountyIsNull();
    }
    
    // Search characters by devil fruit
    public List<Character> searchCharactersByDevilFruit(String devilFruitName) {
        return characterRepository.findByDevilFruitNameContaining(devilFruitName);
    }
    
    // Search characters by devil fruit type
    public List<Character> getCharactersByDevilFruitType(String typeName) {
        return characterRepository.findByDevilFruitType(typeName);
    }
    
    // Get characters ordered by bounty
    public List<Character> getCharactersOrderedByBounty() {
        return characterRepository.findAllOrderByBountyDesc();
    }
    
    // Assign crew to character
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
    
    // Assign devil fruit to character
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
    
    // Remove devil fruit from character
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
