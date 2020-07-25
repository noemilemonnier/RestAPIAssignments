package com.AnimalShelter.Exceptions;

public class DataNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2359946892971655513L;
	public DataNotFoundException(String message) {
		super(message);
	}

}
