package com.endava.movies.controller;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.endava.movies.data.dto.DirectorDTO;
import com.endava.movies.data.dto.DirectorExtendedDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;
import com.endava.movies.service.DirectorService;

@RestController
@RequestMapping(value = "/directors")
public class DirectorController {

	@Autowired
	private DirectorService directorService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public List<DirectorDTO> getData(@QueryParam("sort") String sort, @QueryParam("offset") Integer offset,
			@QueryParam("size") Integer size) throws NoData, SQLException {
		return directorService.getAllPaginated(sort, offset, size);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{directorId}", method = RequestMethod.GET, produces = { "application/xml",
			"application/json" })
	public DirectorExtendedDTO getDirector(@PathVariable int directorId) throws SQLException, NotExisting {
		return directorService.getDirector(directorId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, produces = { "application/xml", "application/json" }, consumes = {
			"application/xml", "application/json" })
	public void addDirector(@RequestBody DirectorExtendedDTO director)
			throws AlreadyExisting, InvalidException, SQLException, NotExisting {
		directorService.newDirector(director);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{id}/films", method = RequestMethod.GET, produces = { "application/xml",
			"application/json" })
	public List<FilmDTO> getFilms(@PathVariable int id) throws NotExisting, SQLException, NoData {
		return directorService.selectMovies(id);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteDirector(@PathVariable int id) throws NotExisting, SQLException {
		directorService.deleteDirector(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public void updateDirector(@RequestBody DirectorExtendedDTO director, @PathVariable int id)
			throws InvalidException, SQLException, AlreadyExisting, NotExisting, NoData {
		directorService.update(director, id);
	}

}
