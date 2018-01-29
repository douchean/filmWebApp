package com.endava.movies.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.endava.movies.annotations.Year;

public class YearValidator implements ConstraintValidator<Year, Integer> {

	@Override
	public void initialize(Year constraintAnnotation) {

	}

	@Override
	public boolean isValid(Integer year, ConstraintValidatorContext context) {
		if (year == 0)
			return true;
		if (year <= 1880 || year >= 2017)
			return false;

		return true;
	}

}
