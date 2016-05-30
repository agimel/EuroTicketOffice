package com.gft.euro2016.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.euro2016.domain.BookedSeatsRepository;
import com.gft.euro2016.domain.BookedSeat;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.MatchRepository;

@Service
public class BookedSeatsServiceImpl implements BookedSeatsService {

	@Autowired
	private MatchRepository matchRepo;
	
	@Autowired
	private BookedSeatsRepository bookedSeatsRepo;
	

	@Override
	public List<BookedSeat> findBookedSeats(Long match_id){
		Match match = matchRepo.findOne(match_id);
		List<BookedSeat> booked_seats = new ArrayList<>();
		if(match != null){
			booked_seats = bookedSeatsRepo.findByMatch(match);
		}

		return booked_seats;
	}
	
	@Override
	public List<Long> findBookedSeatsIDs(Long match_id){

		List<Long> seat_ids = new ArrayList<>();
		List<BookedSeat> seats = new ArrayList<>();
		seats = findBookedSeats(match_id);
		
		if(seats != null && !seats.isEmpty()){
			seat_ids = seats.stream().map(e -> e.getSeat()).map(e -> e.getId()).collect(Collectors.toList());
		}
		return seat_ids;
	}
	
	public BookedSeat bookSeat(BookedSeat seat){
		BookedSeat s = bookedSeatsRepo.save(seat);
		return s;
	}
	
	public List<BookedSeat> bookSeatsList(List<BookedSeat> seats){
		return (List<BookedSeat>) bookedSeatsRepo.save(seats);
	}
}
