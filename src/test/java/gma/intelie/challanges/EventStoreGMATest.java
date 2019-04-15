package gma.intelie.challanges;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import net.intelie.challenges.Event;

public class EventStoreGMATest {
	
	private EventStoreGMA eventStoreGMA;
	
	@Before
	void setup() {
 
		eventStoreGMA= new EventStoreGMA();
	}
	

	public void shouldAddNewEvent() {
		
		Event event= new Event("EV_ADD",100L);
		
		eventStoreGMA.insert(event);
		
	    assertEquals("some_type", event.type());
	}
}
