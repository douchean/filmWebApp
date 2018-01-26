package com.endava.movies.controller;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.endava.movies.data.dto.ActorDTO;
import com.endava.movies.data.dto.ActorExtendedDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;
import com.endava.movies.shells.ActorServing;

@RestController
@RequestMapping(value = "/actors")
public class ActorController {

	@Autowired
	private ActorServing actorService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public List<ActorDTO> getData(@QueryParam("sort") String sort, @QueryParam("offset") Integer offset,
			@QueryParam("size") Integer size) throws NoData, SQLException {
		return actorService.getAllPaginated(sort, offset, size);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{actorid}", method = RequestMethod.GET)
	public ActorExtendedDTO getActor(@PathVariable int actorid) throws SQLException, NotExisting {
		return actorService.getActor(actorid);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public void addActor(@RequestBody ActorExtendedDTO actor)
			throws AlreadyExisting, InvalidException, SQLException, NotExisting {
		actorService.newActor(actor);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{id}/films", method = RequestMethod.GET)
	public List<FilmDTO> getFilms(@PathVariable int id) throws NotExisting, SQLException, NoData {
		return actorService.selectMovies(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteActor(@PathVariable int id) throws NotExisting, SQLException {
		actorService.deleteActor(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ActorExtendedDTO updateActor(@RequestBody ActorExtendedDTO actorDTO, @PathVariable int id)
			throws InvalidException, SQLException, AlreadyExisting, NotExisting, NoData {
		actorService.update(actorDTO, id);
		return actorService.getActor(id);
	}
}
