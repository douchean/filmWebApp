package com.endava.movies.data.dto;

public class DirectorDTO {

	@Override
	public String toString() {
		return " Director [name=" + name + "]";
	}

	private int idDirector;
	private String name;
	private int birth;

	public DirectorDTO(int idDirector, String name, int birth) {
		this.idDirector = idDirector;
		this.name = name;
		this.birth = birth;
	}

	public DirectorDTO() {
	}

	public int getIdDirector() {
		return idDirector;
	}

	public String getName() {
		return name;
	}

	public int getBirth() {
		return birth;
	}

	public void setIdDirector(int id) {
		idDirector = id;
	}

	public void setName(String name) {
		this.name = name;

	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

}
