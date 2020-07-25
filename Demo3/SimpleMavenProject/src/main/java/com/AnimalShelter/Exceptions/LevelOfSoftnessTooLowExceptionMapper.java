package com.AnimalShelter.Exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider

public class LevelOfSoftnessTooLowExceptionMapper implements ExceptionMapper<LevelOfGoodnessTooLowException> {

	@Override
	public Response toResponse(LevelOfGoodnessTooLowException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 402, "http://myDocs.org");
		return Response.status(Status.FORBIDDEN).entity(errorMessage).build();
	}

}