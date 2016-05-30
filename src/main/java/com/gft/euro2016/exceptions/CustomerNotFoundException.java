package com.gft.euro2016.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Customer Not Found") //404
public class CustomerNotFoundException extends Exception {

	public CustomerNotFoundException(long id) {
		super("Customer not found with id: " + id);
		
	}

	
}
