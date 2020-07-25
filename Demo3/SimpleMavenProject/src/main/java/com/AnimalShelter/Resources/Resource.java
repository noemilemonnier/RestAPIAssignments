package com.AnimalShelter.Resources;

import com.AnimalShelter.Models.Model;
import com.AnimalShelter.Services.Service;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("")
@Singleton
public abstract class Resource<T extends Model> {
    private Service<T> service = new Service<>();

    /**
     * List all
     *
     * @param
     * @return
     * @throws
     */
    public Response getAll() {
        return Response.status(Response.Status.OK)
                .entity(service.listAll())
                .build();
    }

    public Response getByName(String name) {
        return Response.status(Response.Status.OK)
                .entity(service.listByName(name))
                .build();
    }

    /**
     * Adds a new entry from the given json format
     *
     * @param object
     * @return
     * @throws
     */
    public Response create(T object, UriInfo uriInfo) {
        Integer id = service.create(object);
        String uri = uriInfo.getBaseUriBuilder()
                .path(Resource.class)
                .path(Long.toString(id))
                .build()
                .toString();
        return Response.status(Response.Status.CREATED)// 201
                .entity("A new object has been created") // It doesnt actually save anything persistently/temporarily yet
                .header("Location", uri).build();
    }

    /**
     * Get object of id {id}
     *
     * @param id
     * @return
     * @throws
     */
    public Response getById(int id) {
        T object = service.getById(id);
        return Response.status(Response.Status.OK)
                .entity(object)
                .build();
    }

    public Response replace(int id, T object) {
        T newObject = service.replace(id, object);
        return Response.status(Response.Status.OK)
                .entity(newObject)
                .build();
    }

    public Response delete(int id) {
        T object = service.delete(id);
        return Response.status(Response.Status.OK)
                .entity(object)
                .build();
    }


    public void setService(Service<T> s) {
        service = s;
    }

    public Service<T> getService() {
        return service;
    }
}
