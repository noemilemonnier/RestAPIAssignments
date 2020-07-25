package com.AnimalShelter.Services;

import com.AnimalShelter.Exceptions.DataNotFoundException;
import com.AnimalShelter.Models.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Service<T extends Model> {
    protected List<T> list = new ArrayList<>();

    public List<T> listAll() {
        return list;
    }

    public List<T> listByName(String name) {
        List<T> result = new ArrayList<>();
        for (T object : list) {
            if (object.getName().equals(name)) {
                result.add(object);
            }
        }
        return result;
    }

    public Integer create(T object) {
        list.add(object);
        return object.getId();
    }

    public T getById(int id) {
        for (T object : list) {
            if (object.getId().equals(id)) {
                return object;
            }
        }
        throw new DataNotFoundException(String.format("Object with the id %d not found.", id));
    }

    public T getByName(String name) {
        for (T object : list) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        throw new DataNotFoundException(String.format("Object with the name %s not found.", name));
    }

    public T replace(int id, T object) {
        for (ListIterator<T> iter = list.listIterator(); iter.hasNext(); ) {
            T a = iter.next();
            if (a.getId().equals(id)) {
                iter.set(object);
                return object;
            }
        }
        throw new DataNotFoundException(String.format("Object with the id %d not found.", id));
    }

    public T delete(int id) {
        for (Iterator<T> iter = list.listIterator(); iter.hasNext(); ) {
            T a = iter.next();
            if (a.getId().equals(id)) {
                iter.remove();
                return a;
            }
        }
        throw new DataNotFoundException(String.format("Object with the id %d not found.", id));
    }
}
