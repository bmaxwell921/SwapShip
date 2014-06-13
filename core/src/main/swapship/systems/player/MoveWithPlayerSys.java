package main.swapship.systems.player;

import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.player.MoveWithPlayerComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class MoveWithPlayerSys extends EntityProcessingSystem {

	private Entity player;
	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<MoveWithPlayerComp> mwpcm;
	
	public MoveWithPlayerSys(Entity player) {
		super(Filter.allComponents(MoveWithPlayerComp.class, SpatialComp.class));
		this.player = player;
	}

	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		mwpcm = world.getMapper(MoveWithPlayerComp.class);
	}
	
	@Override
	protected void process(Entity e) {
		SpatialComp eVc = scm.get(e);
		MoveWithPlayerComp mwpc = mwpcm.get(e);
		SpatialComp pVc = scm.get(player);
		
		eVc.x = pVc.x + mwpc.xDisplace;
		eVc.y = pVc.y + mwpc.yDispace;
	}
	
	
}
