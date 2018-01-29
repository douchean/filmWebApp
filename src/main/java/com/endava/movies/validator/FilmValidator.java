package com.endava.movies.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.endava.movies.annotations.UniqueFilm;
import com.endava.movies.data.repository.FilmRepository;

@Component
public class FilmValidator implements ConstraintValidator<UniqueFilm, String> {
	@Autowired
	private FilmRepository filmRepository;

	@Override
	public void initialize(UniqueFilm constraintAnnotation) {
		org.springframework.web.context.support.SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (filmRepository.existsByTitle(value))
			return false;
		return true;
	}

}
