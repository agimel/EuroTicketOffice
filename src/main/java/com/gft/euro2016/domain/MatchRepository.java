package com.gft.euro2016.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long> {

	List<Match> findByDateAfter(Date date);
}
