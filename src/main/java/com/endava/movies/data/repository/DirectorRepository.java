package com.endava.movies.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.endava.movies.data.bean.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

	Director findByName(String name);

	Director findByIdDirector(int directorId);

	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN 'true' ELSE 'false' END FROM Director d WHERE d.name LIKE ?1")
	public Boolean existsByName(String name);

}
