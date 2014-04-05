package main.swapship.components;

import com.artemis.Component;

public class TargetComp implements Component {

	public SpatialComp target;
	
	public TargetComp() {
		target = null;
	}
	
	@Override
	public void reset() {
		target = null;
	}
}
