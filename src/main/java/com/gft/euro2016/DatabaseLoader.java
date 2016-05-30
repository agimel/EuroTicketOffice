package com.gft.euro2016;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.gft.euro2016.domain.BookedSeatsRepository;
import com.gft.euro2016.domain.BookedSeat;
import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.CustomerRepository;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.MatchRepository;
import com.gft.euro2016.domain.Reservation;
import com.gft.euro2016.domain.ReservationRepository;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.domain.SeatRepository;
import com.gft.euro2016.domain.Stadium;
import com.gft.euro2016.domain.StadiumRepository;

@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	MatchRepository mRepo;
	
	@Autowired
	StadiumRepository sRepo;
	
	@Autowired
	SeatRepository seatRepo;
	
	@Autowired
	BookedSeatsRepository bookedSeatsRepo;
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		Customer customer = new Customer("Anna", "Gimel", "anna.gimel@gft.com");
		customerRepo.save(customer);
		customerRepo.save(new Customer("Jack", "Smith", "jsmith@test.com"));
		
		log.info("Save customer");

		Stadium s =  new Stadium("Stade de France", "Saint-Denis");
		sRepo.save(s);
		s = new Stadium("Stade Velodrome", "Marseille");
		sRepo.save(s);
		
		s = new Stadium("Stade de Lyon", "Lyon");
		sRepo.save(s);
		
		s = new Stadium("Stade Pierre Mauroy", "Lille");
		sRepo.save(s);
		
		s = new Stadium("Parc des Princes", "Paris");
		sRepo.save(s);
		
		log.info("Save stadium");
		
		Calendar c = Calendar.getInstance();
		c.set(2016, 6, 10);
		Date d = c.getTime();
		c.set(2016, 6, 13);

		Match match = new Match(d, 100,s );
		mRepo.save(match);
		mRepo.save(new Match(c.getTime(), 200,s ));
	
		log.info("Save matches");
		
		List<Seat> seats = new ArrayList<Seat>();
		for(int i=1; i<11; i++){
			seats.add(new Seat(i, i*10, i*100.0));
		}
		
		seatRepo.save(seats);
		
		log.info("Save seats");
		
		Reservation reservation = new Reservation(customer, match, 100);
		reservationRepo.save(reservation);
		
		BookedSeat bookedSeat = new BookedSeat(match, seats.get(1), reservation);
		
		bookedSeatsRepo.save(bookedSeat);
	}

}
