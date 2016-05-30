package com.gft.euro2016.test.controllers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import com.gft.euro2016.Euro2016TicketOfficeApplication;
import com.gft.euro2016.domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Euro2016TicketOfficeApplication.class)
@WebAppConfiguration
public class CustomerControllerTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

    }
	
	@Before
	public void setup(){
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void addCustomerTest() throws Exception{
		String customerJson = toJson(new Customer("Anna", "Gimel", "anna.gimel@gft.com"));
		
		this.mockMvc.perform(post("/api/customer/add" ).contentType(contentType).content(customerJson)).andExpect(status().isCreated());
	}
	
	@Test
	public void deleteCustomerTest() throws Exception{
	
		this.mockMvc.perform(delete("/api/customer/2").contentType(contentType)).andExpect(status().is2xxSuccessful()).andDo(MockMvcResultHandlers.print());
		this.mockMvc.perform(get("/api/customer/2").contentType(contentType)).andDo(MockMvcResultHandlers.print()).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void getCustomerTest() throws Exception{
		this.mockMvc.perform(get("/api/customer/1").contentType(contentType)).andExpect(status().is2xxSuccessful()).andExpect(content().string("{\"id\":1,\"firstName\":\"Anna\",\"lastName\":\"Gimel\",\"email\":\"anna.gimel@gft.com\"}"));
	}
	
    protected String toJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
