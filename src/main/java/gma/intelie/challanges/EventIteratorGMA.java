package gma.intelie.challanges;

import java.util.Iterator;
import java.util.List;

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

	}

	@Override
	public boolean moveNext() {

		if (eventIterator.hasNext()) {
			currentEvent = eventIterator.next();
			return eventIterator.hasNext();
		}
		else
		{
			currentEvent=null;
			return false;
		}

	

	}

	@Override
	public Event current() {

		if(currentEvent==null) {
			throw new IllegalStateException();
		}
		
		return currentEvent;
	}

	@Override
	public void remove() {
		
		if(( currentEvent==null) || (!eventIterator.hasNext())) {
			throw new IllegalStateException();
		}
		
		
		eventIterator.remove();

	}

}
