package com.bookspot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookspot.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	User findByEmailAndPassword(String email,
			String password);
	Optional<User> findByEmail(String email);
	
}
