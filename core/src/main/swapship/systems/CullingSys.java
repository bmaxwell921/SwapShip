package main.swapship.systems;

import main.swapship.components.SpatialComp;
import main.swapship.components.diff.BeamComp;
import main.swapship.components.diff.PlayerComp;
import main.swapship.components.other.PathFollowComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;

/**
 * System used to cull bullets and enemies that go out of the screen
 * @author Brandon
 *
 */
public class CullingSys extends EntityProcessingSystem {

	private ComponentMapper<SpatialComp> scm;
	
	public CullingSys() {
		// Excluding the path followers because they already have deletion methods
		super(Filter.allComponents(SpatialComp.class).exclude(PlayerComp.class, PathFollowComp.class, BeamComp.class));
	}
	
	@Override
	public void initialize() {
		this.scm = world.getMapper(SpatialComp.class);
	}
	
	@Override
	public void process(Entity e) {
		SpatialComp sc = scm.get(e);
		
		// Left
		if (sc.x - sc.width < 0) {
			e.deleteFromWorld();
			return;
		}		
		// Right
		if (sc.x > Gdx.graphics.getWidth()) {
			e.deleteFromWorld();
			return;
		}		
		// Bottom
		if (sc.y + sc.height < 0) {
			e.deleteFromWorld();
			return;
		}		
		// Top
		if (sc.y > Gdx.graphics.getHeight()) {
			e.deleteFromWorld();
			return;
		}
	}
}
