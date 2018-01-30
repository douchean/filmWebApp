package com.endava.movies.data.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.endava.movies.annotations.Year;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.data.dto.FilmExtendedDTO;

@Entity
@Table(name = "film")
public class Film {

	private Integer idFilm;
	private String title;

	private Integer year;

	private Float rating;
	private Integer numrat;
	private Set<Actor> actors = new HashSet<Actor>(0);
	private Set<Director> directors = new HashSet<Director>(0);

	public Film() {
	}

	public Film(String title, Integer year, Float rating, Integer numrat, Set<Actor> actors, Set<Director> directors) {
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.numrat = numrat;
		this.actors = actors;
		this.directors = directors;
	}

	public Film(Integer idFilm, String title, Integer year, Float rating, Integer numrat) {
		this.idFilm = idFilm;
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.numrat = numrat;
	}

	public Film(Integer id, String title, Integer year) {
		this.idFilm = id;
		this.title = title;
		this.year = year;
		this.numrat = 0;
	}

	public Film(String title, Integer year) {
		this.title = title;
		this.year = year;
		this.numrat = 0;
	}

	@Column(name = "numrat")
	public Integer getNumrat() {
		return numrat;
	}

	public void setNumrat(Integer numrat) {
		this.numrat = numrat;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFilm")
	public Integer getIdFilm() {
		return idFilm;
	}

	@Column(name = "title")
	@NotNull(message = "VALIDUCI1 Movie title missing!! VALIDUCI2")
	public String getTitle() {
		return title;
	}

	@Column(name = "year")
	@Year(message = "VALIDUCI1 The year you entered is unlikely the release year of any movie!! VALIDUCI2")
	public Integer getYear() {
		return year;
	}

	@Column(name = "rating")
	@DecimalMin("1.0")
	@DecimalMax("10.0")
	public Float getRating() {
		return rating;
	}

	public void setIdFilm(Integer idFilm) {
		this.idFilm = idFilm;
	}

	public void setTitle(String tit) {
		title = tit;
	}

	public void setYear(Integer y) {
		year = y;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "starring", joinColumns = {
			@JoinColumn(name = "idFilm", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "idActor", nullable = false, updatable = false) })
	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "directing", joinColumns = {
			@JoinColumn(name = "idFilm", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "idDirector", nullable = false, updatable = false) })
	public Set<Director> getDirectors() {
		return directors;
	}

	public void setDirectors(Set<Director> directors) {
		this.directors = directors;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public FilmDTO convert() {
		if (rating == null)
			return new FilmDTO(idFilm, title, year, 0);
		return new FilmDTO(idFilm, title, year, rating);
	}

	public FilmExtendedDTO convertExtended() {
		if (rating == null)
			return new FilmExtendedDTO(idFilm, title, year, 0, actors, directors);
		return new FilmExtendedDTO(idFilm, title, year, rating, actors, directors);
	}

	public static List<FilmDTO> convertList(List<Film> list) {
		List<FilmDTO> listDTO = new ArrayList<FilmDTO>();
		list.forEach(f -> listDTO.add(f.convert()));

		return listDTO;
	}

}
