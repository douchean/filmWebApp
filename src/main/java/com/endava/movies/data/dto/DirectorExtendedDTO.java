package com.endava.movies.data.dto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.endava.movies.data.bean.Film;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class DirectorExtendedDTO extends DirectorDTO {
	private List<FilmDTO> films;

	@Override
	public String toString() {
		return super.toString() + ":  [films=" + films + "]";
	}

	public DirectorExtendedDTO() {
		super();
	}

	public DirectorExtendedDTO(int idDirector, String name, int birth) {
		super(idDirector, name, birth);
	}

	public DirectorExtendedDTO(DirectorDTO director) throws SQLException {
		setIdDirector(director.getIdDirector());
		setName(director.getName());
		setBirth(director.getBirth());
	}

	public DirectorExtendedDTO(Integer idDirector, String name, Integer birth, Set<Film> films) {
		super(idDirector, name, birth);
		List<Film> movies = new ArrayList<Film>(films);
		this.films = Film.convertList(movies);
	}

	public List<FilmDTO> getFilms() {
		return films;
	}

	public void setFilms(List<FilmDTO> films) {
		this.films = films;
	}

}
