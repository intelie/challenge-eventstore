package gma.intelie.challanges;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.intelie.challenges.Event;

public class EventIteratorGMATest {
	
	private EventIteratorGMA eventIteratorGMA;
	
	//@Before
	void setup() {
 
	
	}
	
	
	@Test
	public void  moveNext_semMaisItens() {
		List<Event> lstEvent=new ArrayList<>(); 
		lstEvent.add( new Event("EVE-ADD",1L));
		
	   
		eventIteratorGMA= new EventIteratorGMA(lstEvent.iterator());
				
		assertTrue(!eventIteratorGMA.moveNext());
		
		
	}
	
	@Test
	public void  moveNext_comMaisItens() {
		List<Event> lstEvent=new ArrayList<>(); 
		lstEvent.add( new Event("EVE-ADD",1L));
		lstEvent.add( new Event("EVE-ADD",2L));
	   
		eventIteratorGMA= new EventIteratorGMA(lstEvent.iterator());
				
		assertTrue(eventIteratorGMA.moveNext());
		assertFalse(eventIteratorGMA.moveNext());
		
	}
	@Test
	public void  moveNext_semItens() {
		List<Event> lstEvent=new ArrayList<>(); 
	   
		eventIteratorGMA= new EventIteratorGMA(lstEvent.iterator());
				
		
		assertFalse(eventIteratorGMA.moveNext());
	}

	@Test(expected=IllegalStateException.class)
	public void current_notCallNext_should_illegalStateException() {
		
		List<Event> lstEvent=new ArrayList<>(); 
		lstEvent.add( new Event("EVE-ADD",1L));
		
		eventIteratorGMA= new EventIteratorGMA(lstEvent.iterator());
		
		Event e=eventIteratorGMA.current();
			
	}
	
	@Test(expected=IllegalStateException.class)
	public void current_empty_should_illegalStateException() {
		
		List<Event> lstEvent=new ArrayList<>(); 
		
		eventIteratorGMA= new EventIteratorGMA(lstEvent.iterator());
		
		Event e=eventIteratorGMA.current();
			
	}
	
	@Test(expected=IllegalStateException.class)
	public void current_() {
		
		List<Event> lstEvent=new ArrayList<>(); 
		lstEvent.add( new Event("EVE-ADD",1L));
		lstEvent.add( new Event("EVE-ADD",2L));
		lstEvent.add( new Event("EVE-ADD",3L));
		
		eventIteratorGMA= new EventIteratorGMA(lstEvent.iterator());
		
		eventIteratorGMA.moveNext();
		Event e=eventIteratorGMA.current();
			
	}


}
