package com.bookspot.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookspot.exception.*;
import com.bookspot.model.User;
import com.bookspot.repository.UserRepository;
import com.bookspot.service.EmailService;
import com.bookspot.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/bookSpotAPI")
public class UserController {
     
	
	@Autowired
	private UserRepository userRepository;
	private UserService userService;
	private EmailService emailService;
	@Autowired
	public UserController(UserService userService, UserRepository userRepository, EmailService emailService) {
		super();
		this.userService = userService;
		this.userRepository = userRepository;
		this.emailService = emailService;
	}
	
	
	@PostMapping("/addUser")//http://localhost:8080/bookSpotAPI/addUser
	public ResponseEntity<?> saveUser(@RequestBody User user){
		String emailSubject = "Registration Successful!!!";
		String emailMessage = "Hello "+user.getFirstName()+" "+user.getLastName()+" "+" Welcome to world of BookSpot!!"
				+ " We are enlightened to have you as a member of BookSpot.Reading is important, and its value can’t be overestimated when it comes to both adults and children. It might easily help you develop your own way of thinking and point of view; it gives you endless knowledge on various topics (depending on what you like and choose) "
				+ "and broadens your horizons – all while keeping your mind active and you entertained. Reading a book, and taking the time to ruminate and make inferences and engage the imaginational processing, is more cognitively enriching, without a doubt,"
				+ " than the short little bits that you might get if you’re into the 30-second digital mode.BookSpot will give you a wonderful experience,will boost your vocabulary and will add on good things in your life."
				+ " " +" Moreover, if you don’t have much time to read all the books you want from cover to cover,you can try book reviews,ratings and summaries. Explore and  enjoy reading at your very own BookSpot.";
						
		ResponseEntity<?> response = null;
		user.setRole("user");
		try {
			userService.registerUser(user);
			response = new
					ResponseEntity<String>(HttpStatus.OK);
			emailService.sendEmail(emailMessage, emailSubject, user.getEmail());
		} catch (UserExistsException e) {
			response = new
					ResponseEntity<String>(HttpStatus.CONFLICT);
			e.printStackTrace();
		} catch (Exception e) {
			response = new
					ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return response;
	}
	
	
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<User> getUser(@PathVariable("userId") int id){
		Optional<User> c = userRepository.findById(id);
		if(c.isPresent()) {
			User uk = c.get();
			return new ResponseEntity<User>(uk,HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getUserDetails")
	public ResponseEntity<List<User>> getAllUserDetails(){
		List<User> lstUser = userRepository.findAll();
		return new ResponseEntity<List<User>>(lstUser,HttpStatus.OK);
	}

	//http://localhost:8080/bookSpotAPI/login
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user)
			throws UserNotFoundException {
		User validUser = userService.login(user.getEmail(),
				user.getPassword());
		if (validUser == null) {
			//throw new UserNotFoundException();
			return new ResponseEntity<User>(validUser, HttpStatus.UNAUTHORIZED);
		}
		// Build the Json Web Token
		//UserId, name, email password, 
		String token =
				Jwts.builder().
				setId(validUser.getEmail()).
				setSubject(validUser.getRole())
				.setIssuedAt(new Date()).
				signWith(SignatureAlgorithm.HS256,
						"usersecretkey").
				compact();
		// Add it to a Map and send the map in response body
//		Map<String, String> map1 = new
//				HashMap<String, String>();
//		
//		map1.put("token", token);
//		map1.put("message", "User Successfully logged in");
		
		validUser.setToken(token);
		User u = userRepository.save(validUser); //update
		return new ResponseEntity<User>(validUser, HttpStatus.OK);
	}
	@PostMapping("/authenicate")
	public ResponseEntity<?> isAuthenicated(@RequestBody User user) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		boolean isAuthenicateUser = false;
		if(optionalUser.isPresent()) {
			User u = optionalUser.get();
			if(u.getToken().equals(user.getToken())) {
				isAuthenicateUser = true;
			}
		}
		Map<String, Boolean> map1 = new
				HashMap<String, Boolean>();
		map1.put("isAuthenticated", isAuthenicateUser);
		return new ResponseEntity<>(map1, HttpStatus.OK);
	}
	@GetMapping("/forgot-password/{email}")
	public ResponseEntity<User> sendOTP(@PathVariable ("email") String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			User u = user.get();
			String subject = "Reset Password link";
			String message = "Hello "+u.getFirstName()+" "+u.getLastName()+" ,"+"for Reset Password"+" " + "click Here" +" "+ "http://localhost:4200/request-response/"+u.getId() ;
			String to = email;
			boolean flag = this.emailService.sendEmail(message, subject, to);
			if(flag)
				return new ResponseEntity<User>(u, HttpStatus.OK);
			else
				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);//Email not send
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable ("userId") int userId, @RequestBody User user) {
		Optional<User> op_user = userRepository.findById(userId);
		if(op_user.isPresent()) {
			User newData = op_user.get();
			String newPassword = BCrypt.hashpw(user.getPassword(),
					BCrypt.gensalt());
			newData.setPassword(newPassword);
			userRepository.save(newData);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable ("userId") int userId) {
		userRepository.deleteById(userId);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
}
