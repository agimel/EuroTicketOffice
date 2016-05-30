package com.gft.euro2016.service;

import java.util.List;

import com.gft.euro2016.domain.BookedSeat;


public interface BookedSeatsService {

	List<BookedSeat> findBookedSeats(Long match_id);

	List<Long> findBookedSeatsIDs(Long match_id);

	BookedSeat bookSeat(BookedSeat seat);
	
	List<BookedSeat> bookSeatsList(List<BookedSeat> seats);
	
}