package com.gft.euro2016.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gft.euro2016.Euro2016TicketOfficeApplication;
import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.MatchRepository;
import com.gft.euro2016.domain.Reservation;
import com.gft.euro2016.domain.ReservationRepository;
import com.gft.euro2016.service.BookedSeatsService;
import com.gft.euro2016.service.BookingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Euro2016TicketOfficeApplication.class)
@WebAppConfiguration
public class BookingServiceTest {

	
	@Autowired
	@InjectMocks
	private BookingService bookingService;
	@Mock
	private MatchRepository repositoryMock;
	@Mock
	private BookedSeatsService bookedSeatsMock;
	
	@Mock
	private ReservationRepository reservationRepo;
	
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	Customer customer = new Customer("JJ", "JJ", "jj@test.com");
		customer.setId(2);
    	bookingService.prepareReservation(customer);
    	Match match = new Match(new Date(), 100, null);
		match.setId(1L);
		bookingService.prepareReservation(match);
    }

	
	@Test
	public void saveReservationTest(){

		List<Long> seat_ids = Arrays.asList(1L,2L,3L,4L);
		Match match = new Match(new Date(), 100, null);
		match.setId(1L);
		Customer customer = new Customer("JJ", "JJ", "jj@test.com");
		customer.setId(3);
		Reservation reserv = new Reservation(customer, match, 100.00);
		
		doReturn(reserv).when(reservationRepo).save(any(Reservation.class));
		
		Reservation reservation = bookingService.saveReservation(seat_ids);
		
		assertNotNull(reservation);
	}
	

}
