package com.AnimalShelter.Exceptions;

public class LevelOfGoodnessTooLowException extends RuntimeException {
	private static final long serialVersionUID = 4296686263281965673L;

	public LevelOfGoodnessTooLowException(String message) {
		super(message);
	}
}
