package main.swapship.components;

import com.artemis.Component;

public class HealthComp implements Component {
	
	public int health;
	
	// Whether the containing entity is able to be killed
	public boolean killable;
	
	@Override
	public void reset() {
		health = 0;
	}
}
