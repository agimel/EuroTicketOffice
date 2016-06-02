package com.gft.euro2016.service;

import java.util.List;

import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.Reservation;


public interface BookingService {

	List<Reservation> findReservations();
	Reservation findReservation(long id);
	Reservation prepareReservation(Match match);
	Reservation prepareReservation(Customer customer);
	Reservation saveReservation(List<Long> seatId);
	
}
