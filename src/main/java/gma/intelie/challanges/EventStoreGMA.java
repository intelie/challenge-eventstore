package gma.intelie.challanges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;

public class EventStoreGMA implements EventStore {

	private Hashtable<String, List<Event>> htEvento;
	private Object lock;
	
	
	private EventStoreGMA() {
		lock = new Object();
		htEvento = new Hashtable<String, List<Event>>();

	}
	

	public static EventStore create() {
	
		return new EventStoreGMA();
	}
	
	

	@Override
	public  void insert(Event event) {

		
		
		String eventType = event.type();
		if (htEvento.containsKey(eventType)) {
			if (!htEvento.get(eventType).contains(event)) {
				htEvento.get(eventType).add(event);
			}
			// TODO verificar duplicidade chave

		} else {
			// TODO redundante
			// List<Event> lst= Collections.synchronizedList(new ArrayList<Event>());
			List<Event> lst = new ArrayList<Event>();
			lst.add(event);
			htEvento.put(eventType, lst);
		}

	}

	@Override
	public  void removeAll(String type) {

		htEvento.remove(type);

	}

	@Override
	public  EventIterator query(String type, long startTime, long endTime) {
		
		 
			if (!htEvento.containsKey(type)) {
				return EventIteratorGMA.create(Collections.emptyIterator(),null);
			}

			List<Event> lstEvent = htEvento.get(type);
			
			Iterator<Event> itEvento = lstEvent
					.stream()
					.filter(e -> {	return (e.timestamp() >= startTime && (e.timestamp() < endTime));
			})	// .distinct()
					.sorted((p1, p2) -> (p1.timestamp() <= p2.timestamp()) ? -1 : 1)
					//.collect(Collectors.toList()) // snapshort - cursor imutavel  
					.iterator();
					
				 

			
			
		return EventIteratorGMA.create(itEvento,lstEvent );
	}

}
