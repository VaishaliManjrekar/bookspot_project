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
import com.bookspot.model.Recommend;
import com.bookspot.repository.RecommendRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/bookSpotAPI")
public class RecommendController {
	static int count=0;
	
	
	@Autowired
	private RecommendRepository repository;
	
	@PostMapping("/addToRecommendation")//http://localhost:8080/recommend/addToRecommendation
	public String addtoRecommend(@RequestBody Recommend recommend)
	{
		count=repository.findAll().size()+1; // 
		recommend.setId(count);
		
		
		repository.save(recommend);
		System.out.println(recommend);
		
		return "success";
	}
	@GetMapping("/findRecommendedBook/{isbn}/{userId}")
	public Recommend getBook(@PathVariable("isbn") String isbn, @PathVariable("userId") int userId) {
		Recommend recommended = null;
		Optional<Recommend> op_rec = repository.findByIsbnAndUserid(isbn, userId);
		if(op_rec.isPresent()) {
			recommended = op_rec.get();
		}
		return recommended;
	}	
	@DeleteMapping("/deleteRecommendedBook/{id}")
	public String deleteBook(@PathVariable int id) {
		repository.deleteById(id);
		return "success";
	}
	@GetMapping("/getallrecommend")         //http://localhost:8080/recommend/getallrecommend
	//books to show on recommendation list
	public List<Recommend> getRecommend()
	{
		return repository.findAll();
	}
	
}