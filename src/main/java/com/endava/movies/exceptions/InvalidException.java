package com.endava.movies.exceptions;

@SuppressWarnings("serial")
public class InvalidException extends DataException {

	private static final long code = 1020L;

	public InvalidException(String msg) {
		super.setMsg(msg);
	}

	@Override
	public long getCode() {
		return code;
	}

}
