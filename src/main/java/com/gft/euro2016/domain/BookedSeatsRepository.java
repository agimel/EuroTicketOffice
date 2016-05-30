package com.gft.euro2016.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BookedSeatsRepository extends CrudRepository<BookedSeat, Long> {

	List<BookedSeat> findByMatch(Match match);
}
