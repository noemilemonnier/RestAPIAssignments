package com.AnimalShelter.Models;

public abstract class AnimalModel extends Model {
    private String birthdate;
    private String enter_date; // when the cat entered the shelter
    private String description; // What do we know about the cat

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEnter_date() {
        return enter_date;
    }

    public void setEnter_date(String enter_date) {
        this.enter_date = enter_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
