package com.gft.euro2016.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seat {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int rowNr;
	private int colNr;
	private double price;
	
	
	protected Seat(){}

	public Seat(int rowNr, int colNr, double price) {
		this.rowNr = rowNr;
		this.colNr = colNr;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRowNr() {
		return rowNr;
	}

	public void setRowNr(int rowNr) {
		this.rowNr = rowNr;
	}

	public int getColNr() {
		return colNr;
	}

	public void setColNr(int colNr) {
		this.colNr = colNr;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
