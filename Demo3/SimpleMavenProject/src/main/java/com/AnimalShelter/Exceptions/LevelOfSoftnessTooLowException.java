package com.AnimalShelter.Exceptions;

public class LevelOfSoftnessTooLowException extends RuntimeException {
	private static final long serialVersionUID = 4123456789098765434L;

	public LevelOfSoftnessTooLowException(String message) {
		super(message);
	}
}
