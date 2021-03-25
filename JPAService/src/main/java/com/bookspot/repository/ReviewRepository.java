package com.bookspot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookspot.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findAllByBookISBN(String bookISBN);
}
