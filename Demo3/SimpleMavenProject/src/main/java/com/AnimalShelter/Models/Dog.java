package com.AnimalShelter.Models;

import com.AnimalShelter.Exceptions.DataNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dog extends AnimalModel {
	private Integer level_of_goodness; // according to we rate dogs
	private List<Breed> breeds;
	private List<Toy> toys;

	public Dog() {
		addLinks();
	}
	
	public Dog(int id, String name, String birthdate, String enter_date, String description, int level_of_goodness) {
		setId(id);
		setName(name);
		setBirthdate(birthdate);
		setEnter_date(enter_date);
		setDescription(description);
		this.level_of_goodness = level_of_goodness;
		this.breeds =  new ArrayList<>();
		this.toys = new ArrayList<>();
		addLinks();
		
	}

	private void addLinks() {
		addLink("/shelter_api/dogs/" + getId(), "self");
		addLink("/shelter_api/dogs/" + getId() + "/breeds", "breeds");
		addLink("/shelter_api/dogs/" + getId() + "/toys", "toys");
	}
	
/* Example content to create a dog
	{
		"id": 0,
		"name": "Ernie",
		"birthdate": "01-09-2017", 
		"enter_date": "30-05-2019",
		"description": "Ernie came to us with many more dogs on an official seizure, as his former owners have violated the Animal Protection Act. He is a very lively, friendly and human-oriented dog, very cuddly and always in a good mood. Luckily for him, he still lacks the right bipeds, who steer his energy into the right direction and support him for a lifetime.",
		"level_of_goodness": 12
	} 
*/
	// This method gives error

	  public Toy fetchToyById(int id) {

	        for(Toy t :toys) {
	            if(t.getId() == id) {
	                return t;
	            }
	        }
	        throw new DataNotFoundException("Toys not found");
	     }
	     

	    public List<Toy> getToys() {
	        return toys;
	    }

	    public void setToys(List<Toy> toys) {
	        this.toys = toys;
	    }


	public Integer getLevel_of_goodness() {
		return level_of_goodness;
	}

	public void setLevel_of_goodness(Integer level_of_goodness) {
		this.level_of_goodness = level_of_goodness;
	}
	
	@Override
    public String toString() {
       return "\nID: " + getId() + "\tName: "+ getName() +"\tBirthday: "+ getBirthdate() + "\tHere since: " + getEnter_date() + "\tLevel of Goodness: " + this.level_of_goodness+ "\tDescription: " + getDescription() + "\n";
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
