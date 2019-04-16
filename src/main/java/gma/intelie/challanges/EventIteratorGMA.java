package gma.intelie.challanges;

 
import java.util.Iterator;
 

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

public class EventIteratorGMA implements EventIterator {

	private Iterator<Event> eventIterator;
	private Event currentEvent;

	public EventIteratorGMA(Iterator<Event> lstEvent) {
		this.eventIterator = lstEvent;
	}

	@Override
	public void close() throws Exception {
	  throw new UnsupportedOperationException();
		 
	}

	@Override
	public boolean moveNext() {

		if (eventIterator.hasNext()) {
			currentEvent = eventIterator.next();
			return true;
		} else {
			currentEvent = null;
			return false;
		}

	}

	@Override
	public Event current() {

		if (currentEvent == null) {
			throw new IllegalStateException();
		}

		return currentEvent;
	}

	@Override
	public void remove() {
		// TODO ajustar
		synchronized (eventIterator) {

			if (currentEvent == null) {
				throw new IllegalStateException();
			}

			eventIterator.remove();

		}
	}

}
