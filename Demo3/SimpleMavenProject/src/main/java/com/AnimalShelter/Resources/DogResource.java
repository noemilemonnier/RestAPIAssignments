package com.AnimalShelter.Resources;

import com.AnimalShelter.Models.Cat;
import com.AnimalShelter.Models.Dog;
import com.AnimalShelter.Services.Services;
import com.AnimalShelter.auth.RoleNeeded;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("dogs")
@RolesAllowed({"admin", "vet", "dog_manager", "dog_walker"})
@Singleton
public class DogResource extends Resource {
	public DogResource() {
		setService(Services.getInstance().getDogService());
	}

	/**
     * List all dogs
     *
     * @param 
     * @return
     * @throws 
     */
    @GET
    @RolesAllowed({"admin", "vet", "dog_manager", "dog_walker"})
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getDogs(@QueryParam("name") String name) {
        if(name == null) {
            return getAll();
        }
    	return getByName(name);
    }
	
	/**
     * Adds a new dog from the given json format
     *
     * @param dog
     * @return
     * @throws 
     */
    @POST
    @RolesAllowed({"admin", "vet", "dog_manager"})
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createDog(Dog dog, @Context UriInfo uriInfo) {
    	Dog newDog = new Dog(dog.getId(), dog.getName(), dog.getBirthdate(), dog.getEnter_date(), dog.getDescription(), dog.getLevel_of_goodness());
    	return create(newDog, uriInfo);
    }
    
    /**
     * Get dog of id {dogId}
     *
     * @param dogId
     * @return
     * @throws 
     */
    @GET
    @RolesAllowed({"admin", "vet", "dog_manager", "dog_walker"})
    @Path("/{dogId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getDog(@PathParam("dogId") int dogId) {
    	return getById(dogId);
    }

    /**
     * Replace dog of id {dogId}
     *
     * @param dogId
     * @return
     * @throws
     */
    @PUT
    @RolesAllowed({"admin", "vet", "dog_manager"})
    @Path("/{dogId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response replaceDog(@PathParam("dogId") int dogId, Dog dog) {
        return replace(dogId, dog);
    }

    /**
     * Delete dog of id {dogId}
     *
     * @param dogId
     * @return
     * @throws
     */
    @DELETE
    @RolesAllowed({"admin", "vet", "dog_manager"})
    @Path("/{dogId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteDog(@PathParam("dogId") int dogId) {
        return delete(dogId);
    }

}
