package com.AnimalShelter.Resources;

import com.AnimalShelter.Models.Cat;
import com.AnimalShelter.Services.CatService;
import com.AnimalShelter.Services.Services;
import com.AnimalShelter.auth.RoleNeeded;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("cats")
@Singleton
@RolesAllowed({"admin", "vet", "cat_manager", "cat_walker"})
public class CatResource extends Resource {
	public CatResource() {
		setService(Services.getInstance().getCatService());
	}

	/**
     * List all cats
     *
     * @param 
     * @return
     * @throws 
     */
    @GET
    @RolesAllowed({"admin", "vet", "cat_manager", "cat_walker"})
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCats(@QueryParam("name") String name) {
        if(name == null) {
            return getAll();
        }
    	return getByName(name);
    }
	
	/**
     * Adds a new cat from the given json format
     *
     * @param cat
     * @return
     * @throws 
     */
    @POST
    @RolesAllowed({"admin", "vet", "cat_manager"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    @RoleNeeded(role = "user")
    public Response createCat(Cat cat, @Context UriInfo uriInfo) {
    	return create(cat, uriInfo);
    }
    
    /**
     * Get cat of id {catId}
     *
     * @param catId
     * @return
     * @throws 
     */
    @GET
    @RolesAllowed({"admin", "vet", "cat_manager", "cat_walker"})
    @Path("/{catId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCat(@PathParam("catId") int catId) {
    	return getById(catId);
    }

    /**
     * Replace cat of id {catId}
     *
     * @param catId
     * @return
     * @throws
     */
    @PUT
    @RolesAllowed({"admin", "vet", "cat_manager"})
    @Path("/{catId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response replaceCat(@PathParam("catId") int catId, Cat cat) {
        return replace(catId, cat);
    }

    /**
     * Delete cat of id {catId}
     *
     * @param catId
     * @return
     * @throws
     */
    @DELETE
    @RolesAllowed({"admin", "vet", "cat_manager"})
    @Path("/{catId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteCat(@PathParam("catId") int catId) {
        return delete(catId);
    }

}
