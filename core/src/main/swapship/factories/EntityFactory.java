package main.swapship.factories;

import main.swapship.common.Constants;
import main.swapship.common.DefensiveSpecialType;
import main.swapship.common.OffensiveSpecialType;
import main.swapship.components.DamageComp;
import main.swapship.components.FireRateComp;
import main.swapship.components.HealthComp;
import main.swapship.components.LevelComp;
import main.swapship.components.SpatialComp;
import main.swapship.components.TargetComp;
import main.swapship.components.TimeDelComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.diff.BeamComp;
import main.swapship.components.diff.PlayerComp;
import main.swapship.components.other.PathFollowComp;
import main.swapship.components.other.PathTargetComp;
import main.swapship.components.other.SingleSpriteComp;
import main.swapship.components.player.MoveWithPlayerComp;
import main.swapship.components.player.ShipColorsComp;
import main.swapship.components.player.ShipSpritesComp;
import main.swapship.components.player.SpecialComp;
import main.swapship.util.TargetUtil;

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

		GroupManager gm = world.getManager(GroupManager.class);
		gm.removeFromAllGroups(e);

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
		scc.setValues(Color.GREEN, Color.RED, Color.BLUE);
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
		spc.defensive = DefensiveSpecialType.SHIELD;
		spc.defensiveCount = Integer.MAX_VALUE;
		e.addComponent(spc);

		HealthComp hc = world.createComponent(HealthComp.class);
		hc.health = Constants.Player.BASE_HEALTH;
		e.addComponent(hc);

		gm.add(e, Constants.Groups.PLAYER);
		e.addToWorld();
		return e;
	}

	public static void createEnemy(World world) {
		Entity e = world.createEntity();

		GroupManager gm = world.getManager(GroupManager.class);
		gm.removeFromAllGroups(e);

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

		PathFollowComp pfc = world.createComponent(PathFollowComp.class);
		pfc.setValues(PathFactory.createPath());
		e.addComponent(pfc);

		PathTargetComp tc = world.createComponent(PathTargetComp.class);
		tc.target = pfc.path.get(pfc.target);
		e.addComponent(tc);

		gm.add(e, Constants.Groups.ENEMY);
		e.addToWorld();
	}

	public static void createShot(World world, float x, float y, int damage,
			Color tint, boolean playerShot) {
		Entity e = world.createEntity();

		GroupManager gm = world.getManager(GroupManager.class);
		gm.removeFromAllGroups(e);

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

		gm.add(e, playerShot ? Constants.Groups.PLAYER_ATTACK
				: Constants.Groups.ENEMY_ATTACK);
		e.addToWorld();
	}

	/**
	 * Returns whether entity was created or not
	 * 
	 * @param world
	 * @param type
	 * @param sourceX
	 * @param sourceY
	 * @return
	 */
	public static boolean createOffensiveSpecial(World world,
			OffensiveSpecialType type, float sourceX, float sourceY) {
		if (type == OffensiveSpecialType.NONE) {
			return false;
		}

		if (type == OffensiveSpecialType.MISSILE) {
			Array<Entity> targets = TargetUtil.findRandTargets(world,
					Constants.Groups.ENEMY, Constants.Missile.SPAWN_COUNT);
			// Create a bunch of missiles
			if (targets == null) {
				return false;
			}
			for (Entity target : targets) {
				createMissile(world, sourceX, sourceY, target);
			}
			return true;
		}

		if (type == OffensiveSpecialType.BEAM) {
			createBeam(world, sourceX, sourceY);
		}
		return true;
	}

	public static boolean createDefensiveSpecial(World world,
			DefensiveSpecialType type, float sourceX, float sourceY) {
		if (type == DefensiveSpecialType.NONE) {
			return false;
		}

		if (type == DefensiveSpecialType.SHIELD) {
			createShield(world, sourceX, sourceY);
		}
		return true;

	}

	private static void createMissile(World world, float sourceX,
			float sourceY, Entity target) {
		Entity e = world.createEntity();

		GroupManager gm = world.getManager(GroupManager.class);
		gm.removeFromAllGroups(e);

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
		tc.targetGroup = Constants.Groups.ENEMY;
		e.addComponent(tc);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Missile.NAME;
		ssc.tint = Color.WHITE;
		e.addComponent(ssc);

		gm.add(e, Constants.Groups.PLAYER_ATTACK);
		e.addToWorld();
	}

	private static void createBeam(World world, float srcX, float srcY) {
		// Beams are actually just a bunch of beams
		int numBeams = Gdx.graphics.getHeight() / Constants.Beam.HEIGHT + 1;
		float y = srcY;
		float x = srcX - Constants.Beam.WIDTH / 2;
		GroupManager gm = world.getManager(GroupManager.class);
		for (int i = 0; i < numBeams; ++i) {
			Entity e = world.createEntity();
			gm.removeFromAllGroups(e);

			SpatialComp sc = world.createComponent(SpatialComp.class);
			sc.setValues(x, y, Constants.Beam.WIDTH, Constants.Beam.HEIGHT);
			e.addComponent(sc);

			SingleSpriteComp ssc = world
					.createComponent(SingleSpriteComp.class);
			ssc.name = Constants.Beam.NAME;
			ssc.tint = Color.WHITE;
			e.addComponent(ssc);

			DamageComp dc = world.createComponent(DamageComp.class);
			dc.damage = Constants.Beam.BASE_DAMAGE;
			e.addComponent(dc);

			HealthComp hc = world.createComponent(HealthComp.class);
			hc.health = Constants.Beam.HEALTH;
			e.addComponent(hc);

			// Add the player comp so it moves as we move
			BeamComp bc = world.createComponent(BeamComp.class);
			bc.num = i;
			e.addComponent(bc);

			TimeDelComp tdc = world.createComponent(TimeDelComp.class);
			tdc.setValues(Constants.Beam.TIME_OUT);
			e.addComponent(tdc);

			gm.add(e, Constants.Groups.PLAYER_ATTACK);
			e.addToWorld();

			y += Constants.Beam.HEIGHT;
		}
	}

	private static void createShield(World world, float sourceX, float sourceY) {
		Entity e = world.createEntity();

		MoveWithPlayerComp mwpc = world
				.createComponent(MoveWithPlayerComp.class);
		mwpc.xDisplace = -(Constants.Shield.WIDTH - Constants.SHIP_WIDTH) / 2;
		mwpc.yDispace = -(Constants.Shield.HEIGHT - Constants.SHIP_HEIGHT) / 2;
		e.addComponent(mwpc);

		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(sourceX + mwpc.xDisplace, sourceY + mwpc.yDispace,
				Constants.Shield.WIDTH, Constants.Shield.HEIGHT);
		e.addComponent(sc);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Shield.NAME;
		ssc.tint = Color.WHITE;
		e.addComponent(ssc);

		HealthComp hc = world.createComponent(HealthComp.class);
		hc.health = Constants.Shield.HEALTH;
		e.addComponent(hc);

		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
		e.addToWorld();
	}

	public static void createExplosion(World world, float x, float y,
			float xVel, float yVel) {
		Entity e = world.createEntity();

		GroupManager gm = world.getManager(GroupManager.class);
		gm.removeFromAllGroups(e);

		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(x, y, Constants.Explosion.WIDTH,
				Constants.Explosion.HEIGHT);
		e.addComponent(sc);

		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.setValues(xVel * Constants.Explosion.VEL_PERC, yVel
				* Constants.Explosion.VEL_PERC, 0);
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

}
