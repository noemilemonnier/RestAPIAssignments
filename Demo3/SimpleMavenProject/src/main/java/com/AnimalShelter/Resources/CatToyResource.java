package com.AnimalShelter.Resources;

import com.AnimalShelter.Exceptions.DataNotFoundException;
import com.AnimalShelter.Models.Toy;
import com.AnimalShelter.Models.Animal;
import com.AnimalShelter.Models.Cat;
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

@Path("cats/{catId}/toys")
@Singleton
@RolesAllowed({"admin", "cat_manager", "cat_walker"})
public class CatToyResource extends Resource {
    public CatToyResource() {
        setService(Services.getInstance().getCatService());
    }

    /**
     * List all toys
     *
     * @param
     * @return
     * @throws
     */
    @GET
    @RolesAllowed({"admin", "cat_manager", "cat_walker"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getToys(@PathParam("catId") int catId) {
        return Response.status(Response.Status.OK)
                .entity(getCat(catId).getToys())
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
    @RolesAllowed({"admin", "cat_manager"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createToy(Toy toy, @PathParam("catId") int catId, @Context UriInfo uriInfo) {
    	toy.setAnimal_type(Animal.cat);	
        getCat(catId).getToys().add(toy);
        
        toy.addLink("/shelter_api/cats/"+ catId + "/toys/" + toy.getId(), "self");
        toy.addLink("/shelter_api/cats/"+ catId, "cat");

        String uri = uriInfo.getBaseUriBuilder()
                .path(Resource.class)
                .path(Long.toString(catId))
                .build()
                .toString();

        return Response.status(Response.Status.CREATED)// 201
                .entity("A new Cat Toy has been created") // It doesnt actually save anything persistently/temporarily yet
                .header("Location", uri).build();
    }

    /**
     * Get toy of id {toyId}
     *
     * @param toyId
     * @return
     * @throws
     */
    @GET
    @RolesAllowed({"admin", "cat_manager", "cat_walker"})
    @Path("/{toyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getToy(@PathParam("catId") int catId, @PathParam("toyId") int toyId) {
        return Response.status(Response.Status.OK)
                .entity(getCat(catId).fetchToyById(toyId))
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
    @RolesAllowed({"admin", "cat_manager"})
    @Path("/{toyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response replaceToy(@PathParam("catId") int catId, @PathParam("toyId") int toyId, Toy toy) {
        toy.addLink("/shelter_api/cats/"+ catId + "/toys/" + toy.getId(), "self");
        toy.addLink("/shelter_api/cats/"+ catId, "dog");

        for (ListIterator<Toy> iter = getCat(catId).getToys().listIterator(); iter.hasNext(); ) {
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
    @RolesAllowed({"admin", "cat_manager"})
    @Path("/{toyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteToy(@PathParam("catId") int catId, @PathParam("toyId") int toyId) {
        for (ListIterator<Toy> iter = getCat(catId).getToys().listIterator(); iter.hasNext(); ) {
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

    private Cat getCat(int catId) {
        return (Cat) getService().getById(catId);
    }

}
