package gma.intelie.challanges;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;

public class EventStoreGMA implements  EventStore{
	
	private List<Event> lstEvent;
	private Hashtable<String, List<Event>> htEvento;
	
	public EventStoreGMA() {
		htEvento = new Hashtable<String, List<Event>>();
			
		
		
	}

	@Override
	public void insert(Event event) {
		
		String eventType=event.type();
		if(htEvento.containsKey(eventType))
		{
			htEvento.get(eventType).add(event);
		}else
		{
			List<Event> lst=new ArrayList<Event>();
			lst.add(event);
			htEvento.put(eventType,lst);
		}
	
		
		
	}

	@Override
	public void removeAll(String type) {
		htEvento.remove(type);
		
	}

	@Override
	public EventIterator query(String type, long startTime, long endTime) {
		 
		List<Event> lstEvent=htEvento.get(type);
		
		
		return   new EventIteratorGMA(lstEvent.iterator());
	}

}
