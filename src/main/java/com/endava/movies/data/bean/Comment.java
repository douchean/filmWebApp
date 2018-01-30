package com.endava.movies.data.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.endava.movies.data.dto.CommentDTO;

@Document(collection = "comments")
public class Comment {

	@Id
	private String id;

	private String text;
	private String user;
	private String idFilm;

	public Comment(String user, String text, String idFilm) {
		this.user = user;
		this.text = text;
		this.idFilm = idFilm;
	}

	public String getFilm() {
		return idFilm;
	}

	public void setFilm(String idFilm) {
		this.idFilm = idFilm;
	}

	public String getText() {
		return text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public CommentDTO convert() {
		return new CommentDTO(user, text);
	}

	public static List<CommentDTO> convertList(List<Comment> list) {
		List<CommentDTO> listDTO = new ArrayList<CommentDTO>();
		list.forEach(c -> listDTO.add(c.convert()));

		return listDTO;
	}

}
