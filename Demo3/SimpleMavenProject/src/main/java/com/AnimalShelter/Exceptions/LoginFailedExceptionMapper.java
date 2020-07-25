package com.AnimalShelter.Exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LoginFailedExceptionMapper implements ExceptionMapper<LoginFailedException> {
    @Override
    public Response toResponse(LoginFailedException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 401, "http://myDocs.org");
        return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
    }
}
