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

import com.endava.movies.data.bean.Actor;
import com.endava.movies.data.bean.Film;
import com.endava.movies.data.dto.ActorDTO;
import com.endava.movies.data.dto.ActorExtendedDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.data.repository.ActorRepository;
import com.endava.movies.data.repository.FilmRepository;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;
import com.endava.movies.shells.ActorServing;
import com.endava.movies.utils.SortFields;
import com.endava.movies.utils.Validator;

@Service
public class ActorService implements ActorServing {

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public ActorExtendedDTO getActor(int actorId) throws SQLException, NotExisting {
		if (actorRepository.exists(actorId)) {
			Session session = sessionFactory.openSession();
			Actor actor = session.get(Actor.class, actorId);
			return actor.convertExtended();
		} else
			throw new NotExisting("Actor with id " + actorId + " doesn't exist!");
	}

	@Override
	@Transactional
	public void newActor(ActorExtendedDTO actorDTO)
			throws SQLException, AlreadyExisting, InvalidException, NotExisting {
		String name = actorDTO.getName();
		int birth = actorDTO.getBirth();
		Validator.newActorValidator(name, birth, actorRepository.existsByName(name));

		Actor actor = new Actor(name, birth);
		List<FilmDTO> movies = actorDTO.getFilms();
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
			actor.setFilms(setFilm);
		}
		session.save(actor);
	}

	@Override
	@Transactional
	public void deleteActor(int id) throws SQLException, NotExisting {
		if (!actorRepository.exists(id))
			throw new NotExisting("Actor with id: " + id + " doesn't exist!");
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.get(Actor.class, id));
	}

	@Override
	@Transactional
	// ZA BOGA MILOGA POGLE KOBAJE SREDI OVO AKO IKAKO MOZES
	public List<ActorDTO> getAllPaginated(String sort, Integer offset, Integer size) throws NoData, SQLException {
		if (actorRepository.count() == 0)
			throw new NoData("There are no actor records!");
		int field = SortFields.human(sort);
		List<Actor> list;
		switch (field) {
		case (1):
			list = actorRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
			break;
		case (2):
			list = actorRepository.findAll(new Sort(Sort.Direction.DESC, "name"));
			break;
		case (3):
			list = actorRepository.findAll(new Sort(Sort.Direction.ASC, "birth"));
			break;
		case (4):
			list = actorRepository.findAll(new Sort(Sort.Direction.DESC, "birth"));
			break;
		default:
			list = actorRepository.findAll();
		}
		if (offset != null && size != null && offset >= 0 && size >= 0) {
			if (offset + size <= list.size())
				return Actor.convertList(list.subList(offset, offset + size));
		}
		return Actor.convertList(list);
	}

	@Override
	@Transactional
	public void update(ActorExtendedDTO actorDTO, int id)
			throws SQLException, InvalidException, NotExisting, AlreadyExisting, NoData {
		if (!actorRepository.exists(id))
			throw new NotExisting("Actor with id " + id + " doesn't exist!");

		Session session = sessionFactory.getCurrentSession();
		Actor actor = session.get(Actor.class, id);
		String name = actorDTO.getName();
		int birth = actorDTO.getBirth();
		List<FilmDTO> movies = actorDTO.getFilms();
		Validator.validateBirth(birth);

		if (birth != 0)
			actor.setBirth(birth);
		if (name != null) {
			if (actorRepository.existsByName(name))
				throw new AlreadyExisting("Actor named " + name + " already exists!");
			actor.setName(name);
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
			actor.setFilms(setFilm);
		}
		session.merge(actor);
	}

	@Override
	@Transactional
	public List<FilmDTO> selectMovies(int id) throws SQLException, NotExisting, NoData {
		if (!actorRepository.exists(id))
			throw new NotExisting("Director with id: " + id + " doesn't exist!");
		Session session = sessionFactory.getCurrentSession();
		Set<Film> set = session.get(Actor.class, id).getFilms();
		List<Film> list = new ArrayList<Film>(set);
		return Film.convertList(list);
	}

}
