package com.AnimalShelter.Resources;

import com.AnimalShelter.Exceptions.DataNotFoundException;
import com.AnimalShelter.Models.Animal;
import com.AnimalShelter.Models.Breed;
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
import java.util.ListIterator;

@Path("cats/{catId}/breeds")
@RolesAllowed({"admin", "vet", "cat_manager", "cat_walker"})
@Singleton
public class CatBreedResource extends Resource {
    public CatBreedResource() {
        setService(Services.getInstance().getCatService());
    }

    /**
     * List all breeds
     *
     * @param
     * @return
     * @throws
     */
    @GET
    @RolesAllowed({"admin", "vet", "cat_manager", "cat_walker"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBreeds(@PathParam("catId") int catId) {
        return Response.status(Response.Status.OK)
                .entity(getCat(catId).getBreeds())
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
    public Response createBreed(Breed breed, @PathParam("catId") int catId, @Context UriInfo uriInfo) {
    	breed.setAnimal_type(Animal.cat);
    	getCat(catId).getBreeds().add(breed);

        breed.addLink("/shelter_api/cats/"+ catId + "/breeds/" + breed.getId(), "self");
        breed.addLink("/shelter_api/cats/"+ catId, "cat");

        String uri = uriInfo.getBaseUriBuilder()
                .path(Resource.class)
                .path(Long.toString(catId))
                .build()
                .toString();

        return Response.status(Response.Status.CREATED)// 201
                .entity("A new Cat Breed has been created") // It doesnt actually save anything persistently/temporarily yet
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
    @RolesAllowed({"admin", "vet", "cat_manager", "cat_walker"})
    @Path("/{breedId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBreed(@PathParam("catId") int catId, @PathParam("breedId") int breedId) {
        return Response.status(Response.Status.OK)
                .entity(getCat(catId).fetchBreedById(breedId))
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
    @RolesAllowed({"admin", "vet", "cat_manager"})
    @Path("/{breedId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response replaceBreed(@PathParam("catId") int catId, @PathParam("breedId") int breedId, Breed breed) {
        breed.addLink("/shelter_api/cats/"+ catId + "/breeds/" + breed.getId(), "self");
        breed.addLink("/shelter_api/cats/"+ catId, "cat");
        for (ListIterator<Breed> iter = getCat(catId).getBreeds().listIterator(); iter.hasNext(); ) {
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
    @RolesAllowed({"admin", "vet", "cat_manager"})
    @Path("/{breedId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteBreed(@PathParam("catId") int catId, @PathParam("breedId") int breedId) {
        for (ListIterator<Breed> iter = getCat(catId).getBreeds().listIterator(); iter.hasNext(); ) {
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

    private Cat getCat(int catId) {
        return (Cat) getService().getById(catId);
    }

}
