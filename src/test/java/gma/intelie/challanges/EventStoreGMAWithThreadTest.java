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
	 

	//	that's all folks
	//:<
	

	@Test
	public void crazy() throws InterruptedException {

		EventStore eventStore = EventStoreGMA.create();
		
		Thread t1Add=new Thread(()->{addItem(10,1000,eventStore);}) ;
		Thread t2Add=new Thread(()->{addItem(100,500,eventStore);}) ;
		Thread t3Add=new Thread(()->{addItem(300,800,eventStore);}) ;
		Thread t4Add=new Thread(()->{addItem(400,1500,eventStore);}) ;
		Thread t3Show=new Thread(()->{showItem(800,eventStore);}) ;
		Thread t4Del=new Thread(()->{removeItem(2000,eventStore);}) ;
		Thread t5Del=new Thread(()->{removeItem(2000,eventStore);}) ;
		Thread t6RemoveAll=new Thread(()->{removeAll(950,eventStore);}) ;
		
		t1Add.start();
		t2Add.start();
		t3Add.start();
		t4Add.start();
		
		t3Show.start();
		t4Del.start();
		t5Del.start();
		t6RemoveAll.start();
				
		 Thread.sleep(200);
		
		System.out.println("FIMMMM:");
		
		EventIterator evIt=eventStore.query(EVENT_TYPE_ADD,10L, 4000L);
		System.out.println("FIMMMM");
		while(evIt.moveNext()) {
			System.out.println("show item---------->"+evIt.current().timestamp());
			
		}	
		
	}
	
	private void removeAll(int pausa,EventStore eventStore) {
		while(true) {
			EventIterator evIt=eventStore.query(EVENT_TYPE_ADD,10L, 4000L);
			System.out.println("*************Acordou remove all");
			
			eventStore.removeAll(EVENT_TYPE_ADD);
			try {
				 
				Thread.sleep(pausa);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void removeItem(int pausa,EventStore eventStore) {
		while(true) {
			EventIterator evIt=eventStore.query(EVENT_TYPE_ADD,10L, 4000L);
			System.out.println("Acordou del item");
			while(evIt.moveNext()) {
				 evIt.remove();
				
			}	
			try {
				 
				Thread.sleep(pausa);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	 
}
