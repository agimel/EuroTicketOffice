package com.gft.euro2016.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.Reservation;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.exceptions.ReservationNotFoundException;
import com.gft.euro2016.service.BookingService;
import com.gft.euro2016.service.CustomerService;

@RestController
@RequestMapping("/api")
public class BookingController {
	
	private static final Logger log = LoggerFactory.getLogger(BookingController.class);
	
	@Autowired
	private Reservation reservation;
	
	@Autowired
	private BookingService bookingService;	
	@Autowired
	private CustomerService customerService;
	

	@RequestMapping("/match/available")
	public List<Match> availableMatches() {
		return bookingService.findUpcomingMatches();
	}
	
	@RequestMapping(value="/match/choose", method=RequestMethod.POST)
	public ResponseEntity<?> chooseMatch(@RequestBody Match match ){
		log.info("Chosen match: " + match.getId());
		log.info(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api/match/{match_id}/seats").port(8080).buildAndExpand(match.getId()).toUri());
		
		reservation = new Reservation(null, match, 0);
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/match/{match_id}/seats", method = RequestMethod.GET)
	public List<Seat> getAvailableSeats(@PathVariable("match_id") long match_id){
		List<Seat> seats =  bookingService.findAvailableSeats(match_id);
		
		return seats;
	}
	
	@RequestMapping(value="/match/{match_id}/seats", method = RequestMethod.POST)
	public ResponseEntity<?> chooseSeat(@PathVariable long match_id, @RequestBody Seat seat){
		long seat_id = seat.getId();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().port(8080).replacePath("/api/match/{id}/book").query("seat={seat_id}").buildAndExpand(match_id, seat_id).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
	}
	
	
	@RequestMapping("/match/{match_id}/book")
	public ResponseEntity<?> bookMatch(@PathVariable long match_id,@RequestParam("seat") long seat_id, @RequestParam("cid")long customer_id) throws IOException{
		Customer customer = customerService.findCustomerById(customer_id);
		List<Long> seats = new ArrayList<Long>();
		seats.add(seat_id);
		HttpHeaders httpHeaders = new HttpHeaders();
		if(reservation != null){
		reservation.setCustomer(customer);
		}
		else{
			return new ResponseEntity<>(null, httpHeaders, HttpStatus.NOT_FOUND);
		}
		bookingService.saveReservation(reservation.getCustomer().getId(), reservation.getMatch().getId(), seats);
		
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	

	@RequestMapping("/bookings")
	public List<Reservation> getReservations() throws ReservationNotFoundException{
		List<Reservation> res = bookingService.findReservations();
		if(res == null){
			throw new ReservationNotFoundException();
		}
		return res;
	}
	
}
