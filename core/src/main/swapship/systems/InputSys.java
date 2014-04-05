package main.swapship.systems;

import main.swapship.common.Constants;
import main.swapship.components.VelocityComp;
import main.swapship.components.dist.PlayerComp;
import main.swapship.util.InputProcessingUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputSys extends EntityProcessingSystem {
	
	private ComponentMapper<VelocityComp> vcm;

	public InputSys() {
		super(Filter.allComponents(VelocityComp.class, PlayerComp.class));
	}
	
	@Override
	public void initialize() {
		vcm = world.getMapper(VelocityComp.class);
	}
	
	@Override
	public void process(Entity e) {		
		// Android controls
		float xRate = InputProcessingUtil.roundTilt(-Gdx.input.getAccelerometerX());
		float yRate = InputProcessingUtil.roundTilt(-Gdx.input.getAccelerometerY());
		
		// Desktop controls
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			xRate = -1;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			xRate = 1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			yRate = 1;
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			yRate = -1;
		}
		
		vcm.get(e).setValues(xRate * Constants.Player.MAX_MOVE, yRate * Constants.Player.MAX_MOVE);
	}
}
