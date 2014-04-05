package main.swapship.components.player;

import com.artemis.Component;
import com.artemis.Entity;

public class MoveWithPlayerComp implements Component {

	public float xDisplace;
	public float yDispace;
	
	@Override
	public void reset() {
		xDisplace = 0;
		yDispace = 0;
	}
}
