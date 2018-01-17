package com.endava.movies.exceptions;

@SuppressWarnings("serial")
public class NoData extends DataException {

	private static final long code = 1010L;

	public NoData(String msg) {
		super.setMsg(msg);
	}

	public long getCode() {
		return code;
	}

}
