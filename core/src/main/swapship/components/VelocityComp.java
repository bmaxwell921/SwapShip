package main.swapship.components;

import com.artemis.Component;

public class VelocityComp implements Component {

	public float xVel;
	public float yVel;
	
	public void setValues(float xVel, float yVel) {
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public void setXVel(float xVel) {
		this.xVel = xVel;
	}
	
	public void setYVel(float yVel) {
		this.yVel = yVel;
	}
	
	@Override
	public void reset() {
		this.xVel = yVel = 0;
	}
}
