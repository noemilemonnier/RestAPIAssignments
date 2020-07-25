package com.AnimalShelter.Resources; 
 
import org.glassfish.jersey.server.ResourceConfig; 
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature; 
 
import com.AnimalShelter.Resources.Resource; 
 
public class AnimalShelterApplication extends ResourceConfig { 
    public AnimalShelterApplication() { 
    	packages("com.AnimalShelter"); 
        register(RolesAllowedDynamicFeature.class); 
    } 
} 