package com.endava.movies.data.dto;

import javax.validation.ConstraintViolationException;

public class ValidationExceptionDTO {

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public ValidationExceptionDTO(ConstraintViolationException e) {
		String m = e.getMessage();
		int start = m.lastIndexOf("VALIDUCI1") + 9;
		int end = m.lastIndexOf("VALIDUCI2") - 1;
		this.msg = m.substring(start, end);
		this.code = 403;
	}

	private String msg;
	private long code;

}
