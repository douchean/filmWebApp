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
import javax.validation.constraints.NotNull;

import com.endava.movies.annotations.UniqueActor;
import com.endava.movies.annotations.Year;
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

	// VALIDUCIs are used to format the validation messages in
	// ConstraintViolationException so we can
	// control the message that users receive when such exceptions occur
	@Column(name = "name")
	@NotNull(message = "VALIDUCI1 Actor's name missing!! VALIDUCI2")
	@UniqueActor(message = "VALIDUCI1 Actor's name already exists!! VALIDUCI2")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "birth")
	@Year(message = "VALIDUCI1 The year you entered is unlikely to be the birth year of any actor!! VALIDUCI2")
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
		list.forEach(a -> listDTO.add(a.convert()));

		return listDTO;
	}

}
