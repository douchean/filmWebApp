package com.endava.movies.data.bean;

import java.sql.SQLException;
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

import com.endava.movies.annotations.UniqueDirector;
import com.endava.movies.annotations.Year;
import com.endava.movies.data.dto.DirectorDTO;
import com.endava.movies.data.dto.DirectorExtendedDTO;

@Entity
@Table(name = "director")
public class Director {

	private Integer idDirector;
	private String name;
	private Integer birth;
	private Set<Film> films = new HashSet<Film>(0);

	public Director() {
	}

	public Director(String name, Integer birth, Set<Film> films) {
		this.name = name;
		this.birth = birth;
		this.films = films;
	}

	public Director(String name2, Integer birth2) {
		name = name2;
		birth = birth2;
	}

	@Id
	@Column(name = "idDirector")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getIdDirector() {
		return idDirector;
	}

	@Column(name = "name")
	@NotNull(message = "VALIDUCI1 Director's name missing!! VALIDUCI2")
	@UniqueDirector(message = "VALIDUCI1 Director's name already exists!! VALIDUCI2")
	public String getName() {
		return name;
	}

	@Column(name = "birth")
	@Year(message = "VALIDUCI1 The year you entered is unlikely to be the birth year of any director!! VALIDUCI2")
	public Integer getBirth() {
		return birth;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "directing", joinColumns = {
			@JoinColumn(name = "idDirector", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "idFilm", nullable = false, updatable = false) })
	public Set<Film> getFilms() {
		return films;
	}

	public void setFilms(Set<Film> films) {
		this.films = films;
	}

	public void setIdDirector(Integer id) {
		idDirector = id;
	}

	public void setName(String name) throws SQLException {
		this.name = name;

	}

	public void setBirth(Integer birth) throws SQLException {
		this.birth = birth;
	}

	public DirectorDTO convert() {
		return new DirectorDTO(idDirector, name, birth);
	}

	public DirectorExtendedDTO convertExtended() {
		return new DirectorExtendedDTO(idDirector, name, birth, films);
	}

	public static List<DirectorDTO> convertList(List<Director> list) {
		List<DirectorDTO> listDTO = new ArrayList<DirectorDTO>();
		list.forEach(d -> listDTO.add(d.convert()));

		return listDTO;
	}

}
