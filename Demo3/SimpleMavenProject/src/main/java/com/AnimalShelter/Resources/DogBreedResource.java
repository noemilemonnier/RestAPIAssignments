package com.AnimalShelter.Resources;

import com.AnimalShelter.Exceptions.DataNotFoundException;
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

@Path("dogs/{dogId}/breeds")
@RolesAllowed({"admin", "dog_manager", "vet"})
@Singleton
public class DogBreedResource extends Resource {
    public DogBreedResource() {
        setService(Services.getInstance().getDogService());
    }

    /**
     * List all breeds
     *
     * @param
     * @return
     * @throws
     */
    @GET
    @RolesAllowed({"admin", "vet", "dog_manager", "dog_walker"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBreeds(@PathParam("dogId") int dogId) {
        return Response.status(Response.Status.OK)
                .entity(getDog(dogId).getBreeds())
                .build();
    }

    /**
     * Adds a new breed from the given json format
     *
     * @param breed
     * @return
     * @throws
     */
    @POST
    @RolesAllowed({"admin", "vet"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createBreed(Breed breed, @PathParam("dogId") int dogId, @Context UriInfo uriInfo) {
    	breed.setAnimal_type(Animal.dog);
        getDog(dogId).getBreeds().add(breed);

        breed.addLink("/shelter_api/dogs/"+ dogId + "/breeds/" + breed.getId(), "self");
        breed.addLink("/shelter_api/dogs/"+ dogId, "dog");

        String uri = uriInfo.getBaseUriBuilder()
                .path(Resource.class)
                .path(Long.toString(dogId))
                .build()
                .toString();

        return Response.status(Response.Status.CREATED)// 201
                .entity("A new Dog Breed has been created") // It doesnt actually save anything persistently/temporarily yet
                .header("Location", uri).build();
    }

    /**
     * Get breed of id {breedId}
     *
     * @param breedId
     * @return
     * @throws
     */
    @GET
    @RolesAllowed({"admin", "vet", "dog_manager", "dog_walker"})
    @Path("/{breedId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBreed(@PathParam("dogId") int dogId, @PathParam("breedId") int breedId) {
        return Response.status(Response.Status.OK)
                .entity(getDog(dogId).fetchBreedById(breedId))
                .build();
    }

    /**
     * Replace breed of id {breedId}
     *
     * @param breedId
     * @return
     * @throws
     */
    @PUT
    @RolesAllowed({"admin", "vet", "dog_manager"})
    @Path("/{breedId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response replaceBreed(@PathParam("dogId") int dogId, @PathParam("breedId") int breedId, Breed breed) {
        breed.addLink("/shelter_api/dogs/"+ dogId + "/breeds/" + breed.getId(), "self");
        breed.addLink("/shelter_api/dogs/"+ dogId, "dog");
        for (ListIterator<Breed> iter = getDog(dogId).getBreeds().listIterator(); iter.hasNext(); ) {
            Breed a = iter.next();
            if (a.getId().equals(breedId)) {
                iter.set(breed);
                return Response.status(Response.Status.OK)
                        .entity(breed)
                        .build();
            }
        }
        throw new DataNotFoundException("Breed not found");
    }

    /**
     * Delete breed of id {breedId}
     *
     * @param breedId
     * @return
     * @throws
     */
    @DELETE
    @RolesAllowed({"admin", "vet", "dog_manager"})
    @Path("/{breedId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteBreed(@PathParam("dogId") int dogId, @PathParam("breedId") int breedId) {
        for (ListIterator<Breed> iter = getDog(dogId).getBreeds().listIterator(); iter.hasNext(); ) {
            Breed a = iter.next();
            if (a.getId().equals(breedId)) {
                iter.remove();
                return Response.status(Response.Status.OK)
                        .entity(a)
                        .build();
            }
        }
        throw new DataNotFoundException("Breed not found");
    }

    private Dog getDog(int dogId) {
        return (Dog) getService().getById(dogId);
    }

}
