package gma.intelie.challanges;

 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

public class EventIteratorGMATest {

	private final String EVENT_TYPE_ADD="E_ADD";
	private EventIterator eventIterator;

	 

	@Test
	public void moveNext_iteratorHasReachedTheEnd_returnFalse() {
		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 1L));

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent, new Object());

		eventIterator.moveNext();

		assertTrue(!eventIterator.moveNext());

	}

	@Test
	public void moveNext_emptyList_returnFalse() {
		List<Event> lstEvent = new ArrayList<>();

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());

		assertFalse(eventIterator.moveNext());
	}

	@Test
	public void moveNext_notEnd_returnTrue() {
		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 1L));
		lstEvent.add(new Event(EVENT_TYPE_ADD, 2L));
		lstEvent.add(new Event(EVENT_TYPE_ADD, 3L));

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());

		assertTrue(eventIterator.moveNext());
		assertTrue(eventIterator.moveNext());
		 

	}

	@Test(expected = IllegalStateException.class)
	public void current_moveNextWasNeverCalled_returnIllegalStateException() {

		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 1L));

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());

		Event e = eventIterator.current();

	}

	@Test(expected = IllegalStateException.class)
	public void current_emptyList_returnIllegalStateException() {

		List<Event> lstEvent = new ArrayList<>();

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());

		Event e = eventIterator.current();

	}

	@Test(expected = IllegalStateException.class)
	public void current_moveNextLastResultWasFalse_returnIllegalStateException() {
		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196701L));

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());

		eventIterator.moveNext();
		eventIterator.current();

		eventIterator.moveNext();
		eventIterator.current();

	}

	@Test
	public void current_sucess_returnCurrentEvent() {

		boolean hasNext = false;

		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196701L));
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196702L));
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196703L));

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());

		hasNext = eventIterator.moveNext();
		assertTrue((eventIterator.current().timestamp() == 196701L) && (hasNext));

		hasNext = eventIterator.moveNext();
		assertTrue((eventIterator.current().timestamp() == 196702L) && (hasNext));

		hasNext = eventIterator.moveNext();
		assertTrue((eventIterator.current().timestamp() == 196703L) && (hasNext));

		hasNext = eventIterator.moveNext();
		assertTrue(!hasNext);

	}

	@Test(expected = IllegalStateException.class)
	public void remove_notCallNext_returnIllegalStateException() {
		List<Event> lstEvent = new ArrayList<>();

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());

		eventIterator.remove();
	}

	@Test(expected = IllegalStateException.class)
	public void remove_withEnd_returnIllegalStateException() {
		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196701L));

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());
		eventIterator.moveNext();
		eventIterator.moveNext();

		eventIterator.remove();
	}

	@Test
	public void remove_sucesso_() {
		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196701L));

		eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object());
		eventIterator.moveNext();
		eventIterator.remove();

		assertTrue(lstEvent.size() == 0);

	}

	@Test(expected = UnsupportedOperationException.class)
	public void close_sucesso() throws Exception {

		List<Event> lstEvent = new ArrayList<>();
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196701L));
		lstEvent.add(new Event(EVENT_TYPE_ADD, 196702L));
		lstEvent.add(new Event(EVENT_TYPE_ADD, 1967031L));

		try (EventIterator eventIterator = EventIteratorGMA.create(lstEvent.iterator(),lstEvent,new Object())) {

			eventIterator.moveNext();

		}

	}

	 

}
