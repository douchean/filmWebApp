package com.endava.movies.shells;

import java.sql.SQLException;
import java.util.List;

import com.endava.movies.data.dto.ActorDTO;
import com.endava.movies.data.dto.ActorExtendedDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;

public interface ActorServing {

	// public ActorExtendedDTO getActor(String name) throws SQLException,
	// NotExisting;

	public ActorExtendedDTO getActor(int actorId) throws SQLException, NotExisting;

	public void newActor(ActorExtendedDTO actor) throws SQLException, AlreadyExisting, InvalidException, NotExisting;

	public void deleteActor(int id) throws SQLException, NotExisting;

	public List<ActorDTO> getAllPaginated(String sort, Integer offset, Integer size) throws NoData, SQLException;

	public void update(ActorExtendedDTO actorDTO, int id)
			throws SQLException, InvalidException, NotExisting, AlreadyExisting, NoData;

	List<FilmDTO> selectMovies(int id) throws SQLException, NotExisting, NoData;

}
