package com.onepiece.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "crews")
public class Crew {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "El nombre de la tripulación es obligatorio")
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    
    @Column(length = 100)
    private String ship;
    
    @OneToMany(mappedBy = "crew", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Character> characters = new HashSet<>();
    
    // Constructores
    public Crew() {}
    
    public Crew(String name) {
        this.name = name;
    }
    
    public Crew(String name, String ship) {
        this.name = name;
        this.ship = ship;
    }
    
    // Getters y Setters
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
    
    public String getShip() {
        return ship;
    }
    
    public void setShip(String ship) {
        this.ship = ship;
    }
    
    public Set<Character> getCharacters() {
        return characters;
    }
    
    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }
    
    // Métodos de utilidad
    public void addCharacter(Character character) {
        this.characters.add(character);
        character.setCrew(this);
    }
    
    public void removeCharacter(Character character) {
        this.characters.remove(character);
        character.setCrew(null);
    }
}

