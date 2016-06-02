package com.gft.euro2016.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.service.MatchService;

@RestController
@RequestMapping("/api/match")
public class MatchController {
	
	@Autowired
	private MatchService matchService;	

	
	@RequestMapping("/available")
	public List<Match> availableMatches() {
		return matchService.findUpcomingMatches();
	}
	

	@RequestMapping(value="/{match_id}/seats", method = RequestMethod.GET)
	public List<Seat> getAvailableSeats(@PathVariable("match_id") long match_id){
		List<Seat> seats =  matchService.findAvailableSeats(match_id);
		
		return seats;
	}
	
	
	
	

	

}
