package com.AnimalShelter.Resources;

import com.AnimalShelter.Exceptions.LoginFailedException;
import com.AnimalShelter.Models.User;
import com.AnimalShelter.Services.Services;
import com.AnimalShelter.auth.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("users")
@Singleton
public class UserResource extends Resource {
	public UserResource() {
		setService(Services.getInstance().getUserService());
	}

	@RolesAllowed("admin")
	@GET
	public Response getUsers() {
		return getAll();
	}

	@RolesAllowed("admin")
	@GET
	@Path("/{userId}")
	public Response getUser(@PathParam("userId") int userId) {
		return getById(userId);
	}

	/**
	 * Adds a new user from the given json format
	 *
	 * @param user
	 * @return
	 * @throws
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.TEXT_PLAIN})
	public Response createUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email, @FormParam("ID") int id, @FormParam("roles") String roles, @Context UriInfo uriInfo) {

		User newUser = new User(id, username, email, password); //create the user
		//split the role string into list
		String[] items = roles.split(",");
		for (int i=0; i< items.length; i++) {
			newUser.getRole().add(items[i]); //stores the role entered
		}
	
		return create(newUser, uriInfo);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.TEXT_PLAIN})
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password, @Context UriInfo uriInfo) {
		try {
			User u = (User) getService().getByName(username);

			if(!u.checkPassword(password)) {
				throw new LoginFailedException();
			}

			String token = issueToken(u, uriInfo);

			return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private String issueToken(User user, UriInfo uriInfo) {
		Key key = KeyGenerator.getInstance().getKey();

		String jwtToken = Jwts.builder()
				.setSubject(user.getName())
				.setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date())
				.claim("role", user.getRole())
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
		return jwtToken;
	}
}
