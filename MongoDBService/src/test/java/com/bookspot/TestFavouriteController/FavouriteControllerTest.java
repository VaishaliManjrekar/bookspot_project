package com.bookspot.TestFavouriteController;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bookspot.model.Favourite;
import com.bookspot.repository.FavouriteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc 


public class FavouriteControllerTest {
	@Autowired
	private FavouriteRepository favouriterepo;
	
	@Test
	@Rollback(false)
	public void testaddFavourite() {
		int count=(int)favouriterepo.count();
	    Favourite savedFavourite= favouriterepo.save(new Favourite( 1,"ABCbook","XYZauthor","yjnuijkloo908",12, "ABCxy","Img1","nicebook"));
	    
	    equals(favouriterepo.count());
	}
	
	@Test
	@Rollback(false)
	public void testListFavourited() {
	    List<Favourite> Favourited = (List<Favourite>) favouriterepo.findAll();
	    assertThat(Favourited).size().isGreaterThan(0);
	}

}
