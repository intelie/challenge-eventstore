package gma.intelie.challanges;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;

public class EventStoreGMATest {

	@Test
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
	public void insert_3Event2duplicate_returnAdd2Event() {

		EventStoreGMA eventStoreGMA = new EventStoreGMA();
		 
		eventStoreGMA.insert(new Event("EV_ADD", 100L));
		eventStoreGMA.insert(new Event("EV_ADD", 100L));
		eventStoreGMA.insert(new Event("EV_ADD", 101L));

		EventIterator ei = eventStoreGMA.query("EV_ADD", 100L, 121L);

		assertTrue(getTotItem(ei) == 2);
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
		
	
		assertTrue(e1.timestamp()==110L && e2.timestamp()==115L && e3.timestamp()==120L);
		assertTrue(e1.type().equals("EV_ADD") && e2.type().equals("EV_ADD") && e3.type().equals("EV_ADD"));
		
		 
		
	}
	
	@Test
	public void linha() throws InterruptedException {
		
		EventStoreGMA eventStoreGMA = new EventStoreGMA();
		
		Thread t1=createThread(10,2000,eventStoreGMA);
		Thread t2=createThread(100,150,eventStoreGMA);
		t1.start();
		t2.start();
			
		System.out.println("Principallllll");
		Thread.sleep(6000);
		
		EventIterator ei1 = eventStoreGMA.query("EV_ADD", 10L, 200L);
		while (ei1.moveNext()) {
			System.out.println("----------->"+ei1.current().timestamp());
		}
		
		System.out.println("Fim-Principallllll");
		
		
	}
	
	private Thread createThread(long ini, int pausa,EventStore eventStoreGMA ) {
		
		
		Thread t1= new Thread(()->{
		
			long n=ini;
			while(true){
				System.out.println("Acordou: "+Thread.currentThread().getId());
				eventStoreGMA.insert(new Event("EV_ADD", n++));
				try {
					Thread.sleep( pausa);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}) ;
		t1.setDaemon(true);
		
		return t1;
	}

	private int getTotItem(EventIterator eventIte) {

		int tt = 0;
		while (eventIte.moveNext()) {
			tt++;
		}

		return tt;
	}
}
