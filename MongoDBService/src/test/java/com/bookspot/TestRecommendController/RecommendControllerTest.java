package com.bookspot.TestRecommendController;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.bookspot.controller.RecommendController;
import com.bookspot.model.Recommend;
import com.bookspot.repository.FavouriteRepository;
import com.bookspot.repository.RecommendRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecommendControllerTest {
	String token;
	@Autowired
    private MockMvc mockMvc;
    private Recommend recommend;
    @MockBean
    RecommendController recommendController;
  //  @InjectMocks
   private List<Recommend> listrecom = new ArrayList<Recommend>();
   @Autowired
	private RecommendRepository recommendrepo;
    @Before
	public void setUp() throws Exception {
		 token = Jwts.builder().setId("1").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "usersecretkey").compact();
		recommend = new Recommend(12, "ABCbook", "XYZauthor",
				"jikop999087623",121, "ABCZ", "image1","english"
				);
		listrecom.add(recommend);
		recommend = new Recommend(14, "ABCbook2", "XYZauthor2",
				"jiabc99087623",121, "ABCZ2", "image2","maths"
				);
		listrecom.add(recommend);
		recommend = new Recommend(16, "ABCbook3", "XYZauthor3",
				"jikop999wdftw7623",123, "ABCZ4", "image3","phy"
				);
		listrecom.add(recommend);
	}
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testGetAllRecom() throws Exception {
		when(recommendController.getRecommend()).
				thenReturn(listrecom);
		mockMvc.perform(get("/bookSpotAPI/getallrecommend"))
				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.name", is("Testing with Mockito")))
				.andDo(print());
		verify(recommendController,times(1)).getRecommend();
	}
	@Test
    public void testMockCreationrecommend(){
        assertNotNull(recommendrepo);
    }
	// Test Cases Recommendations
	@Test
	@Rollback(false)
	public void testCreateRecommend() {
		int count=(int)((CrudRepository<Recommend, Integer>)recommendrepo).count();
	    Recommend savedrecommend= recommendrepo.save(new Recommend(count+1,"Book1","BookAuthor1","Ayhiuji8907jnbh", 2,"Xyz1","img1","good book"));
	    assertEquals(count+1,(int)recommendrepo.count());
	}
	@Test
	@Rollback(false)
	public void testListRecommended() {
	    List<Recommend> Recommended = (List<Recommend>) recommendrepo.findAll();
	    assertThat(Recommended).size().isGreaterThan(0);
	}
}