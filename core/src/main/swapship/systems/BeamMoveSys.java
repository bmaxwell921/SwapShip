package main.swapship.systems;

import main.swapship.components.SpatialComp;
import main.swapship.components.diff.BeamComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class BeamMoveSys extends EntityProcessingSystem {

	// Player entity
	private Entity player;
	
	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<BeamComp> bcm;
	
	public BeamMoveSys(Entity player) {
		super(Filter.allComponents(BeamComp.class, SpatialComp.class));
		this.player = player; 
	}
	
	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		bcm = world.getMapper(BeamComp.class);
	}
	
	@Override
	protected void process(Entity e) {
		SpatialComp beamS = scm.get(e);
		BeamComp bc = bcm.get(e);
		SpatialComp playerS = scm.get(player);
		
		beamS.x = playerS.x + playerS.width / 2 - beamS.width / 2;
		beamS.y = playerS.y + playerS.height + beamS.height * bc.num;
	}
}
