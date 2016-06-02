package com.gft.euro2016.test.service;

import java.util.ArrayList;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.any;

import com.gft.euro2016.Euro2016TicketOfficeApplication;
import com.gft.euro2016.domain.BookedSeatsRepository;
import com.gft.euro2016.domain.BookedSeat;
import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.MatchRepository;
import com.gft.euro2016.domain.Reservation;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.service.BookedSeatsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Euro2016TicketOfficeApplication.class)
public class BookedSeatsServiceTest {

	
	@Autowired
	@InjectMocks
	private BookedSeatsService bookedSeatsService;
	
	@Mock
	private MatchRepository matchRepoMock;
	
	@Mock
	private BookedSeatsRepository bookedSeatsRepoMock;
	
	
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);

    }
    
	@Test
	public void findBookedSeatsTest(){
		List<BookedSeat> bs = new ArrayList<>();
		Match match = new Match(new Date(), 100, null);
		match.setId(1L);
		
		doReturn(match).when(matchRepoMock).findOne(anyLong());
		doReturn(bs).when(bookedSeatsRepoMock).findByMatch(any(Match.class));
		List<BookedSeat> result = bookedSeatsService.findBookedSeats(1L);
		assertEquals(bs,result);
	}
	
	@Test
	public void bookSeatTest(){
		Match m = new Match(new Date(), 100, null);
		m.setId(1L);
		Seat s = new Seat(1,10,100);
		s.setId(1L);
		Customer c = new Customer("A","AA", "aa@aa.com");
		c.setId(1L);
		Reservation r = new Reservation(c,m,100);
		r.setId(1L);
		BookedSeat seat = new BookedSeat(m,s,r);
		doReturn(seat).when(bookedSeatsRepoMock).save(seat);
		BookedSeat bs = bookedSeatsService.bookSeat(seat);
		assertNotNull(bs);
	}
	
	@Test
	public void bookSeatsListTest(){
		
		List<BookedSeat> seats = new ArrayList<>();
		doReturn(seats).when(bookedSeatsRepoMock).save(seats);
		List<BookedSeat > result = bookedSeatsService.bookSeatsList(seats);
		assertEquals(seats,result);
	}
	
	@Test
	public void findSeatsIds(){
		long match_id = 1;
		List<BookedSeat> bs = new ArrayList<>();
		Match match = new Match(new Date(), 100, null);
		match.setId(1L);
		List<Long> ids = new ArrayList<>();
		doReturn(match).when(matchRepoMock).findOne(anyLong());
		doReturn(bs).when(bookedSeatsRepoMock).findByMatch(any(Match.class));
		
		List<Long> result = bookedSeatsService.findBookedSeatsIDs(match_id);
		assertEquals(ids,result);
	}
}
