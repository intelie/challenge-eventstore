package gma.intelie.challanges;

import net.intelie.challenges.EventIterator;

public class EventIteratorUtil {
	
	
	public static int getTotItem(EventIterator eventIte) {

		int tt = 0;
		while (eventIte.moveNext()) {
			tt++;
		}

		return tt;
	}

}
