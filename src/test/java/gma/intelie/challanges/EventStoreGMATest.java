package gma.intelie.challanges;

 
import static org.junit.Assert.assertTrue;

 
import org.junit.Test;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

public class EventStoreGMATest {
	
	 
	
	 
	@Test
	public void insert() {
		
		EventStoreGMA eventStoreGMA=new EventStoreGMA();
		
		eventStoreGMA.insert(new Event("EV_ADD",100L));
		eventStoreGMA.insert(new Event("EV_ADD",110L));
		eventStoreGMA.insert(new Event("EV_ADD",120L));
		eventStoreGMA.insert(new Event("EV_ADD",130L));
		
		EventIterator ei=eventStoreGMA.query("EV_ADD", 100L, 121L);
		
		int tt=0;
		while(ei.moveNext()) {
			tt++;
		}
		 
		assertTrue(tt==3);
		
	
	}
}
