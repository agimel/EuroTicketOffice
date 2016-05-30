package com.gft.euro2016;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Euro2016TicketOfficeApplication {

	private static final Logger log = LoggerFactory.getLogger(Euro2016TicketOfficeApplication.class);
	
	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Euro2016TicketOfficeApplication.class, args);
		
		

	}
	

}
