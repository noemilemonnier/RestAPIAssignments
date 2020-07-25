package com.AnimalShelter.Exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LevelOfGoodnessTooLowExceptionMapper implements ExceptionMapper<LevelOfGoodnessTooLowException> {

	@Override
	public Response toResponse(LevelOfGoodnessTooLowException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 403, "http://myDocs.org");
		return Response.status(Status.FORBIDDEN).entity(errorMessage).build();
	}

}
