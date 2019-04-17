package gma.intelie.challanges;

import java.util.Iterator;
import java.util.List;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

public class EventIteratorGMA implements EventIterator {

	private Iterator<Event> itEvento;
	private List<Event> lstEvent;
	private Event currentEvent;
	private Object lock;

	private EventIteratorGMA(Iterator<Event> itEvento, List<Event> lstEvent, Object lock) {

		this.itEvento = itEvento;
		this.lstEvent = lstEvent;
		this.lock = lock;
	}

	public static EventIterator create(Iterator<Event> itEvento, List<Event> lstEvent, Object lock) {

		return new EventIteratorGMA(itEvento, lstEvent, lock);
	}

	@Override
	public void close() throws Exception {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean moveNext() {

		synchronized (lock) {
			if (itEvento.hasNext()) {
				currentEvent = itEvento.next();
				return true;
			} else {
				currentEvent = null;
				return false;
			}
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

		if (currentEvent == null) {
			throw new IllegalStateException();
		}
		synchronized (lock) {
			// itEvento.remove();
			lstEvent.remove(currentEvent);
		}
	}

}
