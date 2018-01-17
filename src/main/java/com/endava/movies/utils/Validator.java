package com.endava.movies.utils;

import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;

public class Validator {
	public static void newFilmValidator(String title, int year, Boolean exists)
			throws InvalidException, AlreadyExisting {
		if (title == null)
			throw new InvalidException("Bad request body, film name missing");
		if (year == 0)
			throw new InvalidException("Bad request body, year missing!");
		validateYear(year);
		if (exists)
			throw new AlreadyExisting("Movie titled " + title + " already exists");
	}

	public static void newActorValidator(String name, int birth, Boolean exists)
			throws InvalidException, AlreadyExisting {
		if (name == null)
			throw new InvalidException("Bad request body, actor name missing");
		if (birth == 0)
			throw new InvalidException("Bad request body, birth data missing!");
		validateBirth(birth);
		if (exists)
			throw new AlreadyExisting("Actor named " + name + " already exists!");
	}

	public static void newDirectorValidator(String name, int birth, Boolean exists)
			throws InvalidException, AlreadyExisting {
		if (name == null)
			throw new InvalidException("Bad request body, director name missing");
		if (birth == 0)
			throw new InvalidException("Bad request body, birth data missing!");
		validateBirth(birth);
		if (exists)
			throw new AlreadyExisting("Director named " + name + " already exists!");
	}

	public static void validateRate(int rate) throws InvalidException {
		if (rate <= 0 || rate > 10)
			throw new InvalidException("Invalid rate! Rate must be between 1 and 10");
	}

	public static void validateYear(int year) throws InvalidException {
		if (year != 0)
			if (year <= 1900 || year >= 2020)
				throw new InvalidException("Year " + year + " is unlikely to be the release year of a film");
	}

	public static void validateBirth(int year) throws InvalidException {
		if (year != 0)
			if (year <= 1880 || year >= 2017)
				throw new InvalidException("Year " + year + " is unlikely to be the year of birth!!");
	}

}
