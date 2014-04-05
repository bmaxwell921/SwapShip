package main.swapship.util;

import main.swapship.common.Constants;
import main.swapship.components.LevelComp;
import main.swapship.components.ShipColorsComp;
import main.swapship.components.ShipSpritesComp;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.dist.PlayerComp;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;

public class EntityFactory {

	/**
	 * Creates the player, returning so other classes can have easy access to it
	 * 
	 * @param world
	 * @return
	 */
	public static Entity createPlayer(World world) {
		Entity e = world.createEntity();
		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(Gdx.graphics.getWidth() / 2 - Constants.SHIP_WIDTH / 2,
				Constants.Player.MIN_Y, Constants.SHIP_WIDTH,
				Constants.SHIP_HEIGHT);
		e.addComponent(sc);

		ShipSpritesComp ssc = world.createComponent(ShipSpritesComp.class);
		ssc.setValues(Constants.Player.ARTEMIS_TOP,
				Constants.Player.ARTEMIS_MID, Constants.Player.ARTEMIS_BOT);
		e.addComponent(ssc);

		ShipColorsComp scc = world.createComponent(ShipColorsComp.class);
		scc.setValues(Constants.Player.DEFAULT_COLOR,
				Constants.Player.DEFAULT_COLOR, Constants.Player.DEFAULT_COLOR);
		e.addComponent(scc);

		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.setValues(Constants.Player.START_VEL, Constants.Player.START_VEL);
		e.addComponent(vc);

		e.addComponent(world.createComponent(PlayerComp.class));
		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
		e.addToWorld();

		LevelComp lc = world.createComponent(LevelComp.class);
		lc.setValues(Constants.Player.BASE_PART_LVL,
				Constants.Player.BASE_PART_LVL, Constants.Player.BASE_PART_LVL,
				Constants.Player.BASE_DAMAGE);
		e.addComponent(lc);

		return e;
	}
	
	public static void createShot(World world, int x, int y, boolean playerShot) {
		Entity e = world.createEntity();
		SpatialComp sc = world.createComponent(SpatialComp.class);
	}

}
