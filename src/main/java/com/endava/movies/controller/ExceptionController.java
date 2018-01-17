package com.endava.movies.controller;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.endava.movies.data.dto.ExceptionDTO;
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

	@ExceptionHandler(value = SQLException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleSQLException(SQLException e) {
		logger.error("SQL Exception ocuured.. ");
		return new ExceptionDTO("Server error", e.getErrorCode());
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleException(Exception e) {
		logger.error(e);
		e.printStackTrace();
		return new ExceptionDTO("Server error", 500);
	}

	@ExceptionHandler(value = Throwable.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleThrowable(Throwable e) {
		logger.error(e);
		return new ExceptionDTO("Server error", 500);
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ExceptionDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error(e);
		e.printStackTrace();
		return new ExceptionDTO("Http request not supported", 101);
	}

}
