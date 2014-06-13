package main.swapship.util;

import java.util.ArrayList;
import java.util.List;

import main.swapship.common.Constants;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Utility class with methods used in game. Consolidation of old
 * InputProcessingUtil, RandUtil, TargetUtil, and VectorUtil
 * @author Brandon
 *
 */
public class GameUtil {

	
	/*
	 * ===============================================================================
	 * Input Processing
	 * ===============================================================================
	 */
	/**
	 * Method used to convert a raw tilt value to one that is an increment of .10
	 * @param tiltVal
	 * @return
	 */
	public static float roundTilt(float tiltVal) {
		return MathUtils.round(tiltVal / Constants.Player.DEGREE_AMT) * Constants.Player.DEGREE_AMT;
	}
	
	/*
	 * ===============================================================================
	 * Targeting
	 * ===============================================================================
	 */
	/**
	 * Method used to find a single random target of the given group
	 * @param w
	 * @param targetGroup
	 * @return
	 */
	public static Entity findRandTarget(World w, String targetGroup) {
		if (!existsTargets(w, targetGroup)) {
			return null;
		}
		Array<Entity> targets = findRandTargets(w, targetGroup, 1);
		if (targets == null) {
			return null;
		}
		return targets.first();
	}

	/**
	 * Method used to find the given number of targets from the given group
	 * @param w
	 * @param targetGroup
	 * @param amount
	 * @return
	 */
	public static Array<Entity> findRandTargets(World w, String targetGroup,
			int amount) {
		return RandUtil.chooseNumFrom(amount, w.getManager(GroupManager.class)
				.getEntities(targetGroup));
	}

	/**
	 * Method to check whether there exists a target in the given group
	 * @param w
	 * @param targetGroup
	 * @return
	 */
	public static boolean existsTargets(World w, String targetGroup) {
		return w.getManager(GroupManager.class).getEntities(targetGroup).size > 0;
	}
	
	/*
	 * ===============================================================================
	 * Randomness
	 * ===============================================================================
	 */
	/**
	 * Method used to randomly choose <amount> number of elements from choices
	 * @param amount
	 * @param choices
	 * @return
	 */
	public static <E> Array<E> chooseNumFrom(int amount, Array<E> choices) {
		// Quick finish if we're gonna pick them all
		if (amount >= choices.size) {
			return choices;
		}
		
		List<Integer> choiceIndex = new ArrayList<>();
		for (int i = 0; i < choices.size; ++i) {
			choiceIndex.add(i);
		}
		
		// Basically just choose a random index, then we move that 
		// chosen index so it can't be chosen again
		int searchSpace = choices.size - 1;
		Array<E> selections = new Array<E>(amount);
		for (int i = 0; i < amount; ++i) {
			int index = choiceIndex.get(MathUtils.random(0, searchSpace));
			
			selections.add(choices.get(index));
			
			// Swap the chosen to the end
			int temp = choiceIndex.get(index);
			choiceIndex.set(index, choiceIndex.get(searchSpace));
			choiceIndex.set(searchSpace, temp);
			--searchSpace;
		}
		
		return selections;
	}
	
	/*
	 * ===============================================================================
	 * Vectors
	 * ===============================================================================
	 */
	// Degrees in a circle
	public static final int CIRCLE_DEGREES = 360;
	
	// Vector used to cut down on memory usage
	private static final Vector2 use = Vector2.Zero;
	
	/**
	 * Calculates the rotation from vertical for the given vector
	 * @param xVel
	 * @param yVel
	 * @return
	 */
	public static float calcRotation(float xVel, float yVel) {
		return CIRCLE_DEGREES - (float) MathUtils.atan2(xVel, yVel) * MathUtils.radiansToDegrees;
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
	
	/**
	 * Returns the distance from the given x,y location to the target
	 * @param srcX
	 * @param srcY
	 * @param target
	 * @return
	 */
	public static float dist(float srcX, float srcY, Vector2 target) {
		return use.set(srcX, srcY).dst(target);
		
	}
}
