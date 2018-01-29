package com.endava.movies.controller;

import java.sql.SQLException;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.endava.movies.data.dto.ExceptionDTO;
import com.endava.movies.data.dto.ValidationExceptionDTO;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.DataException;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;

@ControllerAdvice
public class ExceptionController {
	private static final Logger logger = Logger.getLogger(ExceptionController.class);

	@ExceptionHandler({ AlreadyExisting.class, NoData.class, NotExisting.class, InvalidException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleAlreadyExisting(DataException e) {
		logger.warn(e);
		return new ExceptionDTO(e.getMsg(), e.getCode());
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	protected ExceptionDTO handleAccessDenied(AccessDeniedException e) {
		logger.warn(e);
		return new ExceptionDTO("Access denied", 403);
	}

	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected ExceptionDTO handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		logger.warn(e);
		return new ExceptionDTO("Bad Request", 406);
	}

	@ExceptionHandler(value = SQLException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleSQLException(SQLException e) {
		logger.error("SQL Exception ocuured.. " + e);
		return new ExceptionDTO("Server error", e.getErrorCode());
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ValidationExceptionDTO handleConstraintViolationException(ConstraintViolationException e) {
		logger.warn(e);
		return new ValidationExceptionDTO(e);

	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error(e);
		e.printStackTrace();
		return new ExceptionDTO("Http request not supported", 101);
	}

	@ExceptionHandler(value = Throwable.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleThrowable(Throwable e) {
		logger.error(e);
		return new ExceptionDTO("Server error", 500);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleException(Exception e) {
		logger.error(e);
		e.printStackTrace();
		return new ExceptionDTO("Server error", 500);
	}

}
