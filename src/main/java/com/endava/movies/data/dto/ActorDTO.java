package com.endava.movies.data.dto;

import org.springframework.lang.Nullable;

public class ActorDTO {

	@Override
	public String toString() {
		return " Actor [name=" + name + "]";
	}

	private int idActor;
	private String name;
	private int birth;

	public ActorDTO(int idActor, String name, int birth) {
		this.idActor = idActor;
		this.name = name;
		this.birth = birth;
	}

	public ActorDTO() {
	}

	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

	@Nullable
	public int getIdActor() {
		return idActor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirth() {
		return birth;
	}
}
