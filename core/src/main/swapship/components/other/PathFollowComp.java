package main.swapship.components.other;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Component holding the path for an Entity to follow
 * @author Brandon
 *
 */
public class PathFollowComp implements Component {

	public Array<Vector2> path;
	public int target;
	
	public void setValues(Array<Vector2> path) {
		this.path = path;
		target = 0;
	}
	
	@Override
	public void reset() {
		path = null;
		target = 0;
	}
}
