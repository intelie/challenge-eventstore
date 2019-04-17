package gma.intelie.challanges;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;

public class EventStoreGMAWithThreadTest {
	
	private final String EVENT_TYPE_ADD="E_ADD";
	private final String EVENT_TYPE_DIV="E_DIV";
	
	
	 
	public void GMA_AAA() {
		List<String> lstStr=new ArrayList<>();
		
		lstStr.add("11111");
		lstStr.add("22222");
		lstStr.add("33333");
		
		Iterator it1= lstStr.iterator();
		
	//	lstStr.add("44444");
	//	lstStr.add("55555");
	//	lstStr.add("66666");
		
		Iterator it2= lstStr.iterator();
		
		while(it1.hasNext()) {
			String x=(String) it1.next();
			System.out.println("-------->"+x);
		}
		
		
		
	}
	
	
	public void GMA() {
		EventStore eventStore = EventStoreGMA.create();
		eventStore.insert(new Event(EVENT_TYPE_ADD, 100L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 110L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 120L));
		
	 	EventIterator evIt1=eventStore.query(EVENT_TYPE_ADD,10L, 400L);
	
	 	eventStore.insert(new Event(EVENT_TYPE_ADD, 140L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 151L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 130L));
		eventStore.insert(new Event(EVENT_TYPE_ADD, 150L));
	
		 
		EventIterator evIt2=eventStore.query(EVENT_TYPE_ADD,10L, 400L);
		
		System.out.println("--- EV-1 -------------------------------------");
		while(evIt1.moveNext()) {
			System.out.println("--->"+evIt1.current().timestamp());
			
		//	evIt1.remove();
			
		}
		
		
		
		//EventIterator evIt2=eventStore.query(EVENT_TYPE_ADD,10L, 400L);
		System.out.println("--- EV-2 -------------------------------------");
		while(evIt2.moveNext()) {
			System.out.println("--->"+evIt2.current().timestamp());
		 
		}
		
		
		EventIterator evIt3=eventStore.query(EVENT_TYPE_ADD,10L, 400L);
		System.out.println("--- EV-3 -------------------------------------");
		while(evIt3.moveNext()) {
			System.out.println("--->"+evIt3.current().timestamp());
		 
		}
		
	}
	

	@Test
	public void linha() throws InterruptedException {

		EventStore eventStore = EventStoreGMA.create();
		
		Thread t1=new Thread(()->{addItem(10,1000,eventStore);}) ;
		Thread t2=new Thread(()->{addItem(100,500,eventStore);}) ;
		Thread t3=new Thread(()->{showItem(2000,eventStore);}) ;
		t1.start();
		t2.start();
		t3.start();
		
				
		Thread.sleep(20000);
		
		 
		
		 
		
		 

	}
	
	private void showItem(int pausa,EventStore eventStore) {
		
		
		while(true) {
			EventIterator evIt=eventStore.query(EVENT_TYPE_ADD,10L, 4000L);
			System.out.println("Acordou show item");
			while(evIt.moveNext()) {
				System.out.println("show item---------->"+evIt.current().timestamp());
				
			}	
			try {
				 
				Thread.sleep(pausa);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void addItem(long ini, int pausa, EventStore eventStore)  {

		long n = ini;
		while (true) {
			n++;
			System.out.println("Acordou: " + Thread.currentThread().getId() + " - n=" + n);
			eventStore.insert(new Event(EVENT_TYPE_ADD, n));
				
			 
			try {
				 
				Thread.sleep(pausa);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private Thread createThread_delete(int pausa, EventStore eventStore) {

		Thread t1 = new Thread(() -> {

			EventIterator ei1 = eventStore.query("EV_ADD", 10L, 400L);
			while (ei1.moveNext()) {

				System.out.println(
						"Acordou e deleta: " + Thread.currentThread().getId() + " " + ei1.current().timestamp());
				ei1.remove();
				try {
					Thread.sleep(pausa);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		// t1.setDaemon(true);

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
