package main.swapship.components.player;

import com.artemis.Component;

public class MoveWithPlayerComp implements Component {

	public float xDisplace;
	public float yDispace;
	
	@Override
	public void reset() {
		xDisplace = 0;
		yDispace = 0;
	}
}
