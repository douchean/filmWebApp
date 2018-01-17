package com.endava.movies.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.endava.movies.data.bean.Actor;
import com.endava.movies.data.bean.Director;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class FilmExtendedDTO extends FilmDTO {

	@Override
	public String toString() {
		return super.toString() + ":  [actors=" + actors + "]";
	}

	private List<ActorDTO> actors;
	private List<DirectorDTO> directors;

	public FilmExtendedDTO() {
		super();
	}

	public FilmExtendedDTO(int idFilm, String title, int year, float rating) {
		super(idFilm, title, year, rating);
	}

	public FilmExtendedDTO(String title, int year, float rating) {
		super(title, year, rating);
	}

	public FilmExtendedDTO(String title, int year, float rating, List<ActorDTO> actors, List<DirectorDTO> directors) {
		super(title, year, rating);
		this.actors = actors;
		this.directors = directors;
	}

	public FilmExtendedDTO(int idFilm, String title, int year, float rating, Set<Actor> actors,
			Set<Director> directors) {
		super(idFilm, title, year, rating);
		List<Actor> list = new ArrayList<Actor>(actors);
		this.actors = Actor.convertList(list);
		List<Director> listDirector = new ArrayList<Director>(directors);
		this.directors = Director.convertList(listDirector);
	}

	public List<ActorDTO> getActors() {
		return actors;
	}

	public void setActors(List<ActorDTO> actors) {
		this.actors = actors;
	}

	public List<DirectorDTO> getDirectors() {
		return directors;
	}

	public void setDirectors(List<DirectorDTO> directors) {
		this.directors = directors;
	}

	public FilmExtendedDTO(FilmDTO film) {
		setIdFilm(film.getIdFilm());
		setRating(film.getRating());
		setTitle(film.getTitle());
		setYear(film.getYear());
	}

}
