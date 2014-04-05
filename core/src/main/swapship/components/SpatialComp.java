package main.swapship.components;

import com.artemis.Component;

/**
 * Component holding the location and width and height of an Entity
 * @author Brandon
 *
 */
public class SpatialComp implements Component {

	public float x;
	public float y;
	
	public int width;
	public int height;
	
	public void setValues(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void reset() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

}
