package com.endava.movies.data.dto;

public class CommentDTO {

	private String user;
	private String text;

	public CommentDTO(String user, String text) {
		this.user = user;
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
