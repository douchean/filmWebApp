package com.endava.movies.utils;

import com.endava.movies.exceptions.InvalidException;

public class Validator {
	public static void validateRate(int rate) throws InvalidException {
		if (rate <= 0 || rate > 10)
			throw new InvalidException("Invalid rate! Rate must be between 1 and 10");
	}

}
