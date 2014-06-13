package main.swapship.systems.player;

import main.swapship.common.Constants;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.player.SpecialComp;
import main.swapship.components.types.BeamComp;
import main.swapship.components.types.PlayerComp;
import main.swapship.factories.EntityFactory;
import main.swapship.util.GameUtil;

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
		// Beam and Player should move the same way
		super(Filter.allComponents(VelocityComp.class).any(PlayerComp.class,
				BeamComp.class));
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
		float xRate = GameUtil.roundTilt(-Gdx.input
				.getAccelerometerX());
		float yRate = GameUtil.roundTilt(-Gdx.input
				.getAccelerometerY());

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
			if (sc.defensiveCount <= 0
					|| !GameUtil.existsTargets(world, Constants.Groups.ENEMY)) {
				return;
			}
			// Create defensive special
			if (EntityFactory.createDefensiveSpecial(world, sc.defensive, spc.x, spc.y)) {
				--sc.defensiveCount;
			}
		} else {
			if (sc.offensiveCount <= 0
					|| !GameUtil.existsTargets(world, Constants.Groups.ENEMY)) {
				return;
			}

			// Create offensive special, only decrement if it was made
			if (EntityFactory.createOffensiveSpecial(world, sc.offensive, spc.x, spc.y)) {
				--sc.offensiveCount;
			}
		}
	}
}
