package main.swapship.components;

import com.artemis.Component;

public class DamageComp implements Component {

	public int damage;
	
	@Override
	public void reset() {
		this.damage = 0;
	}
}
