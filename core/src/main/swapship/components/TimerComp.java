package main.swapship.components;

import com.artemis.Component;

/**
 * Generic timer component, used 
 * @author Brandon
 *
 */
public abstract class TimerComp implements Component {

	public float startTime;
	public float timeLeft;
	
	@Override
	public void reset() {
		startTime = timeLeft = 0;
	}
}
