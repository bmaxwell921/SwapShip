package main.swapship.systems;

import main.swapship.components.SpatialComp;
import main.swapship.components.TargetComp;
import main.swapship.components.VelocityComp;
import main.swapship.util.TargetUtil;
import main.swapship.util.VectorUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;

public class TargetSys extends EntityProcessingSystem {

	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<TargetComp> tcm;
	private ComponentMapper<VelocityComp> vcm;
	
	public TargetSys() {
		super(Filter.allComponents(SpatialComp.class, TargetComp.class, VelocityComp.class));
	}
	
	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		tcm = world.getMapper(TargetComp.class);
		vcm = world.getMapper(VelocityComp.class);
	}
	
	@Override
	public void process(Entity e) {
		SpatialComp sc = scm.get(e);
		TargetComp tc = tcm.get(e);
		VelocityComp vc = vcm.get(e);
		
		Entity target = tc.target;
		
		// If the target was already destroyed, get a new one
		if (target == null || !target.isActive()) {
			target = TargetUtil.findRandTarget(world, tc.targetGroup);
		}
		
		if (target == null) {
			return;
		}
		
		SpatialComp tsc = scm.get(target);
		Vector2 dir = VectorUtil.calcDirection(sc.x, sc.y, tsc.x, tsc.y);
		vc.setXVel(dir.x * vc.maxVel);
		vc.setYVel(dir.y * vc.maxVel);
	}
}
