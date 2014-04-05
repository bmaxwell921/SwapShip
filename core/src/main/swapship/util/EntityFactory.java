package main.swapship.util;

import main.swapship.common.Constants;
import main.swapship.components.ShipColorsComp;
import main.swapship.components.ShipSpritesComp;
import main.swapship.components.SpatialComp;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;

public class EntityFactory {

	public static Entity createPlayer(World world) {
		Entity e = world.createEntity();
		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(Gdx.graphics.getWidth() / 2 - Constants.Player.WIDTH / 2,
				Constants.PLAYER_START_HEIGHT, Constants.Player.WIDTH,
				Constants.Player.HEIGHT);
		e.addComponent(sc);

		ShipSpritesComp ssc = world.createComponent(ShipSpritesComp.class);
		ssc.setValues(Constants.Player.ARTEMIS_TOP,
				Constants.Player.ARTEMIS_MID, Constants.Player.ARTEMIS_BOT);
		e.addComponent(ssc);

		ShipColorsComp scc = world.createComponent(ShipColorsComp.class);
		scc.setValues(Constants.Player.DEFAULT_COLOR,
				Constants.Player.DEFAULT_COLOR, Constants.Player.DEFAULT_COLOR);

		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
		e.addToWorld();

		return e;
	}

}
