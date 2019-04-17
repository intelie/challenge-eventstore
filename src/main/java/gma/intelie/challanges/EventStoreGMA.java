package gma.intelie.challanges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

 

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;


public class EventStoreGMA implements  EventStore{
	 
	private Hashtable<String, List<Event>> htEvento;
	
	
	public EventStoreGMA() {
		htEvento = new Hashtable<String, List<Event>>();
		
	}

	@Override
	public synchronized void insert(Event event) {
		
		String eventType=event.type();
		if(htEvento.containsKey(eventType))
		{
			if(!htEvento.get(eventType).contains(event)) {
				htEvento.get(eventType).add(event);	
			}
			//TODO verificar duplicidade chave
				
		}else
		{
			 
			List<Event> lst=new ArrayList<Event>();
			lst.add(event);
			htEvento.put(eventType,lst);
		}
	
		
		
	}

	@Override
	public synchronized void removeAll(String type) {
		htEvento.remove(type);
		
	}

	@Override
	public EventIterator query(String type, long startTime, long endTime) {
		 
		if (!htEvento.containsKey(type)) {
			return  new EventIteratorGMA(Collections.emptyIterator());
		}
		
		List<Event> lstEvent=htEvento.get(type);
	
		
		Iterator<Event> it= lstEvent
				.stream()
				.filter(e->{ return (e.timestamp()>=startTime && (e.timestamp()<endTime));})
				//.distinct()
			 	.sorted((p1, p2)-> (p1.timestamp()<=p2.timestamp())? -1:1)
				.collect(Collectors.toList())
				.iterator();
				 
		
		return   new EventIteratorGMA(it);
	}

}
