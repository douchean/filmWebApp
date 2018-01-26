package com.endava.movies.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endava.movies.data.bean.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);
}
