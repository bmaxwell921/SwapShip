package main.swapship.util;

import main.swapship.common.Constants;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.utils.Array;

public class TargetUtil {
	
	public static Entity findRandTarget(World w, String targetGroup) {
		return findRandTargets(w, targetGroup, 1).first();
	}
	
	public static Array<Entity> findRandTargets(World w, String targetGroup, int amount) {
		return RandUtil.chooseNumFrom(amount, w.getManager(GroupManager.class).getEntities(targetGroup));
	}
}
