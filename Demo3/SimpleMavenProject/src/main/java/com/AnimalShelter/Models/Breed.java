package com.AnimalShelter.Models;

public class Breed extends Model {
	private Animal animal_type;
	private String family;

	public Breed () {}

	public Breed(Integer id, String name, Animal animal_type, String family) {
		setId(id);
		setName(name);
		this.animal_type = animal_type;
		this.family = family;
	}

	public void addLinks() {

	}

	public Animal getAnimal_type() {
		return animal_type;
	}

	public void setAnimal_type(Animal animal_type) {
		this.animal_type = animal_type;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}
}
