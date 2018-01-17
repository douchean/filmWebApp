package com.endava.movies.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.endava.movies.data.bean.Film;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class ActorExtendedDTO extends ActorDTO {
	private List<FilmDTO> films;

	@Override
	public String toString() {
		return super.toString() + ":  [films=" + films + "]";
	}

	public ActorExtendedDTO() {
		super();
	}

	public ActorExtendedDTO(int idActor, String name, int birth) {
		super(idActor, name, birth);
	}

	public List<FilmDTO> getFilms() {
		return films;
	}

	public void setFilms(List<FilmDTO> films) {
		this.films = films;
	}

	public ActorExtendedDTO(ActorDTO actor) {
		setIdActor(actor.getIdActor());
		setName(actor.getName());
		setBirth(actor.getBirth());
	}

	public ActorExtendedDTO(Integer idActor, String name, Integer birth, Set<Film> films) {
		super(idActor, name, birth);
		List<Film> movies = new ArrayList<Film>(films);
		this.films = Film.convertList(movies);
	}
}
