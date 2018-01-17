package com.endava.movies.shells;

import java.sql.SQLException;
import java.util.List;

import com.endava.movies.data.dto.DirectorDTO;
import com.endava.movies.data.dto.DirectorExtendedDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;

public interface DirectorServing {
	// public DirectorExtendedDTO getDirector(String name) throws SQLException,
	// NotExisting;

	public DirectorExtendedDTO getDirector(int directorId) throws SQLException, NotExisting;

	public void newDirector(DirectorExtendedDTO directorDTO)
			throws SQLException, AlreadyExisting, InvalidException, NotExisting;

	public void deleteDirector(int id) throws SQLException, NotExisting;

	public List<DirectorDTO> getAllPaginated(String sort, Integer offset, Integer size) throws NoData, SQLException;

	public void update(DirectorExtendedDTO directorDTO, int id)
			throws SQLException, InvalidException, NotExisting, AlreadyExisting, NoData;

	public List<FilmDTO> selectMovies(int id) throws SQLException, NotExisting, NoData;

}
