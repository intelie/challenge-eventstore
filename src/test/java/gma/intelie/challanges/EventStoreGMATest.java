package gma.intelie.challanges;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

public class EventStoreGMATest {

	// @Test
	public void insert() {

		EventStoreGMA eventStoreGMA = new EventStoreGMA();

		eventStoreGMA.insert(new Event("EV_ADD", 115L));
		eventStoreGMA.insert(new Event("EV_ADD", 100L));
		eventStoreGMA.insert(new Event("EV_ADD", 110L));
		eventStoreGMA.insert(new Event("EV_ADD", 110L));
		eventStoreGMA.insert(new Event("EV_ADD", 120L));
		eventStoreGMA.insert(new Event("EV_ADD", 130L));
		eventStoreGMA.insert(new Event("EV_DIV", 111L));

		EventIterator ei = eventStoreGMA.query("EV_ADD", 100L, 121L);

		assertTrue(getTotItem(ei) == 4);

	}

	@Test
	public void removeAll() {
		EventStoreGMA eventStoreGMA = new EventStoreGMA();
		eventStoreGMA.insert(new Event("EV_ADD", 115L));
		eventStoreGMA.insert(new Event("EV_ADD", 100L));
		eventStoreGMA.insert(new Event("EV_ADD", 110L));
		eventStoreGMA.insert(new Event("EV_ADD", 120L));

		eventStoreGMA.insert(new Event("EV_DIV", 115L));
		eventStoreGMA.insert(new Event("EV_DIV", 100L));
		eventStoreGMA.insert(new Event("EV_DIV", 110L));

		EventIterator ei1 = eventStoreGMA.query("EV_ADD", 100L, 121L);
		EventIterator ei2 = eventStoreGMA.query("EV_DIV", 100L, 111L);

		eventStoreGMA.removeAll("EV_ADD");
		
		ei1 = eventStoreGMA.query("EV_ADD", 100L, 121L);
		ei2 = eventStoreGMA.query("EV_DIV", 100L, 111L);
		
		  assertTrue(getTotItem(ei1)==0 && getTotItem(ei2)==2);
		 
	}
	
	@Test
	public void query() {
		EventStoreGMA eventStoreGMA = new EventStoreGMA();
		eventStoreGMA.insert(new Event("EV_ADD", 115L));
		eventStoreGMA.insert(new Event("XX_ADD", 120L));
		eventStoreGMA.insert(new Event("EV_ADD", 100L));
		eventStoreGMA.insert(new Event("EV_ADD", 110L));
		eventStoreGMA.insert(new Event("EV_ADD", 120L));
		
		EventIterator ei1 = eventStoreGMA.query("EV_ADD", 110L, 121L);
		
		ei1.moveNext(); Event e1= ei1.current();
		ei1.moveNext(); Event e2= ei1.current();
		ei1.moveNext(); Event e3= ei1.current();
		
		System.out.println("--->"+e1.timestamp());
		System.out.println("--->"+e2.timestamp());
		System.out.println("--->"+e3.timestamp());
		
		assertTrue(e1.timestamp()==110L && e2.timestamp()==115L && e3.timestamp()==120L);
		assertTrue(e1.type().equals("EV_ADD") && e2.type().equals("EV_ADD") && e3.type().equals("EV_ADD"));
		
		 
		
	}

	private int getTotItem(EventIterator eventIte) {

		int tt = 0;
		while (eventIte.moveNext()) {
			// System.out.println("--->"+eventIte.current().type()+" - "+
			// eventIte.current().timestamp());
			tt++;
		}

		return tt;
	}
}
