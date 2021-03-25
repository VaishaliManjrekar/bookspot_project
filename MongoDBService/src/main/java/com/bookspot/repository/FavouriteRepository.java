package com.bookspot.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookspot.model.*;



public interface FavouriteRepository extends MongoRepository<Favourite, Integer>{
public List<Favourite> findAllByUserid(int userid);
public Optional<Favourite> findByIsbnAndUserid(String isbn, int userId);
}