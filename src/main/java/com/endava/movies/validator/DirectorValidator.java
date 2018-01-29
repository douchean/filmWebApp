package com.endava.movies.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.endava.movies.annotations.UniqueDirector;
import com.endava.movies.data.repository.DirectorRepository;

public class DirectorValidator implements ConstraintValidator<UniqueDirector, String> {
	@Autowired
	private DirectorRepository directorRepository;

	@Override
	public void initialize(UniqueDirector constraintAnnotation) {
		org.springframework.web.context.support.SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

	}

	@Override
	public boolean isValid(String actor, ConstraintValidatorContext context) {
		if (directorRepository.existsByName(actor))
			return false;
		return true;
	}
}
