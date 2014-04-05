package main.swapship.components;

import com.artemis.Component;

public class HealthComp implements Component {
	
	public int health;
	
	@Override
	public void reset() {
		health = 0;
	}
}
