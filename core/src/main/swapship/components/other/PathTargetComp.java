package main.swapship.components.other;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class PathTargetComp implements Component {

	public Vector2 target;

	@Override
	public void reset() {
		target.set(0, 0);
	}
	
	
}
