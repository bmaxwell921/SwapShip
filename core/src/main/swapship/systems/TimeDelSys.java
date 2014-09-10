package main.swapship.systems;

import main.swapship.components.TimeDelComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class TimeDelSys extends EntityProcessingSystem {

	private ComponentMapper<TimeDelComp> tdcm;
	
	public TimeDelSys() {
		super(Filter.allComponents(TimeDelComp.class));
	}
	
	@Override
	public void initialize() {
		tdcm = world.getMapper(TimeDelComp.class);
	}
	
	@Override
	public void process(Entity e) {
		TimeDelComp tdc = tdcm.get(e);
		
		tdc.timeLeft -= world.getDelta();
		
		if (tdc.timeLeft <= 0) {
			e.deleteFromWorld();
		}
	}
}
