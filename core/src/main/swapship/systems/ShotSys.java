package main.swapship.systems;

import main.swapship.components.FireRateComp;
import main.swapship.components.LevelComp;
import main.swapship.components.SpatialComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class ShotSys extends EntityProcessingSystem {

	private ComponentMapper<FireRateComp> frcm;
	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<LevelComp> lcm;
	
	public ShotSys() {
		super(Filter.allComponents(FireRateComp.class));
	}
	
	@Override
	public void initialize() {
		frcm = world.getMapper(FireRateComp.class);
		scm = world.getMapper(SpatialComp.class);
		lcm = world.getMapper(LevelComp.class);
	}
	
	@Override
	public void process(Entity e) {
		FireRateComp frc = frcm.get(e);
		
		frc.timeLeft -= world.getDelta();
		
		if (frc.timeLeft <= 0) {
			
		}
	}
}
