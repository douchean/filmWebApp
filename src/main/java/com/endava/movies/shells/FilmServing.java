package com.endava.movies.shells;

import java.sql.SQLException;
import java.util.List;

import com.endava.movies.data.dto.ActorDTO;
import com.endava.movies.data.dto.CommentDTO;
import com.endava.movies.data.dto.DirectorDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.data.dto.FilmExtendedDTO;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;

public interface FilmServing {

	// public FilmExtendedDTO getFilm(String name) throws DataException,
	// SQLException;

	public FilmExtendedDTO getFilm(int filmid) throws SQLException, NotExisting;

	public void newFilm(FilmExtendedDTO film) throws AlreadyExisting, SQLException, InvalidException;

	public void deleteFilm(int id) throws SQLException, NotExisting;

	public FilmExtendedDTO rate(int rate, int id) throws SQLException, NotExisting, InvalidException;

	public FilmExtendedDTO update(FilmExtendedDTO filmDTO, int id)
			throws SQLException, InvalidException, NotExisting, AlreadyExisting, NoData;

	public List<FilmDTO> getAllPaginated(String sort, Integer offset, Integer size) throws NoData, SQLException;

	public List<DirectorDTO> selectDirectors(int id) throws NoData, NotExisting, SQLException;

	public List<ActorDTO> selectActors(int id) throws NoData, NotExisting, SQLException;

	public List<CommentDTO> selectComments(int id) throws NotExisting;

}
