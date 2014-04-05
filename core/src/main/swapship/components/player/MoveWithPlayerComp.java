package main.swapship.components.player;

import com.artemis.Component;
import com.artemis.Entity;

public class MoveWithPlayerComp implements Component {

	public int xDisplace;
	public int yDispace;
	
	@Override
	public void reset() {
		xDisplace = 0;
		yDispace = 0;
	}
}
