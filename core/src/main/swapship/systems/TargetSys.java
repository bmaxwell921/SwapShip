package main.swapship.systems;

import main.swapship.components.SpatialComp;
import main.swapship.components.TargetComp;
import main.swapship.components.VelocityComp;
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
		
		Vector2 dir = VectorUtil.calcDirection(sc.x, sc.y, tc.target.x, tc.target.y);
		vc.setXVel(dir.x * vc.maxVel);
		vc.setYVel(dir.y * vc.maxVel);
	}
}
