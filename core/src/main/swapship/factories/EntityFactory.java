package main.swapship.factories;

import main.swapship.GameInfo.Level;
import main.swapship.common.Constants;
import main.swapship.common.DefensiveSpecialType;
import main.swapship.common.OffensiveSpecialType;
import main.swapship.components.DamageComp;
import main.swapship.components.FireRateComp;
import main.swapship.components.HealthComp;
import main.swapship.components.LevelComp;
import main.swapship.components.NonCullComp;
import main.swapship.components.SpatialComp;
import main.swapship.components.TargetComp;
import main.swapship.components.TimeDelComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.other.PathFollowComp;
import main.swapship.components.other.PathTargetComp;
import main.swapship.components.other.SingleSpriteComp;
import main.swapship.components.player.MoveWithPlayerComp;
import main.swapship.components.player.ShipColorsComp;
import main.swapship.components.player.ShipSpritesComp;
import main.swapship.components.player.SpecialComp;
import main.swapship.components.types.PlayerComp;
import main.swapship.util.GameUtil;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class EntityFactory {

	private static final int NO_HEALTH = -1;

	// Creates a basic ship with a location, velocity, and health
	private static Entity createBasicEntity(World world, float x, float y,
			int width, int height, float vx, float vy, float vmax, int health) {
		Entity e = world.createEntity();

		// Location data
		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(x, y, width, height);
		e.addComponent(sc);

		// Velocity data
		VelocityComp vc = world.createComponent(VelocityComp.class);
		vc.setValues(vx, vy, vmax);
		e.addComponent(vc);

		// Health data
		if (health != NO_HEALTH) {
			HealthComp hc = world.createComponent(HealthComp.class);
			hc.health = health;
			e.addComponent(hc);
		}

		return e;
	}

	// Adds levels, damage, and fire rate to an entity
	private static Entity addShipOffense(World world, Entity e, int topL,
			int midL, int botL, int baseDmg, float fireRate) {
		// Level data
		LevelComp lc = world.createComponent(LevelComp.class);
		lc.setValues(topL, midL, botL);
		e.addComponent(lc);

		DamageComp dc = world.createComponent(DamageComp.class);
		// TODO better damage calculation??
		dc.damage = baseDmg;
		e.addComponent(dc);

		FireRateComp frc = world.createComponent(FireRateComp.class);
		frc.setValues(fireRate);
		e.addComponent(frc);
		
		return e;
	}

	/**
	 * Creates the player, returning so other classes can have easy access to it
	 * 
	 * @param world
	 * @return
	 */
	public static Entity createPlayer(World world) {
		Entity e = createBasicEntity(world, Gdx.graphics.getWidth() / 2
				- Constants.SHIP_WIDTH / 2, Constants.Player.MIN_Y,
				Constants.SHIP_WIDTH, Constants.SHIP_HEIGHT,
				Constants.Player.START_VEL, Constants.Player.START_VEL,
				Constants.Player.MAX_MOVE, Constants.Player.BASE_HEALTH);

		addShipOffense(world, e, Constants.Player.BASE_PART_LVL,
				Constants.Player.BASE_PART_LVL, Constants.Player.BASE_PART_LVL,
				Constants.Player.BASE_DAMAGE, Constants.Player.FIRE_RATE);

		// Distinguishing comp
		e.addComponent(world.createComponent(PlayerComp.class));

		// Drawing
		ShipSpritesComp ssc = world.createComponent(ShipSpritesComp.class);
		ssc.setValues(Constants.Player.ARTEMIS_TOP,
				Constants.Player.ARTEMIS_MID, Constants.Player.ARTEMIS_BOT);
		e.addComponent(ssc);

		ShipColorsComp scc = world.createComponent(ShipColorsComp.class);
		scc.setValues(Color.GREEN, Color.RED, Color.BLUE);
		e.addComponent(scc);

		// Testing values
		SpecialComp spc = world.createComponent(SpecialComp.class);
		spc.defensive = DefensiveSpecialType.INVINCIBLITY;
		spc.defensiveCount = Integer.MAX_VALUE;

		spc.offensive = OffensiveSpecialType.BEAM;
		spc.offensiveCount = Integer.MAX_VALUE;
		e.addComponent(spc);

		// Deletion is handled separately
		e.addComponent(world.createComponent(NonCullComp.class));

		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
		e.addToWorld();
		return e;
	}

	public static void createEnemy(World world) {
		Entity e = createBasicEntity(
				world,
				MathUtils.random(0, Gdx.graphics.getWidth()
						- Constants.SHIP_WIDTH), Gdx.graphics.getHeight(),
				Constants.SHIP_WIDTH, Constants.SHIP_HEIGHT, 0,
				-Constants.Enemy.MAX_MOVE, Constants.Enemy.MAX_MOVE,
				Constants.Enemy.BASE_HEALTH);
		addShipOffense(world, e, 0, 0, 1, Constants.Enemy.BASE_DAMAGE,
				Constants.Enemy.FIRE_RATE);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Enemy.NAMES[MathUtils
				.random(Constants.Enemy.NAMES.length - 1)];
		ssc.tint = Color.GRAY;
		e.addComponent(ssc);

		// Path following ai
		PathFollowComp pfc = world.createComponent(PathFollowComp.class);
		pfc.setValues(PathFactory.createPath());
		e.addComponent(pfc);

		PathTargetComp tc = world.createComponent(PathTargetComp.class);
		tc.target = pfc.path.get(pfc.target);
		e.addComponent(tc);

		// Enemies are deleted when they get to the end of the path
		e.addComponent(world.createComponent(NonCullComp.class));

		world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMY);
		e.addToWorld();
	}

	public static void createBoss(World world, Level level) {
		Entity e = createBasicEntity(
				world,
				MathUtils.random(0, Gdx.graphics.getWidth()
						- 144), Gdx.graphics.getHeight() - 108, 144,
				108, 0, 0, 0,
				Constants.Enemy.BASE_HEALTH);
		addShipOffense(world, e, 0, 0, 1, Constants.Enemy.BASE_DAMAGE,
				Constants.Enemy.FIRE_RATE);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = "Boss1_V2";
		ssc.tint = level.tint;
		e.addComponent(ssc);

		e.addComponent(world.createComponent(NonCullComp.class));

		world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMY);
		e.addToWorld();
	}

	public static void createShot(World world, float x, float y, int damage,
			Color tint, boolean playerShot) {
		Entity e = createBasicEntity(world, x, y, Constants.Shot.WIDTH,
				Constants.Shot.HEIGHT, 0, Constants.Shot.VEL
						* ((playerShot) ? 1 : -1), 0, NO_HEALTH);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Shot.SHOT_NAME;
		ssc.tint = tint;
		e.addComponent(ssc);

		DamageComp dc = world.createComponent(DamageComp.class);
		dc.damage = damage;
		e.addComponent(dc);

		world.getManager(GroupManager.class).add(
				e,
				playerShot ? Constants.Groups.PLAYER_ATTACK
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
		// TODO changed sourceX and sourceY to be bottom left corner of player
		if (type == OffensiveSpecialType.NONE) {
			return false;
		}

		if (type == OffensiveSpecialType.MISSILE) {
			return createMissiles(world, sourceX, sourceY);
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

		if (type == DefensiveSpecialType.INVINCIBLITY) {
			createInvincibility(world, sourceX, sourceY);
		}
		return true;

	}

	private static boolean createMissiles(World world, float sourceX,
			float sourceY) {
		Array<Entity> targets = GameUtil.findRandTargets(world,
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

	private static void createMissile(World world, float sourceX,
			float sourceY, Entity target) {
		Entity e = createBasicEntity(world, sourceX + Constants.SHIP_WIDTH / 2
				- Constants.Missile.WIDTH / 2, sourceY + Constants.SHIP_HEIGHT,
				Constants.Missile.WIDTH, Constants.Missile.HEIGHT, 0, 0,
				Constants.Missile.VEL, NO_HEALTH);

		DamageComp dc = world.createComponent(DamageComp.class);
		dc.damage = Integer.MAX_VALUE; // Max Value so it's an insta-kill
		e.addComponent(dc);

		TargetComp tc = world.createComponent(TargetComp.class);
		tc.target = target;
		tc.targetGroup = Constants.Groups.ENEMY;
		e.addComponent(tc);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = Constants.Missile.NAME;
		ssc.tint = Color.WHITE;
		e.addComponent(ssc);

		world.getManager(GroupManager.class).add(e,
				Constants.Groups.PLAYER_ATTACK);
		e.addToWorld();
	}

	private static void createBeam(World world, float srcX, float srcY) {
		// Beams are actually just a bunch of beams
		int numBeams = Gdx.graphics.getHeight() / Constants.Beam.HEIGHT + 1;
		float y = srcY + Constants.SHIP_HEIGHT;
		float x = srcX + Constants.SHIP_WIDTH / 2 - Constants.Beam.WIDTH / 2;
		GroupManager gm = world.getManager(GroupManager.class);
		for (int i = 0; i < numBeams; ++i) {
			Entity e = world.createEntity();
			gm.removeFromAllGroups(e);

			MoveWithPlayerComp mwpc = world
					.createComponent(MoveWithPlayerComp.class);
			mwpc.xDisplace = Constants.SHIP_WIDTH / 2 - Constants.Beam.WIDTH
					/ 2;
			mwpc.yDispace = Constants.SHIP_HEIGHT + Constants.Beam.HEIGHT * i;
			e.addComponent(mwpc);

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

			// Beams are removed after a certain amount of time, so don't cull
			// them
			e.addComponent(world.createComponent(NonCullComp.class));

			TimeDelComp tdc = world.createComponent(TimeDelComp.class);
			tdc.setValues(Constants.Beam.TIME_OUT);
			e.addComponent(tdc);

			gm.add(e, Constants.Groups.PLAYER_ATTACK);
			e.addToWorld();

			y += Constants.Beam.HEIGHT;
		}
	}

	// Method to consolidate shield and invincibility code
	private static Entity createBubble(World world, float sourceX,
			float sourceY, int bubWidth, int bubHeight, String imgName,
			int health) {
		Entity e = world.createEntity();

		MoveWithPlayerComp mwpc = world
				.createComponent(MoveWithPlayerComp.class);
		mwpc.xDisplace = -(bubWidth - Constants.SHIP_WIDTH) / 2;
		mwpc.yDispace = -(bubHeight - Constants.SHIP_HEIGHT) / 2;
		e.addComponent(mwpc);

		SpatialComp sc = world.createComponent(SpatialComp.class);
		sc.setValues(sourceX + mwpc.xDisplace, sourceY + mwpc.yDispace,
				bubWidth, bubHeight);
		e.addComponent(sc);

		SingleSpriteComp ssc = world.createComponent(SingleSpriteComp.class);
		ssc.name = imgName;
		ssc.tint = Color.WHITE;
		e.addComponent(ssc);

		HealthComp hc = world.createComponent(HealthComp.class);
		hc.health = health;
		e.addComponent(hc);

		e.addComponent(world.createComponent(NonCullComp.class));
		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
		return e;
	}

	private static void createShield(World world, float sourceX, float sourceY) {
		createBubble(world, sourceX, sourceY, Constants.Shield.WIDTH,
				Constants.Shield.HEIGHT, Constants.Shield.NAME,
				Constants.Shield.HEALTH).addToWorld();
	}

	private static void createInvincibility(World world, float sourceX,
			float sourceY) {
		Entity e = createBubble(world, sourceX, sourceY,
				Constants.Invincibility.WIDTH, Constants.Invincibility.HEIGHT,
				Constants.Invincibility.NAME, Constants.Invincibility.HEALTH);

		TimeDelComp tdc = world.createComponent(TimeDelComp.class);
		tdc.setValues(Constants.Invincibility.TIME_OUT);
		e.addComponent(tdc);

		e.addToWorld();
	}

	public static void createExplosion(World world, float x, float y,
			float xVel, float yVel) {
		Entity e = createBasicEntity(world, x, y,
				Constants.Explosion.WIDTH, Constants.Explosion.HEIGHT, xVel
						* Constants.Explosion.VEL_PERC, yVel
						* Constants.Explosion.VEL_PERC, 0, NO_HEALTH);

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
