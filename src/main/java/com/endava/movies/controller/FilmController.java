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
import com.endava.movies.data.dto.CommentDTO;
import com.endava.movies.data.dto.DirectorDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.data.dto.FilmExtendedDTO;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;
import com.endava.movies.shells.FilmServing;

@RestController
@RequestMapping("/films")
public class FilmController {

	@Autowired
	private FilmServing filmService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public List<FilmDTO> getData(@QueryParam("sort") String sort, @QueryParam("offset") Integer offset,
			@QueryParam("size") Integer size) throws NoData, SQLException {
		return filmService.getAllPaginated(sort, offset, size);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public FilmExtendedDTO getFilm(@PathVariable int id) throws NotExisting, SQLException {
		return filmService.getFilm(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{id}/directors")
	public List<DirectorDTO> getDirectors(@PathVariable int id) throws NoData, NotExisting, SQLException {
		return filmService.selectDirectors(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{id}/actors", method = RequestMethod.GET)
	public List<ActorDTO> getActors(@PathVariable int id) throws NoData, NotExisting, SQLException {
		return filmService.selectActors(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{id}/comments", method = RequestMethod.GET)
	public List<CommentDTO> getComments(@PathVariable int id) throws NoData, NotExisting, SQLException {
		return filmService.selectComments(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public void addFilm(@RequestBody FilmExtendedDTO film)
			throws NotExisting, SQLException, AlreadyExisting, InvalidException {
		filmService.newFilm(film);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT, value = "{id}/rate/{rate}")
	public FilmExtendedDTO rateFilm(@PathVariable int id, @PathVariable int rate)
			throws NotExisting, InvalidException, SQLException {
		return filmService.rate(rate, id);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public FilmExtendedDTO updateFilm(@RequestBody FilmExtendedDTO film, @PathVariable int id)
			throws InvalidException, SQLException, AlreadyExisting, NotExisting, NoData {
		return filmService.update(film, id);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteFilm(@PathVariable int id) throws NotExisting, SQLException {
		filmService.deleteFilm(id);
	}

}
