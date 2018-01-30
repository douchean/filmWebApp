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
import com.endava.movies.data.bean.Comment;
import com.endava.movies.data.bean.Director;
import com.endava.movies.data.bean.Film;
import com.endava.movies.data.dto.ActorDTO;
import com.endava.movies.data.dto.CommentDTO;
import com.endava.movies.data.dto.DirectorDTO;
import com.endava.movies.data.dto.FilmDTO;
import com.endava.movies.data.dto.FilmExtendedDTO;
import com.endava.movies.data.repository.ActorRepository;
import com.endava.movies.data.repository.CommentRepository;
import com.endava.movies.data.repository.DirectorRepository;
import com.endava.movies.data.repository.FilmRepository;
import com.endava.movies.exceptions.AlreadyExisting;
import com.endava.movies.exceptions.InvalidException;
import com.endava.movies.exceptions.NoData;
import com.endava.movies.exceptions.NotExisting;
import com.endava.movies.shells.FilmServing;
import com.endava.movies.utils.SortFields;
import com.endava.movies.utils.Validator;

@Service
public class FilmService implements FilmServing {

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CommentRepository commentRepository;

	@Override
	@Transactional
	public FilmExtendedDTO getFilm(int filmid) throws NotExisting, SQLException {
		if (filmRepository.exists(filmid)) {
			Session session = sessionFactory.openSession();
			Film film = session.get(Film.class, filmid);
			return film.convertExtended();
		} else
			throw new NotExisting("Film with id " + filmid + " doesn't exist!");
	}

	@Override
	@Transactional
	public void newFilm(FilmExtendedDTO filmDTO) throws AlreadyExisting, InvalidException, SQLException {
		Film film = new Film(filmDTO.getTitle(), filmDTO.getYear());
		Session session = sessionFactory.getCurrentSession();

		List<ActorDTO> actors = filmDTO.getActors();
		if (actors != null) {
			Set<Actor> setActors = new HashSet<Actor>(0);
			for (ActorDTO a : actors) {
				String actorName = a.getName();
				if (actorName != null) {// is actor valid
					Actor act;
					if (!actorRepository.existsByName(actorName)) // if actor doesn't exist, create new
					{
						act = new Actor(actorName, a.getBirth());
						session.save(act);
					} else
						act = actorRepository.findByName(actorName);

					if (setActors.isEmpty() || !setActors.contains(act))
						setActors.add(act);
				}
			}
			film.setActors(setActors);
		}

		List<DirectorDTO> directors = filmDTO.getDirectors();
		if (directors != null) {
			Set<Director> setDirectors = new HashSet<Director>(0);
			for (DirectorDTO a : directors) {
				String directorName = a.getName();
				if (directorName != null) { // is actor valid
					Director dir = null;
					if (!directorRepository.existsByName(directorName)) // if actor doesn't exist, create new
					{
						dir = new Director(directorName, a.getBirth());
						session.save(dir);
					} else
						dir = directorRepository.findByName(directorName);

					if (setDirectors.isEmpty() || !setDirectors.contains(directorRepository.findByName(directorName)))
						setDirectors.add(dir);
				}
			}
			film.setDirectors(setDirectors);
		}
		session.save(film);
	}

	@Override
	@Transactional
	public FilmExtendedDTO rate(int rate, int id) throws SQLException, NotExisting, InvalidException {
		Film film = null;
		Validator.validateRate(rate);
		if (!filmRepository.exists(id))
			throw new NotExisting("Film: " + id + " doesn't exist!");

		Session session = sessionFactory.getCurrentSession();
		film = session.get(Film.class, id);
		int num = film.getNumrat();
		if (num == 0) { // first rating
			film.setNumrat(1);
			film.setRating((float) rate);
		} else { // not the first rating
			float currRate = film.getRating();
			currRate *= num;
			currRate += rate;
			num++;
			currRate /= num;

			film.setNumrat(num);
			film.setRating(currRate);
		}
		session.save(film);
		return new FilmExtendedDTO(film.convert());
	}

	@Override
	@Transactional
	public FilmExtendedDTO update(FilmExtendedDTO filmExtendedDTO, int id)
			throws NoData, NotExisting, AlreadyExisting, SQLException, InvalidException {
		if (!filmRepository.exists(id))
			throw new NotExisting("Film with id " + id + " doesn't exist!");

		Session session = sessionFactory.getCurrentSession();
		Film film = session.get(Film.class, id);
		String title = filmExtendedDTO.getTitle();
		int year = filmExtendedDTO.getYear();
		if (year != 0)
			film.setYear(year);
		if (title != null)
			film.setTitle(title);

		List<ActorDTO> actors = filmExtendedDTO.getActors();
		if (actors != null) {
			Set<Actor> setActors = new HashSet<Actor>(0);
			for (ActorDTO a : actors) {
				String actorName = a.getName();
				if (actorName != null) {// is actor valid
					Actor act;
					if (!actorRepository.existsByName(actorName)) // if actor doesn't exist, create new
					{
						act = new Actor(actorName, a.getBirth());
						session.save(act);
					} else
						act = actorRepository.findByName(actorName);

					if (setActors.isEmpty() || !setActors.contains(actorRepository.findByName(actorName)))
						setActors.add(act);
				}
			}
			film.setActors(setActors);
		}

		List<DirectorDTO> directors = filmExtendedDTO.getDirectors();
		if (directors != null) {
			Set<Director> setDirectors = new HashSet<Director>(0);
			for (DirectorDTO a : directors) {
				String directorName = a.getName();
				if (directorName != null) { // is actor valid
					Director dir = null;
					if (!directorRepository.existsByName(directorName)) // if actor doesn't exist, create new
					{
						dir = new Director(directorName, a.getBirth());
						session.save(dir);
					} else
						dir = directorRepository.findByName(directorName);

					if (setDirectors.isEmpty() || !setDirectors.contains(directorRepository.findByName(directorName)))
						setDirectors.add(dir);
				}
			}
			film.setDirectors(setDirectors);
		}
		session.save(film);
		return new FilmExtendedDTO(film.convert());
	}

	@Override
	@Transactional
	public List<FilmDTO> getAllPaginated(String sort, Integer offset, Integer size) throws NoData {
		if (filmRepository.count() != 0) {
			int field = SortFields.film(sort);
			List<Film> list;
			System.out.println("field = " + field);
			switch (field) {
			case 1:
				list = filmRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
				break;
			case 2:
				list = filmRepository.findAll(new Sort(Sort.Direction.DESC, "title"));
				break;
			case 3:
				list = filmRepository.findAll(new Sort(Sort.Direction.ASC, "year"));
				break;
			case 4:
				list = filmRepository.findAll(new Sort(Sort.Direction.DESC, "year"));
				break;
			case 5:
				list = filmRepository.findAll(new Sort(Sort.Direction.ASC, "rating"));
				break;
			case 6:
				list = filmRepository.findAll(new Sort(Sort.Direction.DESC, "rating"));
				break;
			default:
				list = filmRepository.findAll();
			}
			if (offset != null && size != null && offset >= 0 && size >= 0) {
				if (offset + size <= list.size())
					return Film.convertList(list.subList(offset, offset + size));
			}
			return Film.convertList(list);
		}
		throw new NoData("There are no film records!");
	}

	@Override
	@Transactional
	public List<DirectorDTO> selectDirectors(int id) throws NotExisting {
		if (!filmRepository.exists(id))
			throw new NotExisting("There is not a movie with id " + id);

		Session session = sessionFactory.getCurrentSession();
		return Director.convertList(new ArrayList<Director>(session.get(Film.class, id).getDirectors()));
	}

	@Override
	@Transactional
	public List<ActorDTO> selectActors(int id) throws NotExisting {
		if (!filmRepository.exists(id))
			throw new NotExisting("There is not a movie with id " + id);

		Session session = sessionFactory.getCurrentSession();
		return Actor.convertList(new ArrayList<Actor>(session.get(Film.class, id).getActors()));
	}

	@Override
	@Transactional
	public List<CommentDTO> selectComments(int id) throws NotExisting {
		if (!filmRepository.exists(id))
			throw new NotExisting("There is not a movie with id " + id);

		return Comment.convertList(commentRepository.findByIdFilm(String.valueOf(id)));
	}

	@Override
	@Transactional
	public void addComment(CommentDTO comment, int id) throws NotExisting {
		if (!filmRepository.exists(id))
			throw new NotExisting("There is not a movie with id " + id);

		commentRepository.save(new Comment(comment.getUser(), comment.getText(), String.valueOf(id)));
	}

	@Override
	@Transactional
	public void deleteFilm(int id) throws NotExisting {
		if (!filmRepository.exists(id))
			throw new NotExisting("There are no records of the film with id " + id);

		Session session = sessionFactory.getCurrentSession();
		session.delete(session.get(Film.class, id));

	}

}
