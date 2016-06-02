package com.gft.euro2016.test.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;

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

import com.gft.euro2016.Euro2016TicketOfficeApplication;
import com.gft.euro2016.domain.Match;
import com.gft.euro2016.domain.MatchRepository;
import com.gft.euro2016.domain.Seat;
import com.gft.euro2016.service.BookedSeatsServiceImpl;
import com.gft.euro2016.service.MatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Euro2016TicketOfficeApplication.class)
public class MatchServiceTest {

	@Autowired
	@InjectMocks
	private MatchService matchService;
	@Mock
	private MatchRepository repositoryMock;
	@Mock
	private BookedSeatsServiceImpl bookedSeatsMock;

    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);

    }
	
	@Test
	public void findUpcomingMatchesTest(){
		List<Match> models = new ArrayList<Match>();

		doReturn(models).when(repositoryMock).findByDateAfter(new Date());
		
		List<Match> actual = matchService.findUpcomingMatches();
		
		assertThat(actual, is(models));
	}
	
	@Test
	public void findAvailableSeats_NoBookings(){
		long match_id = 1;
		List<Long> seats = new ArrayList<>();

		doReturn(seats).when(bookedSeatsMock).findBookedSeatsIDs(anyLong());

		List<Seat> available = matchService.findAvailableSeats(match_id);
		assertNotNull(available);
		assertEquals(10, available.size());
	}
	
	@Test
	public void findAvailableSeats_WithBookings(){
		long match_id = 1;
		List<Long> seats = new ArrayList<>();
		seats.add(1L);
				
		doReturn(seats).when(bookedSeatsMock).findBookedSeatsIDs(anyLong());
		
		List<Seat> available = matchService.findAvailableSeats(match_id);
		assertNotNull(available);
		assertEquals(9, available.size());
	}
}
