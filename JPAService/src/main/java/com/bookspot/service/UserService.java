/**
 * 
 */
package com.bookspot.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.bookspot.exception.UserExistsException;
import com.bookspot.exception.*;
import com.bookspot.model.*;
import com.bookspot.repository.*;
@Service
public class UserService {
	private UserRepository userRepo;
	@Autowired
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	public boolean registerUser(User user) throws UserExistsException {
		Optional<User> optUser = userRepo.findByEmail(user.getEmail());
		if (optUser.isPresent()) {
			throw new UserExistsException();
		}
		String hashpw =
				BCrypt.hashpw(user.getPassword(),
						BCrypt.gensalt());
		System.out.println(hashpw);
		user.setPassword(hashpw);
		userRepo.save(user);
		return true;
	}
	public User login(String email, String password) {
		Optional<User> userSearch =
				userRepo.findByEmail(email);
		User user = null;
		if(userSearch.isPresent()) {
			user = userSearch.get();
			boolean matched = BCrypt.checkpw(password, user.getPassword());
			if(!matched) {
				user = null;
			}
		}
		return user;
	}
	
	
}