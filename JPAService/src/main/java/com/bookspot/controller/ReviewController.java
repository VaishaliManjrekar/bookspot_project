package com.bookspot.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookspot.model.Review;
import com.bookspot.repository.ReviewRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/bookSpotAPI")
public class ReviewController {
	@Autowired
	ReviewRepository reviewRepository;
	
	@PostMapping("/addReview")
	public ResponseEntity<Review> addReview(@RequestBody Review review) {
		review.setCreatedOn(LocalDateTime.now());
		Review r = reviewRepository.save(review);
		return new ResponseEntity<Review>(r, HttpStatus.OK);
	}
	
	@GetMapping("/getReview/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable("reviewId") int reviewId) {
		Optional<Review> review = reviewRepository.findById(reviewId);
		if(review.isPresent()) {
			Review r = review.get();
			return new ResponseEntity<Review>(r, HttpStatus.OK);
		}
		return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getReviewsByBook/{bookISBN}")
	public ResponseEntity<List<Review>> getReviewsByBook(@PathVariable("bookISBN") String bookISBN) {
		List<Review> listCustomer = reviewRepository.findAllByBookISBN(bookISBN);
		return new ResponseEntity<List<Review>>(listCustomer, HttpStatus.OK);
	}
	@DeleteMapping("/deleteReview/{reviewId}")
	public ResponseEntity<Review> deleteCustomer(@PathVariable("reviewId") int reviewId) {
		Optional<Review> r = reviewRepository.findById(reviewId);
		if(reviewRepository.existsById(reviewId)) {
			Review review = r.get();
			reviewRepository.deleteById(reviewId);
			return new ResponseEntity<Review>(review, HttpStatus.OK);
		}
		return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/updateReview")
	public ResponseEntity<Review> updateReview(@RequestBody Review review) {
		Review r = reviewRepository.save(review);
		return new ResponseEntity<Review>(r, HttpStatus.OK);
	}
	
	@GetMapping("/getAllReview")
	public ResponseEntity<List<Review>> getCustomerDetails() {
		List<Review> listReview = reviewRepository.findAll();
		return new ResponseEntity<List<Review>>(listReview, HttpStatus.OK);
	}
}
