package com.gft.euro2016.domain;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Reservation{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(optional=false)
	@JoinColumn
	private Customer customer;
	
	@ManyToOne(optional=false)
	@JoinColumn
	private Match match;
	
	private double price;
	
	@CreationTimestamp
	private Date timestamp;
	

	protected Reservation(){
		
	}
	
	
	public Reservation(Customer customer, Match match, double price) {
		this.customer = customer;
		this.match = match;
		this.price = price;
	}



	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public void setId(long id) {
		this.id = id;
		
	}


	public long getId() {

		return id;
	}
	
	
}
