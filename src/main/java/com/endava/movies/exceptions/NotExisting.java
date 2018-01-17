package com.endava.movies.exceptions;

@SuppressWarnings("serial")
public class NotExisting extends DataException {

	private static final long code = 404L;

	public NotExisting(String m) {
		super.setMsg(m);
	}

	@Override
	public long getCode() {
		return code;
	}

}
