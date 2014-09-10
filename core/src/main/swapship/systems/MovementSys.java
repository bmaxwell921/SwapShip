package main.swapship.systems;

import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSys extends EntityProcessingSystem {

	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<VelocityComp> vcm;
	
	public MovementSys() {
		super(Filter.allComponents(SpatialComp.class, VelocityComp.class));
	}
	
	@Override
	public void initialize() {
		this.scm = world.getMapper(SpatialComp.class);
		this.vcm = world.getMapper(VelocityComp.class);
	}
	
	@Override
	public void process(Entity e) {
		SpatialComp sc = scm.get(e);
		VelocityComp vc = vcm.get(e);
		
		sc.x += vc.xVel * world.getDelta();
		sc.y += vc.yVel * world.getDelta();
	}
}
