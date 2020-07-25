package com.AnimalShelter.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.AnimalShelter.Models.User;

import java.io.IOException;
import java.security.Key;
import java.util.List;

@Provider
//@RoleNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTSecurtiyFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
            try {
            	String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            	String path = requestContext.getUriInfo().getPath();
            	System.out.println("path: " + path);
            	System.out.println("Request: " + requestContext.getMethod());
                if(authorizationHeader != null || path.indexOf("user") == -1) {
                	
	                String token = authorizationHeader.substring("Bearer".length()).trim();
	                
	                
	                Key key = KeyGenerator.getInstance().getKey();
	                
	                Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	
	                User user = new User();
	                user.setName(claims.getBody().getSubject());
	                List<String> roles = (List<String>) claims.getBody().get("role"); 
	                System.out.println("current user roles: "+ roles);
	                user.setRole(roles);
	                   
	                // Create Security Context
	                AnimalShelterSecurityContext secContext = new AnimalShelterSecurityContext(user, requestContext.getSecurityContext().isSecure());
	                requestContext.setSecurityContext(secContext);
	                
                }
            } catch (Exception e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
    }

}
