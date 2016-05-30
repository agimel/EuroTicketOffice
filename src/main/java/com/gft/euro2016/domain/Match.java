package com.gft.euro2016.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Match {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Date date;
	private int availableTickets;
	
	@ManyToOne(optional=false)
	@JoinColumn
	private Stadium stadium;
	
	protected Match(){}

	public Match(long id,Date date, int availableTickets, Stadium stadium) {
		this.id = id;
		this.date = date;
		this.availableTickets = availableTickets;
		this.stadium = stadium;
	}

	public Match(Date date, int availableTickets, Stadium stadium) {
		
		this.date = date;
		this.availableTickets = availableTickets;
		this.stadium = stadium;
	}
	
	@Override
	public String toString() {
		return "Match [id=" + id + ", date=" + date + ", availableTickets=" + availableTickets + ", stadium=" + stadium + "]";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(int availableTickets) {
		this.availableTickets = availableTickets;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
