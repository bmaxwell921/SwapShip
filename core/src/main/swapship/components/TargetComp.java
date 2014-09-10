package main.swapship.components;

import com.artemis.Component;
import com.artemis.Entity;

public class TargetComp implements Component {

	public Entity target;
	public String targetGroup;
	
	public TargetComp() {
		target = null;
	}
	
	@Override
	public void reset() {
		target = null;
	}
}
