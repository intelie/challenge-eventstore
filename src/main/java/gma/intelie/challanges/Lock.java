package gma.intelie.challanges;

public class Lock {
	private boolean isLocked = false;

	  public synchronized void lock() {
		  isLocked = true;
	    while(isLocked){
	      try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    isLocked = true;
	  }

	  public synchronized void unlock(){
	    isLocked = false;
	    notify();
	  }

}
