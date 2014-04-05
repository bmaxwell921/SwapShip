package main.swapship.util;

import main.swapship.common.Constants;
import main.swapship.common.OffensiveSpecialType;
import main.swapship.components.DamageComp;
import main.swapship.components.FireRateComp;
import main.swapship.components.HealthComp;
import main.swapship.components.LevelComp;
import main.swapship.components.SpatialComp;
import main.swapship.components.TargetComp;
import main.swapship.components.TimeDelComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.diff.PlayerComp;
import main.swapship.components.other.SingleSpriteComp;
import main.swapship.components.player.ShipColorsComp;
import main.swapship.components.player.ShipSpritesComp;
import main.swapship.components.player.SpecialComp;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

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
		vc.setValues(Constants.Player.START_VEL, Constants.Player.START_VEL,
				Constants.Player.MAX_MOVE);
		e.addComponent(vc);

		e.addComponent(world.createComponent(PlayerComp.class));

		// Level
		LevelComp lc = world.createComponent(LevelComp.class);
		lc.setValues(Constants.Player.BASE_PART_LVL,
				Constants.Player.BASE_PART_LVL, Constants.Player.BASE_PART_LVL);
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
		// Testing
		spc.offensive = OffensiveSpecialType.MISSILE;
		spc.offensiveCount = 5;
		e.addComponent(spc);
		
		HealthComp hc = world.createComponent(HealthComp.class);
		hc.health = Constants.Player.BASE_HEALTH;
		e.addComponent(hc);

		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
		e.addToWorld();
		return e;
	}

	public static void createEnemy(World world) {
		Entity e = world.createEntity();

		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(
				MathUtils.random(0, Gdx.graphics.getWidth()
						- Constants.SHIP_WIDTH), Gdx.graphics.getHeight(),
				Constants.SHIP_WIDTH, Constants.SHIP_HEIGHT);
		e.addComponent(sc);

		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.setValues(0, -Constants.Enemy.MAX_MOVE, Constants.Enemy.MAX_MOVE);
		e.addComponent(vc);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Enemy.NAMES[MathUtils
				.random(Constants.Enemy.NAMES.length - 1)];
		ssc.tint = Constants.Enemy.COLORS[MathUtils
				.random(Constants.Enemy.COLORS.length - 1)];
		e.addComponent(ssc);

		DamageComp dc = world.createComponent(DamageComp.class);
		dc.damage = Constants.Enemy.BASE_DAMAGE;
		e.addComponent(dc);

		FireRateComp frc = world.createComponent(FireRateComp.class);
		frc.setValues(Constants.Enemy.FIRE_RATE);
		e.addComponent(frc);

		LevelComp lc = world.createComponent(LevelComp.class);
		lc.setValues(0, 0, 1);
		e.addComponent(lc);
		
		HealthComp hc = world.createComponent(HealthComp.class);
		hc.health = Constants.Enemy.BASE_HEALTH;
		e.addComponent(hc);

		world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMY);
		e.addToWorld();
	}

	public static void createShot(World world, float x, float y, int damage,
			Color tint, boolean playerShot) {
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

		world.getManager(GroupManager.class).add(
				e,
				playerShot ? Constants.Groups.PLAYER_ATTACK
						: Constants.Groups.ENEMY_ATTACK);
		e.addToWorld();
	}

	public static void createOffensiveSpecial(World world,
			OffensiveSpecialType type, float sourceX, float sourceY) {
		if (type == OffensiveSpecialType.NONE) {
			return;
		}

		if (type == OffensiveSpecialType.MISSILE) {
			Array<Entity> targets = findTargets(world, sourceX, sourceY);
			// Create a bunch of missiles
			for (Entity target : targets) {
				// TODO this isn't spawning in the center. Also, they aren't
				// moving
				createMissile(world, sourceX, sourceY,
						target.getComponent(SpatialComp.class));
			}
			return;
		}
	}

	private static void createMissile(World world, float sourceX,
			float sourceY, SpatialComp target) {
		Entity e = world.createEntity();
		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(sourceX - Constants.Missile.WIDTH / 2, sourceY,
				Constants.Missile.WIDTH, Constants.Missile.HEIGHT);
		e.addComponent(sc);

		DamageComp dc = world.createComponent(DamageComp.class);
		dc.damage = Integer.MAX_VALUE; // Max Value so it's an insta-kill
		e.addComponent(dc);

		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.setValues(0, 0, Constants.Missile.VEL);
		e.addComponent(vc);

		TargetComp tc = world.createComponent(TargetComp.class);
		tc.target = target;
		e.addComponent(tc);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Missile.NAME;
		ssc.tint = Color.WHITE;
		e.addComponent(ssc);

		world.getManager(GroupManager.class).add(e,
				Constants.Groups.PLAYER_ATTACK); 
		e.addToWorld();
	}

	public static void createExplosion(World world, float x, float y, float xVel, float yVel) {
		Entity e = world.createEntity();
		
		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(x, y, Constants.Explosion.WIDTH, Constants.Explosion.HEIGHT);
		e.addComponent(sc);
		
		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.setValues(xVel * Constants.Explosion.VEL_PERC, yVel * Constants.Explosion.VEL_PERC, 0);
		e.addComponent(vc);
		
		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Explosion.NAME;
		ssc.tint = Color.WHITE;
		e.addComponent(ssc);
		
		TimeDelComp tdc = world.createComponent(TimeDelComp.class);
		tdc.setValues(Constants.Explosion.LIFE_TIME);
		e.addComponent(tdc);
		
		e.addToWorld();
	}
	
	private static Array<Entity> findTargets(World world, float sourceX,
			float sourceY) {
		return RandUtil.chooseNumFrom(
				Constants.Missile.SPAWN_COUNT,
				world.getManager(GroupManager.class).getEntities(
						Constants.Groups.ENEMY));
	}
}
