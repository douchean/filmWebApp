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

import com.endava.movies.data.dto.ActorDTO;
import com.endava.movies.data.dto.ActorExtendedDTO;

@Entity
@Table(name = "actor")
public class Actor {

	@Override
	public String toString() {
		return "Actor [idActor=" + idActor + ", name=" + name + ", birth=" + birth + ", films=" + films + "]";
	}

	private Integer idActor;
	private String name;
	private Integer birth;
	private Set<Film> films = new HashSet<Film>(0);

	public Actor() {
	}

	public Actor(String name, Integer birth, Set<Film> films) {
		this.name = name;
		this.birth = birth;
		this.films = films;
	}

	public Actor(String name, Integer birth) {
		this.name = name;
		this.birth = birth;
	}

	public void setIdActor(Integer idActor) {
		this.idActor = idActor;
	}

	public void setBirth(Integer birth) {
		this.birth = birth;
	}

	@Id
	@Column(name = "idActor")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getIdActor() {
		return idActor;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "birth")
	public Integer getBirth() {
		return birth;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "starring", joinColumns = {
			@JoinColumn(name = "idActor", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "idFilm", nullable = false, updatable = false) })
	public Set<Film> getFilms() {
		return films;
	}

	public void setFilms(Set<Film> films) {
		this.films = films;
	}

	public ActorDTO convert() {
		return new ActorDTO(idActor, name, birth);
	}

	public ActorExtendedDTO convertExtended() {
		return new ActorExtendedDTO(idActor, name, birth, films);
	}

	public static List<ActorDTO> convertList(List<Actor> list) {
		List<ActorDTO> listDTO = new ArrayList<ActorDTO>();
		for (Actor a : list) {
			ActorDTO actor = a.convert();
			listDTO.add(actor);
		}
		return listDTO;
	}

}
