package com.AnimalShelter.Models;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class User extends Model implements Principal {
    private String email, password;
    private List<String> role;

    public User() {

    }

    public User(int id, String name, String email, String password) {
        setId(id);
        setName(name);
        this.email = email;
        this.password = password;
        this.role = new ArrayList<String>();
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }
}
