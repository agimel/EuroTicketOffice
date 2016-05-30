package com.gft.euro2016.service;

import java.util.List;

import com.gft.euro2016.domain.BookedSeat;
import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.Reservation;
import com.gft.euro2016.domain.Seat;


public interface BookingService {

	List<Match> findUpcomingMatches();
	List<Seat>	findAvailableSeats(long match_id);
	List<Reservation> findReservations();
	Reservation findReservation(long id);
	
	Reservation saveReservation(long customer_id, long match_id,  List<Long> seat_id);
	
	void deleteReservation(long reservation_id);
	
	
}
