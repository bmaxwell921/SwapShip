package main.swapship.components.other;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class SingleSpriteComp implements Component {

	public String name;
	public Color tint;
	
	@Override
	public void reset() {
		this.name = "";
		tint = Color.WHITE;
	}
}
