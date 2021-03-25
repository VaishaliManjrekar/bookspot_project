package com.bookspot.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookspot.model.Recommend;

public interface RecommendRepository extends MongoRepository<Recommend, Integer>{
	public Optional<Recommend> findByIsbnAndUserid(String isbn, int userId);
}
