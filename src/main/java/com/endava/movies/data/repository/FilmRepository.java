package com.endava.movies.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.endava.movies.data.bean.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

	public Film findByIdFilm(int filmid);

	public Film findByTitle(String title);

	@Query("SELECT CASE WHEN COUNT(f) > 0 THEN 'true' ELSE 'false' END FROM Film f WHERE f.title LIKE ?1")
	public Boolean existsByTitle(String title);

}
