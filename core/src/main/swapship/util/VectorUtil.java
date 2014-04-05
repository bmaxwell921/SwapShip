package main.swapship.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class VectorUtil {

	public static final int CIRCLE_DEGREES = 360;
	
	/**
	 * Calculates the rotation from vertical for the given vector
	 * @param x
	 * @param y
	 * @return
	 */
	public static float calcRotation(int x, int y) {
		return CIRCLE_DEGREES - (float) MathUtils.atan2(x, y) * MathUtils.radiansToDegrees;
	}
	
	/**
	 * Calculates a vector that points from the source to the target. Returns a 
	 * normalized vector
	 * @param srcX
	 * @param srcY
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public static Vector2 calcDirection(float srcX, float srcY, float targetX, float targetY) {
		return new Vector2(targetX, targetY).sub(srcX, srcY).nor();
	}
}
