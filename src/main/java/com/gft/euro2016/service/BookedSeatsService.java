package com.gft.euro2016.service;

import java.util.List;

import com.gft.euro2016.domain.BookedSeat;


public interface BookedSeatsService {

	List<BookedSeat> findBookedSeats(Long matchId);

	List<Long> findBookedSeatsIDs(Long matchId);

	BookedSeat bookSeat(BookedSeat seat);
	
	List<BookedSeat> bookSeatsList(List<BookedSeat> seats);
	
}