package com.bookspot.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookspot.model.Favourite;
import com.bookspot.repository.FavouriteRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/bookSpotAPI")
public class FavouriteController {
	static int count=0;
	@Autowired
	private FavouriteRepository repository;

	@PostMapping("/addFavouriteBook")
	public Favourite saveBook(@RequestBody Favourite favourite) {
		count=repository.findAll().size()+1;
		favourite.setId(count);
		return repository.save(favourite);
	}
	@GetMapping("/getFavouriteBooksByUserId/{userid}")
	public List<Favourite> getFavouriteBooksByid(@PathVariable int userid) {
		return repository.findAllByUserid(userid);
	}

	@GetMapping("/findAllFavouriteBooks")
	public List<Favourite> getBooks() {
		return repository.findAll();
	}

	@GetMapping("/findFavouriteBook/{isbn}/{userId}")
	public Favourite getBook(@PathVariable("isbn") String isbn, @PathVariable("userId") int userId) {
		Favourite fav = null;
		Optional<Favourite> op_fav = repository.findByIsbnAndUserid(isbn, userId);
		if(op_fav.isPresent()) {
			fav = op_fav.get();
		}
		return fav;
	}	

	@DeleteMapping("/deleteFavouriteBook/{id}")
	public String deleteBook(@PathVariable int id) {
		repository.deleteById(id);
		return "success";
	}

}
