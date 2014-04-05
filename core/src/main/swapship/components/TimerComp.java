package main.swapship.components;

import com.artemis.Component;

/**
 * Generic timer component, used 
 * @author Brandon
 *
 */
public abstract class TimerComp implements Component {

	public int startTime;
	public int timeLeft;
	
	@Override
	public void reset() {
		startTime = timeLeft = 0;
	}
}
