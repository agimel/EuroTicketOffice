package com.gft.euro2016.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.gft.euro2016.domain.BookedSeat;
import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.Reservation;
import com.gft.euro2016.domain.ReservationRepository;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.domain.SeatRepository;

@Service
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class BookingServiceImpl implements BookingService {

	private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
	
	private Reservation reservation;
	
	@Autowired
	private SeatRepository seatRepo;
	
	@Autowired
	public ReservationRepository reservationRepo;
	
	@Autowired
	private BookedSeatsService bookedSeatsService;
	

	public BookingServiceImpl(){
		reservation = new Reservation(null, null, 0);
	}
	
	@Override
	public Reservation saveReservation(List<Long> seatIds) {
		Match match = reservation.getMatch();
		List<Seat> seats = (List<Seat>) seatRepo.findAll(seatIds);
		double price = seats.stream().mapToDouble(e -> e.getPrice()).sum();
		Customer customer = reservation.getCustomer();
		
		Reservation reservation = reservationRepo.save(new Reservation(customer,match,price));
		
		List<BookedSeat> bookedSeats = new ArrayList<BookedSeat>();
		for(Long l : seatIds){
			bookedSeats.add(new BookedSeat(match, seatRepo.findOne(l), reservation));
		}
		
		bookedSeatsService.bookSeatsList(bookedSeats);
		
		return reservation;
	}

	public List<Reservation> findReservations(){
		List<Reservation> res = (List<Reservation>) reservationRepo.findAll();
		return res;
	}


	@Override
	public Reservation findReservation(long id) {
		
		return reservationRepo.findOne(id);
	}

	@Override
	public Reservation prepareReservation(Match match) {
		reservation.setMatch(match);
		return reservation;
	}

	@Override
	public Reservation prepareReservation(Customer customer) {
		reservation.setCustomer(customer);
		return reservation;
	}
}
