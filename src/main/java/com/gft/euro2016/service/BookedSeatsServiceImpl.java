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
	public List<BookedSeat> findBookedSeats(Long matchId){
		Match match = matchRepo.findOne(matchId);
		List<BookedSeat> bookedSeats = new ArrayList<>();
		if(match != null){
			bookedSeats = bookedSeatsRepo.findByMatch(match);
		}

		return bookedSeats;
	}
	
	@Override
	public List<Long> findBookedSeatsIDs(Long matchId){

		List<Long> seatIds = new ArrayList<>();
		List<BookedSeat> seats = new ArrayList<>();
		seats = findBookedSeats(matchId);
		
		if(seats != null && !seats.isEmpty()){
			seatIds = seats.stream().map(e -> e.getSeat()).map(e -> e.getId()).collect(Collectors.toList());
		}
		return seatIds;
	}
	
	public BookedSeat bookSeat(BookedSeat seat){
		BookedSeat s = bookedSeatsRepo.save(seat);
		return s;
	}
	
	public List<BookedSeat> bookSeatsList(List<BookedSeat> seats){
		return (List<BookedSeat>) bookedSeatsRepo.save(seats);
	}
}
