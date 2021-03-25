package com.bookspot.test.controller;



import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bookspot.controller.UserController;
import com.bookspot.exception.UserExistsException;
import com.bookspot.model.User;
import com.bookspot.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc




public class UserControllerTest {
	 @Autowired
	    private MockMvc mockMvc;
	    private User user;
	    @MockBean
	    UserService userService;
	    @InjectMocks
	    UserController userController;
	    
	    
	    @Before
	    public void setUp() throws Exception {
	        MockitoAnnotations.initMocks(this);
	      //  (int id, String firstName, String lastName, String email, String password, String role
	       user = new User(1, "Priyashi", "Dogra", "userp3125@gmail.com", "userp@123","user");
	    }
	    
	    //two methods for test cases failure and success of login
	    @Test
	    public void testLoginSuccess() throws Exception {
	        when(userService.login(user.getEmail(),user.getPassword())).thenReturn(user);
	        mockMvc.perform(post("/bookSpotAPI/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
	                .andExpect(status().isOk()).andDo(print());
	    }
	    
	    public static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    @Test
	    public void testLoginFailure() throws Exception {
	        when(userService.login(user.getEmail(),user.getPassword())).thenReturn(null);
	        mockMvc.perform(post("/bookSpotAPI/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
	                .andExpect(status().isUnauthorized()).andDo(print());
	    }
	    
	    //two methods for register
	    @Test
		public void testRegisterUserSuccess() throws Exception {
			
			when(userService.registerUser(Mockito.any(User.class))).thenReturn(true);
			String bookJson = new ObjectMapper().writeValueAsString(user);
			mockMvc.perform(post("/bookSpotAPI/addUser").contentType(MediaType.APPLICATION_JSON)
					.content(bookJson))
			.andExpect(status().isOk());
		}
	    
	    @Test
	    public void testRegisterUserFailure() throws UserExistsException, Exception {
	        when(userService.registerUser(Mockito.any(User.class))).thenThrow(UserExistsException.class);
	        mockMvc.perform(post("/bookSpotAPI/addUser").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
	                .andExpect(status().isConflict()).andDo(print());
	    }
	    
	    
}
