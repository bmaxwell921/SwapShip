package main.swapship.util;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.utils.Array;

public class TargetUtil {

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

	public static Array<Entity> findRandTargets(World w, String targetGroup,
			int amount) {
		return RandUtil.chooseNumFrom(amount, w.getManager(GroupManager.class)
				.getEntities(targetGroup));
	}

	public static boolean existsTargets(World w, String targetGroup) {
		return w.getManager(GroupManager.class).getEntities(targetGroup).size > 0;
	}
}
