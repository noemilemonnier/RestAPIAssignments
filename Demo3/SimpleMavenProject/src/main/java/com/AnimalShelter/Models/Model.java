package com.AnimalShelter.Models;

import com.AnimalShelter.hateoas.Link;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public abstract class Model {
    private Integer id;
    private String name;

    private List<Link> links = new ArrayList<>();
   

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

    public void addLink(String url, String rel){
        Link link = new Link();
        link.setLink(url);
        link.setRel(rel);
        links.add(link);
    }

    public List<Link> getLinks() {
        return links;
    }
}
