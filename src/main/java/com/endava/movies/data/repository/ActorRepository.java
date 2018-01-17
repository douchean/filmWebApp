package com.endava.movies.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.endava.movies.data.bean.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

	Actor findByName(String name);

	Actor findByIdActor(Integer actorId);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN 'true' ELSE 'false' END FROM Actor a WHERE a.name LIKE ?1")
	public Boolean existsByName(String name);

}
