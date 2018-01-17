package com.endava.movies.data.dto;

public class ExceptionDTO {

	private String msg;
	private long code;

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

	public ExceptionDTO(String msg, long code) {
		this.msg = msg;
		this.code = code;
	}

}
