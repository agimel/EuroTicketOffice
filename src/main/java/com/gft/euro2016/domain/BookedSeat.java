package com.gft.euro2016.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BookedSeat {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(optional=false)
	@JoinColumn( updatable = true, insertable = true)
	private Match match;
	@ManyToOne(optional=false)
	@JoinColumn( updatable = true, insertable = true)
	private Seat seat;
	
	@ManyToOne(optional=false)
	@JoinColumn( updatable = true, insertable = true)
	private Reservation reservation;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public BookedSeat(Match match, Seat seat, Reservation reservation) {
		this.match = match;
		this.seat = seat;
		this.reservation = reservation;
	}
	
	
}
