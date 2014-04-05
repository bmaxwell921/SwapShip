package main.swapship.util;

import main.swapship.common.Constants;
import main.swapship.common.OffensiveSpecialType;
import main.swapship.components.DamageComp;
import main.swapship.components.FireRateComp;
import main.swapship.components.LevelComp;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.dist.PlayerComp;
import main.swapship.components.other.SingleSpriteComp;
import main.swapship.components.player.ShipColorsComp;
import main.swapship.components.player.ShipSpritesComp;
import main.swapship.components.player.SpecialComp;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class EntityFactory {

	/**
	 * Creates the player, returning so other classes can have easy access to it
	 * 
	 * @param world
	 * @return
	 */
	public static Entity createPlayer(World world) {
		Entity e = world.createEntity();
		
		// Location
		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(Gdx.graphics.getWidth() / 2 - Constants.SHIP_WIDTH / 2,
				Constants.Player.MIN_Y, Constants.SHIP_WIDTH,
				Constants.SHIP_HEIGHT);
		e.addComponent(sc);

		// Drawing
		ShipSpritesComp ssc = world.createComponent(ShipSpritesComp.class);
		ssc.setValues(Constants.Player.ARTEMIS_TOP,
				Constants.Player.ARTEMIS_MID, Constants.Player.ARTEMIS_BOT);
		e.addComponent(ssc);

		ShipColorsComp scc = world.createComponent(ShipColorsComp.class);
		scc.setValues(Constants.Player.DEFAULT_COLOR,
				Constants.Player.DEFAULT_COLOR, Constants.Player.DEFAULT_COLOR);
		e.addComponent(scc);

		// Movement
		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.setValues(Constants.Player.START_VEL, Constants.Player.START_VEL);
		e.addComponent(vc);

		e.addComponent(world.createComponent(PlayerComp.class));

		// Level
		LevelComp lc = world.createComponent(LevelComp.class);
		lc.setValues(Constants.Player.BASE_PART_LVL,
				Constants.Player.BASE_PART_LVL, Constants.Player.BASE_PART_LVL,
				Constants.Player.BASE_DAMAGE);
		e.addComponent(lc);
		
		// Attacking
		DamageComp dc = world.createComponent(DamageComp.class);
		dc.damage = lc.calcDamage(Constants.Player.BASE_DAMAGE);
		e.addComponent(dc);
		
		FireRateComp frc = world.createComponent(FireRateComp.class);
		frc.startTime = Constants.Player.FIRE_RATE;
		frc.timeLeft = 0;
		e.addComponent(frc);
		
		SpecialComp spc = world.createComponent(SpecialComp.class);
		e.addComponent(spc);

		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
		e.addToWorld();
		return e;
	}

	public static void createShot(World world, float x, float y, int damage, Color tint,
			boolean playerShot) {
		Entity e = world.createEntity();
		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(x, y, Constants.Shot.WIDTH, Constants.Shot.HEIGHT);
		e.addComponent(sc);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Shot.SHOT_NAME;
		ssc.tint = tint;
		e.addComponent(ssc);

		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.yVel = Constants.Shot.VEL * ((playerShot) ? 1 : -1);
		e.addComponent(vc);
		
		DamageComp dc = world.createComponent(DamageComp.class);
		dc.damage = damage;
		e.addComponent(dc);

		world.getManager(GroupManager.class).add(e, playerShot ? Constants.Groups.PLAYER_ATTACK
						: Constants.Groups.ENEMY_ATTACK);
		e.addToWorld();
	}

}
