package gma.intelie.challanges;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;

public class EventStoreGMATest {

	private final String EVENT_TYPE_ADD="E_ADD";
	private final String EVENT_TYPE_DIV="E_DIV";
	
	@Test
	public void insert() {

		EventStore eventStore = EventStoreGMA.create();

		eventStore.insert(new Event(EVENT_TYPE_ADD, 115L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 100L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 110L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 110L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 120L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 130L));
		eventStore.insert(new Event(EVENT_TYPE_DIV, 111L));

		EventIterator ei = eventStore.query(EVENT_TYPE_ADD, 100L, 121L);

		assertTrue(EventIteratorUtil.getTotItem(ei) == 4);
	}
	
	@Test
	public void insert_3Event2duplicate_returnAdd2Event() {

		EventStore eventStore = EventStoreGMA.create();
		 
		eventStore.insert(new Event(EVENT_TYPE_ADD, 100L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 100L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 101L));

		EventIterator ei = eventStore.query(EVENT_TYPE_ADD, 100L, 121L);

		assertTrue(EventIteratorUtil.getTotItem(ei) == 2);
	}

	

	@Test
	public void removeAll() {
		EventStore eventStore = EventStoreGMA.create();
		eventStore.insert(new Event(EVENT_TYPE_ADD, 115L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 100L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 110L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 120L));

		eventStore.insert(new Event(EVENT_TYPE_DIV, 115L));
		eventStore.insert(new Event(EVENT_TYPE_DIV, 100L));
		eventStore.insert(new Event(EVENT_TYPE_DIV, 110L));

		EventIterator ei1 = eventStore.query(EVENT_TYPE_ADD, 100L, 121L);
		EventIterator ei2 = eventStore.query(EVENT_TYPE_DIV, 100L, 111L);

		eventStore.removeAll(EVENT_TYPE_ADD);
		
		ei1 = eventStore.query(EVENT_TYPE_ADD, 100L, 121L);
		ei2 = eventStore.query(EVENT_TYPE_DIV, 100L, 111L);
		
		  assertTrue(EventIteratorUtil.getTotItem(ei1)==0 && EventIteratorUtil.getTotItem(ei2)==2);
		 
	}
	
	@Test
	public void queryAndRemove() {
		EventStore eventStore = EventStoreGMA.create();
		eventStore.insert(new Event(EVENT_TYPE_ADD, 100L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 110L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 120L));
		
		EventIterator evIt1=eventStore.query(EVENT_TYPE_ADD,10L, 400L);
	
		while(evIt1.moveNext()) {
			evIt1.remove();
		}
		
		evIt1=eventStore.query(EVENT_TYPE_ADD,10L, 400L);
		
		 assertTrue(EventIteratorUtil.getTotItem(evIt1)==0);
	}
	
	@Test
	public void query() {
		EventStore eventStore = EventStoreGMA.create();
		eventStore.insert(new Event(EVENT_TYPE_ADD, 115L));
		eventStore.insert(new Event(EVENT_TYPE_DIV, 120L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 100L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 110L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 120L));
		
		EventIterator ei1 = eventStore.query(EVENT_TYPE_ADD, 110L, 121L);
		
		ei1.moveNext(); Event e1= ei1.current();
		ei1.moveNext(); Event e2= ei1.current();
		ei1.moveNext(); Event e3= ei1.current();
		
	
		assertTrue(e1.timestamp()==110L && e2.timestamp()==115L && e3.timestamp()==120L);
		assertTrue(e1.type().equals(EVENT_TYPE_ADD) && 
				  e2.type().equals(EVENT_TYPE_ADD) && 
				  e3.type().equals(EVENT_TYPE_ADD));
		
		 
		
	}
	
	 
}
