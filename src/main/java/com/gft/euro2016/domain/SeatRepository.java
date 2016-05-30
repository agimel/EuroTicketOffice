package com.gft.euro2016.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SeatRepository extends CrudRepository<Seat, Long> {

	List<Seat> findByIdNotIn(List<Long> id);
}
