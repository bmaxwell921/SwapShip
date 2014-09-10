package main.swapship.systems;

import main.swapship.components.SpatialComp;
import main.swapship.components.TargetComp;
import main.swapship.components.VelocityComp;
import main.swapship.factories.EntityFactory;
import main.swapship.util.GameUtil;

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
			e.deleteFromWorld();
			EntityFactory.createExplosion(world, sc.x, sc.y, vc.xVel, vc.yVel);
			return;
		}
		
		SpatialComp tsc = scm.get(target);
		Vector2 dir = GameUtil.calcDirection(sc.x, sc.y, tsc.x, tsc.y);
		vc.setXVel(dir.x * vc.maxVel);
		vc.setYVel(dir.y * vc.maxVel);
	}
}
