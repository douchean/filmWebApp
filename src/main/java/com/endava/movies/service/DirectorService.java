package com.endava.movies.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.endava.movies.data.bean.Director;
import com.endava.movies.data.bean.Film;
import com.endava.movies.data.dto.DirectorDTO;
import com.endava.movies.data.dto.DirectorExtendedDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.data.repository.DirectorRepository;
import com.endava.movies.data.repository.FilmRepository;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;
import com.endava.movies.shells.DirectorServing;
import com.endava.movies.utils.SortFields;
import com.endava.movies.utils.Validator;

@Service
public class DirectorService implements DirectorServing {

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public DirectorExtendedDTO getDirector(int directorId) throws SQLException, NotExisting {
		if (directorRepository.exists(directorId)) {
			Session session = sessionFactory.openSession();
			Director director = session.get(Director.class, directorId);
			return director.convertExtended();
		} else
			throw new NotExisting("Director with id " + directorId + " doesn't exist!");
	}

	@Override
	@Transactional
	public void newDirector(DirectorExtendedDTO directorDTO)
			throws SQLException, AlreadyExisting, InvalidException, NotExisting {
		String name = directorDTO.getName();
		int birth = directorDTO.getBirth();
		Validator.newDirectorValidator(name, birth, directorRepository.existsByName(name));

		Director director = new Director(name, birth);
		List<FilmDTO> movies = directorDTO.getFilms();
		Session session = sessionFactory.getCurrentSession();
		if (movies != null) {
			Set<Film> setFilm = new HashSet<Film>(0);
			for (FilmDTO f : movies) {
				String title = f.getTitle();
				int year = f.getYear();
				if (title != null) {
					Film film = null;
					if (!filmRepository.existsByTitle(title)) {
						film = new Film(title, year);
						session.save(film);
					} else
						film = filmRepository.findByTitle(title);

					if (setFilm.isEmpty() || !setFilm.contains(film))
						setFilm.add(film);
				}
			}
			director.setFilms(setFilm);
		}
		session.save(director);
	}

	@Override
	@Transactional
	public void deleteDirector(int id) throws SQLException, NotExisting {
		if (!directorRepository.exists(id))
			throw new NotExisting("Director with id: " + id + " doesn't exist!");
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.get(Director.class, id));

	}

	@Override
	@Transactional
	public List<DirectorDTO> getAllPaginated(String sort, Integer offset, Integer size) throws NoData, SQLException {
		if (directorRepository.count() == 0)
			throw new NoData("There are no director records!");
		int field = SortFields.human(sort);
		List<Director> list;
		switch (field) {
		case (1):
			list = directorRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
			break;
		case (2):
			list = directorRepository.findAll(new Sort(Sort.Direction.DESC, "name"));
			break;
		case (3):
			list = directorRepository.findAll(new Sort(Sort.Direction.ASC, "birth"));
			break;
		case (4):
			list = directorRepository.findAll(new Sort(Sort.Direction.DESC, "birth"));
			break;
		default:
			list = directorRepository.findAll();
		}

		if (offset != null && size != null && offset >= 0 && size >= 0) {
			if (offset + size <= list.size())
				return Director.convertList(list.subList(offset, offset + size));
		}
		return Director.convertList(list);
	}

	@Override
	@Transactional
	public void update(DirectorExtendedDTO directorDTO, int id)
			throws SQLException, InvalidException, NotExisting, AlreadyExisting, NoData {
		if (!directorRepository.exists(id))
			throw new NotExisting("Directir with id " + id + " doesn't exist!");
		Session session = sessionFactory.getCurrentSession();
		Director director = session.get(Director.class, id);
		String name = directorDTO.getName();
		int birth = directorDTO.getBirth();
		List<FilmDTO> movies = directorDTO.getFilms();
		Validator.validateBirth(birth);

		if (birth != 0)
			director.setBirth(birth);
		if (name != null) {
			if (directorRepository.existsByName(name))
				throw new AlreadyExisting("Director named " + name + " already exists!");
			director.setName(name);
		}
		if (movies != null) {
			Set<Film> setFilm = new HashSet<Film>(0);
			for (FilmDTO f : movies) {
				String title = f.getTitle();
				int year = f.getYear();
				if (title != null) {
					Film film = null;
					if (!filmRepository.existsByTitle(title)) {
						film = new Film(title, year);
						session.save(film);
					} else
						film = filmRepository.findByTitle(title);

					if (setFilm.isEmpty() || !setFilm.contains(film))
						setFilm.add(film);
				}
			}
			director.setFilms(setFilm);
		}
		session.save(director);
	}

	@Override
	@Transactional
	public List<FilmDTO> selectMovies(int id) throws SQLException, NotExisting, NoData {
		if (!directorRepository.exists(id))
			throw new NotExisting("Director with id: " + id + " doesn't exist!");
		Session session = sessionFactory.getCurrentSession();
		Set<Film> set = session.get(Director.class, id).getFilms();
		List<Film> list = new ArrayList<Film>(set);
		return Film.convertList(list);
	}
}
