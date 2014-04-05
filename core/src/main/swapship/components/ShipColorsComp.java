package main.swapship.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class ShipColorsComp implements Component {

	public Color topColor;
	public Color midColor;
	public Color botColor;

	public void setValues(Color topColor, Color midColor, Color botColor) {
		this.topColor = topColor;
		this.midColor = midColor;
		this.botColor = botColor;
	}

	public void setTop(Color topColor) {
		this.topColor = topColor;
	}

	public void setMid(Color midColor) {
		this.midColor = midColor;
	}

	public void setBot(Color botColor) {
		this.botColor = botColor;
	}

	@Override
	public void reset() {
		this.topColor = this.midColor = this.botColor = Color.WHITE;
	}

}
