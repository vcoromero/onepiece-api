package com.onepiece.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "devil_fruits")
public class DevilFruit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Devil fruit name is mandatory")
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    @JsonBackReference
    private DevilFruitType type;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToMany(mappedBy = "devilFruits", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Character> characters = new HashSet<>();
    
    // Constructors
    public DevilFruit() {}
    
    public DevilFruit(String name, DevilFruitType type) {
        this.name = name;
        this.type = type;
    }
    
    public DevilFruit(String name, DevilFruitType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public DevilFruitType getType() {
        return type;
    }
    
    public void setType(DevilFruitType type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Set<Character> getCharacters() {
        return characters;
    }
    
    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }
    
    // Utility methods
    public void addCharacter(Character character) {
        this.characters.add(character);
        character.getDevilFruits().add(this);
    }
    
    public void removeCharacter(Character character) {
        this.characters.remove(character);
        character.getDevilFruits().remove(this);
    }
}

