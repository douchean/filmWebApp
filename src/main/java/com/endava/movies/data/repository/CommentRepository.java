package com.endava.movies.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.endava.movies.data.bean.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

	Optional<Comment> findById(String id);

	List<Comment> findByIdFilm(String idFilm);
}
