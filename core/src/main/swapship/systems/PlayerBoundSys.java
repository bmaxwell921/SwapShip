package main.swapship.systems;

import main.swapship.common.Constants;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.diff.PlayerComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;

public class PlayerBoundSys extends EntityProcessingSystem {

	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<VelocityComp> vcm;
	
	public PlayerBoundSys() {
		super(Filter.allComponents(SpatialComp.class, VelocityComp.class).anyComponents(PlayerComp.class));
	}
	
	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		vcm = world.getMapper(VelocityComp.class);
	}
	
	@Override
	public void process(Entity e) {
		SpatialComp sc = scm.get(e);
		
		//Left
		if (sc.x < 0 ) {
			sc.x = 0;
		}
		
		// Right
		if (sc.x + sc.width > Gdx.graphics.getWidth()) {
			sc.x = Gdx.graphics.getWidth() - sc.width;
		}
		
		// Bottom
		if (sc.y < 0) {
			sc.y = 0;
		}
		
		// Top
		if (sc.y + sc.height > Constants.Player.MAX_Y) {
			sc.y = Constants.Player.MAX_Y - sc.height;
		}
	}
}
