package com.gft.euro2016.service;

import java.util.List;

import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.Seat;

public interface MatchService {

	List<Match> findUpcomingMatches();

	List<Seat> findAvailableSeats(long matchId);

}