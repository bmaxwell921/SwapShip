package main.swapship.util;

import main.swapship.common.Constants;

import com.badlogic.gdx.math.MathUtils;

public class InputProcessingUtil {
	
	/**
	 * Method used to convert a raw tilt value to one that is an increment of .10
	 * @param tiltVal
	 * @return
	 */
	public static float roundTilt(float tiltVal) {
		return MathUtils.round(tiltVal / Constants.Player.DEGREE_AMT) * Constants.Player.DEGREE_AMT;
	}
}
