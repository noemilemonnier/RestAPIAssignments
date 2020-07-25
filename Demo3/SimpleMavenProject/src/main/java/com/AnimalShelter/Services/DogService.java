package com.AnimalShelter.Services;

import com.AnimalShelter.Exceptions.DataNotFoundException;
import com.AnimalShelter.Exceptions.LevelOfGoodnessTooLowException;
import com.AnimalShelter.Models.Animal;
import com.AnimalShelter.Models.Breed;
import com.AnimalShelter.Models.Dog;
import com.AnimalShelter.Models.Toy;

import java.util.ArrayList;
import java.util.List;

public class DogService extends Service<Dog> {

    public DogService() {
        Dog dog1 = new Dog(0, "Tommy", "12.7.2016", "10.8.2019", "good doggy tommy", 12);
        Dog dog2 = new Dog(1, "Ernie", "17.2.2017", "18.10.2018", "good doggy ernie", 13);
        list.add(dog1);
        list.add(dog2);

        List<Breed> breeds1 = new ArrayList<>();
        Breed breed1 = new Breed(1, "Labrador", Animal.dog, "");
        breed1.addLink("/shelter_api/dogs/0/breeds/1", "self");
        breed1.addLink("/shelter_api/dogs/0", "dog");
        breeds1.add(breed1);
        Breed breed2 = new Breed(2, "Retriever", Animal.dog, "");
        breed2.addLink("/shelter_api/dogs/0/breeds/2", "self");
        breed2.addLink("/shelter_api/dogs/0", "dog");
        breeds1.add(breed2);
        dog1.setBreeds(breeds1);

        List<Breed> breeds2 = new ArrayList<>();
        Breed breed3 = new Breed(3, "Husky", Animal.dog, "");
        breed3.addLink("/shelter_api/dogs/1/breeds/3", "self");
        breed3.addLink("/shelter_api/dogs/1", "dog");
        breeds2.add(breed3);
        dog2.setBreeds(breeds2);

        List<Toy> toys = new ArrayList<>();
        Toy toy1 = new Toy(1, "Stick for the Doggo", Animal.dog);
        toy1.addLink("/shelter_api/dogs/0/toys/1", "self");
        toy1.addLink("/shelter_api/dogs/0", "dog");
        toys.add(toy1);
        dog1.setToys(toys);

        List<Toy> toys1 = new ArrayList<>();
        Toy toy2 = new Toy(2, "Potate", Animal.dog);
        toy2.addLink("/shelter_api/dogs/1/toys/2", "self");
        toy2.addLink("/shelter_api/dogs/1", "dog");
        toys1.add(toy2);
        dog2.setToys(toys1);
    }

    @Override
    public Integer create(Dog dog) {
        if (dog.getLevel_of_goodness() < 10) {
            throw new LevelOfGoodnessTooLowException("No dog's level of goodness can be under 10.");
        }
        return super.create(dog);
    }

    public String toString() {
        String results = "+";
        for (int i = 0; i < this.list.size(); i++) {
            results += " " + this.list.get(i);
        }
        return results;
    }

    @Override
    public Dog getById(int id) {
        try {
            Dog dog = super.getById(id);
            return dog;
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(String.format("Dog with the id %d not found.", id));
        }
    }

}
