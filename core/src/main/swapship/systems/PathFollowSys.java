package main.swapship.systems;

import main.swapship.common.Constants;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.other.PathFollowComp;
import main.swapship.components.other.PathTargetComp;
import main.swapship.util.VectorUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class PathFollowSys extends EntityProcessingSystem {

	private ComponentMapper<PathFollowComp> pfcm;
	private ComponentMapper<PathTargetComp> ptcm;
	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<VelocityComp> vcm;
	
	public PathFollowSys() {
		super(Filter.allComponents(PathFollowComp.class, PathTargetComp.class, SpatialComp.class));
	}
	
	@Override
	public void initialize() {
		pfcm = world.getMapper(PathFollowComp.class);
		ptcm = world.getMapper(PathTargetComp.class);
		scm = world.getMapper(SpatialComp.class);
		vcm = world.getMapper(VelocityComp.class);
	}

	@Override
	public void process(Entity e) {
		PathTargetComp ptc = ptcm.get(e);
		SpatialComp sc = scm.get(e);
		
		float dist = VectorUtil.dist(sc.x, sc.y, ptc.target);
		
		if (dist <= Constants.SHIP_WIDTH) {
			PathFollowComp pfc = pfcm.get(e);
			++pfc.target;
			// They've run out of things to follow, so it must be off the screen.
			if (pfc.path.size <= pfc.target) {
				e.deleteFromWorld();
				Gdx.app.log("CULLING", "Deleted entity at the end of path");
				return;
			}
			ptc.target = pfc.path.get(pfc.target);
		}
		
		// Update the velocity
		Vector2 dir = VectorUtil.calcDirection(sc.x, sc.y, ptc.target.x, ptc.target.y);
		VelocityComp vc = vcm.get(e);
		vc.xVel = dir.x * vc.maxVel;
		vc.yVel = dir.y * vc.maxVel;
	}
	

}