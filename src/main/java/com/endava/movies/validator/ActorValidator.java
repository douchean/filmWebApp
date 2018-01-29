package com.endava.movies.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.endava.movies.annotations.UniqueActor;
import com.endava.movies.data.repository.ActorRepository;

public class ActorValidator implements ConstraintValidator<UniqueActor, String> {
	@Autowired
	private ActorRepository actorRepository;

	@Override
	public void initialize(UniqueActor constraintAnnotation) {
		org.springframework.web.context.support.SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

	}

	@Override
	public boolean isValid(String actor, ConstraintValidatorContext context) {
		if (actorRepository.existsByName(actor))
			return false;
		return true;
	}

}
