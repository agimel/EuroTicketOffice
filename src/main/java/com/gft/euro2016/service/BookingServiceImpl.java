package com.gft.euro2016.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.euro2016.domain.BookedSeat;
import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.MatchRepository;
import com.gft.euro2016.domain.Reservation;
import com.gft.euro2016.domain.ReservationRepository;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.domain.SeatRepository;

@Service
public class BookingServiceImpl implements BookingService {

	private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
	
	@Autowired
	private MatchRepository matchRepo;

	@Autowired
	private SeatRepository seatRepo;
	
	@Autowired
	private ReservationRepository reservationRepo;
	
	@Autowired
	private BookedSeatsService bookedSeatsService;
	
	@Autowired
	private CustomerService customerService; 

	
	@Override
	public List<Match> findUpcomingMatches() {
		
		List<Match> matches = matchRepo.findByDateAfter(new Date());
		for(Match m : matches){
			log.info(m.toString());
		}
		return matches;
	}

	@Override
	public List<Seat> findAvailableSeats(long match_id) {
		List<Seat> seats = null;

		List<Long> ids = bookedSeatsService.findBookedSeatsIDs(match_id);
		log.info("ids size: " + ids.size());
		if(ids != null  && ids.isEmpty()){
			seats = (List<Seat>) seatRepo.findAll();
		}
		else{
			seats = seatRepo.findByIdNotIn(ids);
		}
		
		return seats;
	}

	@Override
	public Reservation saveReservation(long customer_id, long match_id, List<Long> seat_ids) {
		Match match = matchRepo.findOne(match_id);
		List<Seat> seats = (List<Seat>) seatRepo.findAll(seat_ids);
		double price = seats.stream().mapToDouble(e -> e.getPrice()).sum();
		Customer customer = customerService.findCustomerById(customer_id);

		
		Reservation reservation = reservationRepo.save(new Reservation(customer, match, price));
		
		List<BookedSeat> bookedSeats = new ArrayList<BookedSeat>();
		for(Long l : seat_ids){
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
	public void deleteReservation(long reservation_id) {
		reservationRepo.delete(reservation_id);
		
	}

	@Override
	public Reservation findReservation(long id) {
		
		return reservationRepo.findOne(id);
	}
}
