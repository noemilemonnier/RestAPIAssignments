package com.AnimalShelter.Resources;

import com.AnimalShelter.Exceptions.DataNotFoundException;
import com.AnimalShelter.Models.Toy;
import com.AnimalShelter.Models.Animal;
import com.AnimalShelter.Models.Breed;
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

import java.util.ListIterator;

@Path("dogs/{dogId}/toys")
@RolesAllowed({"admin", "dog_manager", "dog_walker"})
@Singleton
public class DogToyResource extends Resource {

    public DogToyResource() {
        setService(Services.getInstance().getDogService());
    }

    /**
     * List all toys of certain dog
     *
     * @param
     * @return
     * @throws
     */
    @GET
    @RolesAllowed({"admin", "dog_manager", "dog_walker"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getToys(@PathParam("dogId") int dogId) {
        return Response.status(Response.Status.OK)
                .entity(getDog(dogId).getToys())
                .build();
    }
    /**
     * Adds a new toy from the given json format
     *
     * @param toy
     * @return
     * @throws
     */
    @POST
    @RolesAllowed({"admin", "dog_manager"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createToy(Toy toy, @PathParam("dogId") int dogId,@Context UriInfo uriInfo) {
    	toy.setAnimal_type(Animal.dog);
    	
    	getDog(dogId).getToys().add(toy);

    	toy.addLink("/shelter_api/dogs/"+ dogId + "/toys/" + toy.getId(), "self");
    	toy.addLink("/shelter_api/dogs/"+ dogId, "dog");
        
        String uri = uriInfo.getBaseUriBuilder()
                .path(Resource.class)
                .path(Long.toString(dogId))
                .build()
                .toString();
        
        return Response.status(Response.Status.CREATED)// 201
                .entity("A new Dog Toy has been created") // It doesnt actually save anything persistently/temporarily yet
                .header("Location", uri).build();
    }


    /** 
     * Get id of toy {toyId}
     *
     * @param toyId
     * @return
     * @throws
     * */
    @GET
    @RolesAllowed({"admin", "dog_manager", "dog_walker"})
    @Path("/{toyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getToy(@PathParam("dogId") int dogId, @PathParam("toyId") int toyId) {
        return Response.status(Response.Status.OK)
                .entity(getDog(dogId).fetchToyById(toyId))
                .build();
    }


    /**
     * Replace toy of id {toyId}
     *
     * @param toyId
     * @return
     * @throws
     */
    @PUT
    @RolesAllowed({"admin", "dog_manager"})
    @Path("/{toyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response replaceToy(@PathParam("dogId") int dogId, @PathParam("toyId") int toyId, Toy toy) {
        toy.addLink("/shelter_api/dogs/"+ dogId + "/toys/" + toy.getId(), "self");
        toy.addLink("/shelter_api/dogs/"+ dogId, "dog");

        for (ListIterator<Toy> iter = getDog(dogId).getToys().listIterator(); iter.hasNext(); ) {
            Toy a = iter.next();
            if (a.getId().equals(toyId)) {
                iter.set(toy);
                return Response.status(Response.Status.OK)
                        .entity(toy)
                        .build();
            }
        }
        throw new DataNotFoundException("Toy not found");
    }


    /**
     * Delete toy of id {toyId}
     *
     * @param toyId
     * @return
     * @throws
     */
    @DELETE
    @RolesAllowed({"admin", "dog_manager"})
    @Path("/{toyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteToy(@PathParam("dogId") int dogId, @PathParam("toyId") int toyId) {
        for (ListIterator<Toy> iter = getDog(dogId).getToys().listIterator(); iter.hasNext(); ) {
            Toy a = iter.next();
            if (a.getId().equals(toyId)) {
                iter.remove();
                return Response.status(Response.Status.OK)
                        .entity(a)
                        .build();
            }
        }
        throw new DataNotFoundException("Toy not found");
    }


    private Dog getDog(int dogId) {
        return (Dog) getService().getById(dogId);
    }

}
