package com.onepiece.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "devil_fruit_types")
public class DevilFruitType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "El nombre del tipo es obligatorio")
    @Column(nullable = false, length = 50, unique = true)
    private String name;
    
    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<DevilFruit> devilFruits = new HashSet<>();
    
    // Constructores
    public DevilFruitType() {}
    
    public DevilFruitType(String name) {
        this.name = name;
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
    
    public Set<DevilFruit> getDevilFruits() {
        return devilFruits;
    }
    
    public void setDevilFruits(Set<DevilFruit> devilFruits) {
        this.devilFruits = devilFruits;
    }
    
    // Métodos de utilidad
    public void addDevilFruit(DevilFruit devilFruit) {
        this.devilFruits.add(devilFruit);
        devilFruit.setType(this);
    }
    
    public void removeDevilFruit(DevilFruit devilFruit) {
        this.devilFruits.remove(devilFruit);
        devilFruit.setType(null);
    }
}

