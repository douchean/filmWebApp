package com.endava.movies.exceptions;

@SuppressWarnings("serial")
public abstract class DataException extends Exception { // msg property i setter

	private String msg;

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public abstract long getCode();

	public String getMsg() {
		return msg;
	};
}
