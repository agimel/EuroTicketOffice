package com.gft.euro2016.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Reservation Not Found") //404
public class ReservationNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReservationNotFoundException() {
		super("Reservations not found");
		
	}

	
}
