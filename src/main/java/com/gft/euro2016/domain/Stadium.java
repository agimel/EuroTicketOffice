package com.gft.euro2016.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Stadium {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String location;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER)
	private Collection<Match> matches;
	
	public Collection<Match> getMatches() {
		return matches;
	}

	public void setMatches(Collection<Match> matches) {
		this.matches = matches;
	}

	protected Stadium(){}

	public Stadium(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Stadium [name=" + name + ", location=" + location + ", matches=" + matches + "]";
	}
	
	
}
