package main.swapship.systems;

import main.swapship.common.Constants;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.diff.PlayerComp;
import main.swapship.components.player.SpecialComp;
import main.swapship.factories.EntityFactory;
import main.swapship.util.InputProcessingUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class InputSys extends EntityProcessingSystem {
	
	// Camera used to unproject touch positions
	private OrthographicCamera camera;
	private ComponentMapper<VelocityComp> vcm;
	private ComponentMapper<SpecialComp> scm;
	private ComponentMapper<SpatialComp> spcm;

	// field here, so we don't create lots of data
	private Vector3 touchPoint;
	public InputSys(OrthographicCamera camera) {
		super(Filter.allComponents(VelocityComp.class, PlayerComp.class));
		touchPoint = Vector3.Zero;
		this.camera = camera;
	}
	
	@Override
	public void initialize() {
		vcm = world.getMapper(VelocityComp.class);
		scm = world.getMapper(SpecialComp.class);
		spcm = world.getMapper(SpatialComp.class);
	}
	
	@Override
	public void process(Entity e) {		
		
		checkVelocity(e);
		checkSpecial(e);
	}
	
	private void checkVelocity(Entity e) {
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
		
		VelocityComp vc = vcm.get(e);
		vc.setXVel(xRate * Constants.Player.MAX_MOVE);
		vc.setYVel(yRate * Constants.Player.MAX_MOVE);
	}
	
	private void checkSpecial(Entity e) {
		if (!Gdx.input.justTouched()) {
			return;
		}
		SpecialComp sc = scm.get(e);
		SpatialComp spc = spcm.get(e);
		camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		
		if (touchPoint.x < Gdx.graphics.getWidth() / 2) {
			// Too bad, no special left
			if (sc.defensiveCount <= 0) {
				return;
			}
			// Create defensive special
			
			--sc.defensiveCount;
		} else {
			if (sc.offensiveCount <= 0) {
				return;
			}
			
			// Create offensive special
			EntityFactory.createOffensiveSpecial(world, sc.offensive, spc.x + spc.width / 2, spc.y + spc.height);
			--sc.offensiveCount;
		}
	}
}
