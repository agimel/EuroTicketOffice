package com.gft.euro2016.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.MatchRepository;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.domain.SeatRepository;

@Service
public class MatchServiceImpl implements MatchService {

	private static final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);
	
	@Autowired
	private MatchRepository matchRepo;
	@Autowired
	private SeatRepository seatRepo;
	
	@Autowired
	private BookedSeatsService bookedSeatsService;
	
	@Override
	public List<Match> findUpcomingMatches() {
		
		List<Match> matches = matchRepo.findByDateAfter(new Date());
		for(Match m : matches){
			log.info(m.toString());
		}
		return matches;
	}
	
	@Override
	public List<Seat> findAvailableSeats(long matchId) {
		List<Seat> seats = null;

		List<Long> ids = bookedSeatsService.findBookedSeatsIDs(matchId);
		log.info("ids size: " + ids.size());
		if(ids != null  && ids.isEmpty()){
			seats = (List<Seat>) seatRepo.findAll();
		}
		else{
			seats = seatRepo.findByIdNotIn(ids);
		}
		
		return seats;
	}
	
}
