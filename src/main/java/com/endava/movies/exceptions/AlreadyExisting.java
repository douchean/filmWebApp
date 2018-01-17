package com.endava.movies.exceptions;

@SuppressWarnings("serial")
public class AlreadyExisting extends DataException {

	private static final long code = 1024L;

	public AlreadyExisting(String msg) {
		super.setMsg(msg);
	}

	@Override
	public long getCode() {
		return code;
	}

}
