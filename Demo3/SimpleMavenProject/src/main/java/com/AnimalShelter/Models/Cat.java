package com.AnimalShelter.Models;

import com.AnimalShelter.Exceptions.DataNotFoundException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Cat extends AnimalModel {
	private Integer level_of_softness; // according to we rate cats
	private List<Breed> breeds;
	private List<Toy> toys;
			
	public Cat() {
		addLinks();
    }

	public Cat(int id, String name, String birthdate, String enter_date, String description, int level_of_softness) {
		setId(id);
		setName(name);
		setBirthdate(birthdate);
		setEnter_date(enter_date);
		setDescription(description);
		this.level_of_softness = level_of_softness;
		this.breeds =  new ArrayList<>();
		this.toys = new ArrayList<>();
        addLinks();

	}

	private void addLinks() {
		addLink("/shelter_api/cats/" + getId(), "self");
		addLink("/shelter_api/cats/" + getId() + "/toys", "toys");
	}

	/* Example content to create a cat
	{
		"id": 0,
		"name": "Ernie",
		"birthdate": "01-09-2017", 
		"enter_date": "30-05-2019",
		"description": "Ernie came to us with many more dogs on an official seizure, as his former owners have violated the Animal Protection Act. He is a very lively, friendly and human-oriented dog, very cuddly and always in a good mood. Luckily for him, he still lacks the right bipeds, who steer his energy into the right direction and support him for a lifetime.",
		"level_of_softness": 12
	} 
	 */

	public Integer getlevel_of_softness() {
		return level_of_softness;
	}

	public void setlevel_of_softness(Integer level_of_softness) {
		this.level_of_softness = level_of_softness;
	}

	@Override
	public String toString() {
		return "\nID: " + getId() + "\tName: "+ getName() +"\tBirthday: "+ getBirthdate() + "\tHere since: " + getEnter_date() + "\tLevel of Goodness: " + this.level_of_softness+ "\tDescription: " + getDescription() + "\n";
	}

	public Toy fetchToyById(int id) {
		for (Toy t :toys) {
			if(t.getId() == id) {
				return t;
			}
		}
		throw new DataNotFoundException("Breed not found");
	}

	public List<Toy> getToys() {
		return toys;
	}

    public void setToys(List<Toy> toys) {
        this.toys = toys;
    }
    public Breed fetchBreedById(int id) {
        for (Breed b :breeds) {
            if(b.getId() == id) {
                return b;
            }
        }
        throw new DataNotFoundException("Breed not found");
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }
}

