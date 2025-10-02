package com.onepiece.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "characters")
public class Character {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false, length = 100)
    private String name;
    
    @DecimalMin(value = "0.0", message = "Bounty must be positive")
    @Column(precision = 15, scale = 2)
    private BigDecimal bounty;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id")
    @JsonBackReference
    private Crew crew;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "character_fruit",
        joinColumns = @JoinColumn(name = "character_id"),
        inverseJoinColumns = @JoinColumn(name = "fruit_id")
    )
    @JsonIgnore
    private Set<DevilFruit> devilFruits = new HashSet<>();
    
    // Constructors
    public Character() {}
    
    public Character(String name, BigDecimal bounty) {
        this.name = name;
        this.bounty = bounty;
    }
    
    public Character(String name, BigDecimal bounty, Crew crew) {
        this.name = name;
        this.bounty = bounty;
        this.crew = crew;
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
    
    public BigDecimal getBounty() {
        return bounty;
    }
    
    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }
    
    public Crew getCrew() {
        return crew;
    }
    
    public void setCrew(Crew crew) {
        this.crew = crew;
    }
    
    public Set<DevilFruit> getDevilFruits() {
        return devilFruits;
    }
    
    public void setDevilFruits(Set<DevilFruit> devilFruits) {
        this.devilFruits = devilFruits;
    }
    
    // Utility methods
    public void addDevilFruit(DevilFruit devilFruit) {
        this.devilFruits.add(devilFruit);
        devilFruit.getCharacters().add(this);
    }
    
    public void removeDevilFruit(DevilFruit devilFruit) {
        this.devilFruits.remove(devilFruit);
        devilFruit.getCharacters().remove(this);
    }
}
