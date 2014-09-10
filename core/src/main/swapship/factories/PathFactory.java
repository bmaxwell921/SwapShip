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
		float x = Gdx.graphics.getWidth() / 2;
		
		// Times 4 because enemies say they're close enough to their target when 
		// they're within 1 ship_height of it.
		while (y >= -Constants.SHIP_HEIGHT * 4) {
			x = calcNextX(x);
			path.add(new Vector2(x, y));
			
			y -= Constants.SHIP_HEIGHT * 2;
		}
		
		return path;
	}
	
	private static float calcNextX(float curX) {
		// 50% chance to stay the same way
		final float CHANCE_SAME = 0.5f;
		
		// Don't change it
		if (MathUtils.randomBoolean(CHANCE_SAME)) {
			return curX;
		}
		
		// Go Left - 50% chance
		if (MathUtils.randomBoolean()) {
			return MathUtils.random(curX);
		}
		
		// Go right
		return MathUtils.random(curX + 1, Gdx.graphics.getWidth() - Constants.SHIP_WIDTH);		
	}
	
}
