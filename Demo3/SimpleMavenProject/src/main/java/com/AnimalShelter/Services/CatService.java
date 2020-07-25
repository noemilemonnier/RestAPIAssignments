package com.AnimalShelter.Services;

import com.AnimalShelter.Exceptions.DataNotFoundException;
import com.AnimalShelter.Exceptions.LevelOfSoftnessTooLowException;
import com.AnimalShelter.Models.Animal;
import com.AnimalShelter.Models.Breed;
import com.AnimalShelter.Models.Cat;
import com.AnimalShelter.Models.Toy;

import java.util.ArrayList;
import java.util.List;

public class CatService extends Service<Cat> {

    public CatService() {
        Cat cat1 = new Cat(10, "Luna", "12.4.2016", "10.2.2019", "soft persian Luna", 12);
        Cat cat2 = new Cat(11, "Fancy", "17.2.2017", "18.11.2018", "angry face Fancy", 13);
        list.add(cat1);
        list.add(cat2);

        List<Toy> toys1 = new ArrayList<>();
        Toy toy1 = new Toy(1, "Ball", Animal.cat);
        toy1.addLink("/shelter_api/cats/10/toys/1", "self");
        toy1.addLink("/shelter_api/cats/10", "cat");
        toys1.add(toy1);
        cat1.setToys(toys1);

        List<Toy> toys2 = new ArrayList<>();
        Toy toy2 = new Toy(2, "Mouse", Animal.cat);
        toy2.addLink("/shelter_api/cats/11/toys/2", "self");
        toy2.addLink("/shelter_api/cats/11", "cat");
        toys2.add(toy2);
        Toy toy3 = new Toy(3, "Laserpointer", Animal.cat);
        toy3.addLink("/shelter_api/cats/11/toys/3", "self");
        toy3.addLink("/shelter_api/cats/11", "cat");
        toys2.add(toy3);
        cat2.setToys(toys2);
        
        List<Breed> breeds1 = new ArrayList<>();
        Breed breed1 = new Breed(15, "Normal cat", Animal.cat, "");
        breed1.addLink("/shelter_api/cats/0/breeds/15", "self");
        breed1.addLink("/shelter_api/cats/0", "cat");
        breeds1.add(breed1);
        
        List<Breed> breeds2 = new ArrayList<>();
        Breed breed2 = new Breed(16, "Grumpy cat", Animal.cat, "");
        breed2.addLink("/shelter_api/cats/0/breeds/16", "self");
        breed2.addLink("/shelter_api/cats/0", "cat");
        breeds2.add(breed2);
        
        cat1.setBreeds(breeds1);
        cat2.setBreeds(breeds2);
        
        
    }

    @Override
    public Integer create(Cat cat) {
        if (cat.getlevel_of_softness() < 10) {
            throw new LevelOfSoftnessTooLowException("No cat's level of softness can be under 10.");
        }
        return super.create(cat);
    }

    public String toString() {
        String results = "+";
        for (int i = 0; i < this.list.size(); i++) {
            results += " " + this.list.get(i);
        }
        return results;
    }

    @Override
    public Cat getById(int id) {
        try {
            Cat cat = super.getById(id);
            return cat;
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(String.format("Cat with the id %d not found.", id));
        }
    }

}
