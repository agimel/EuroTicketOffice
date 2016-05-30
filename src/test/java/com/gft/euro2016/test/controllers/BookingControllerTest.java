package com.gft.euro2016.test.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import com.gft.euro2016.Euro2016TicketOfficeApplication;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.Stadium;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Euro2016TicketOfficeApplication.class)
@WebAppConfiguration
public class BookingControllerTest {

	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private MockHttpSession session;
   

	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

    }
	
	@Before
	public void setup(){
		this.mockMvc = webAppContextSetup(webApplicationContext).build();	
		MockitoAnnotations.initMocks(this);
        session.clearAttributes();
        session.invalidate();
	}
	
    protected String toJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
    
  
    @Test
    public void findAvailableMatchesTest() throws Exception{
    	this.mockMvc.perform(get("/api/match/available" ).contentType(contentType)).andExpect(status().is2xxSuccessful()).andDo(MockMvcResultHandlers.print());
    }
    
    
    @Test
    public void chooseSeatTest() throws Exception{

    	String seatJson = "{\"id\":1,\"row_nr\":1,\"col_nr\":10,\"price\":100.0}";
    	this.mockMvc.perform(post("/api/match/2/seats" ).contentType(contentType).content(seatJson)).andExpect(redirectedUrl("http://localhost:8080/api/match/2/book?seat=1")).andDo(MockMvcResultHandlers.print());
    }
  
    @Test
    public void chooseMatchTest() throws Exception{
    	Stadium s =  new Stadium("Parc des Princes", "Paris");
    	Calendar c = Calendar.getInstance();
		c.set(2016, 6, 10);
		Date d = c.getTime();
		
		Match m = new Match(1, d, 100,s );
		
    	String matchJson = toJson(m);
    	this.mockMvc.perform(post("/api/match/choose" ).contentType(contentType).content(matchJson)).andExpect(status().is2xxSuccessful()).andExpect(header().string("location", "http://localhost:8080/api/match/1/seats")).andDo(MockMvcResultHandlers.print());
    }
   
    @Test
    public void getAvailableSeatsTest()throws Exception{
    	this.mockMvc.perform(get("/api/match/2/seats" ).contentType(contentType)).andExpect(status().is2xxSuccessful()).andDo(MockMvcResultHandlers.print());
    }
    

    @Test
    public void bookMatchTest() throws Exception{

    	Stadium s =  new Stadium("Parc des Princes", "Paris");
    	Calendar c = Calendar.getInstance();
		c.set(2016, 6, 10);
		Date d = c.getTime();
		
		Match m = new Match(1, d, 100,s );
		
    	String matchJson = toJson(m);

    	this.mockMvc.perform(post("/api/match/choose" ).contentType(contentType).content(matchJson).session(session)).andExpect(status().is2xxSuccessful());
    	this.mockMvc.perform(post("/api/match/1/book?seat=1&cid=1").contentType(contentType).session(session)).andExpect(status().is2xxSuccessful());

    }
    
    @Test
    public void getReservationsTest() throws Exception{
    	this.mockMvc.perform(get("/api/bookings").contentType(contentType)).andExpect(status().is2xxSuccessful()).andExpect(content().string(containsString("[{\"id\":1,\"customer\":{\"id\":1,\"firstName\":\"Anna\",\"lastName\":\"Gimel\",\"email\":\"anna.gimel@gft.com\"")));
   }


}
