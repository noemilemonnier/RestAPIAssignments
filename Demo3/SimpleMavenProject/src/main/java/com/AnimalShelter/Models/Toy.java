package com.AnimalShelter.Models;

public class Toy extends Model {
    private Animal animal_type;

    public Toy() {
    }

    public Toy(Integer id, String name, Animal animal_type) {
        setId(id);
        setName(name);
        this.animal_type = animal_type;
        
    }

    

    public Animal getAnimal_type() {
        return animal_type;
    }

    public void setAnimal_type(Animal animal_type) {
        this.animal_type = animal_type;
    }
}
