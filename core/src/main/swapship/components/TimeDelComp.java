package main.swapship.components;


/**
 * Component used for timed deletion of entities
 * @author Brandon
 *
 */
public class TimeDelComp extends TimerComp {

	public void setValues(float lifeTime) {
		this.startTime = lifeTime;
		this.timeLeft = startTime;
	}
	
}
