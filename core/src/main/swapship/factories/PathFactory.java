package main.swapship.factories;

import main.swapship.common.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Class used to get Paths for enemies to follow
 * @author Brandon
 *
 */
public class PathFactory {
	
	public static Array<Vector2> createPath() {
		// We'll grab a point every ship size * 2 down the screen, so the path size should be as such. Plus
		// 1 so we'll go down the screen
		Array<Vector2> path = new Array<>();
		
		float y = Gdx.graphics.getHeight() - (Constants.SHIP_HEIGHT * 3);
		float x;
		
		while (y >= -Constants.SHIP_HEIGHT * 4) {
			x = MathUtils.random(Gdx.graphics.getWidth() - Constants.SHIP_WIDTH);
			path.add(new Vector2(x, y));
			
			y -= Constants.SHIP_HEIGHT * 2;
		}
		
		return path;
	}
	
}
