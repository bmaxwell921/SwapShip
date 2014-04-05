package main.swapship.components;


/**
 * Component for ships 
 * @author Brandon
 *
 */
public class FireRateComp extends TimerComp{
	public void setValues(float fireRate) {
		super.startTime = fireRate;
		super.timeLeft = 0;
	}
}
