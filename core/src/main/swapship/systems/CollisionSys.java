package main.swapship.systems;

import main.swapship.SwapShipGame;
import main.swapship.common.Constants;
import main.swapship.components.DamageComp;
import main.swapship.components.HealthComp;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.factories.EntityFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntitySystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * System used to handle collisions
 * 
 * Code borrowed from
 * https://code.google.com/p/gamadu-starwarrior/source/browse/
 * src/com/gamadu/starwarrior/systems/CollisionSystem.java
 * 
 * @author Brandon
 * 
 */
public class CollisionSys extends EntitySystem {

	private SwapShipGame game;

	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<VelocityComp> vcm;
	private ComponentMapper<DamageComp> dcm;
	private ComponentMapper<HealthComp> hcm;

	private Array<CollisionGroup> collisionGroups;

	// Used to check collision
	private Rectangle oneRect;
	private Rectangle twoRect;

	public CollisionSys(final SwapShipGame game) {
		super(Filter.allComponents(SpatialComp.class, DamageComp.class));
		this.game = game;
		oneRect = new Rectangle();
		twoRect = new Rectangle();
	}

	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		vcm = world.getMapper(VelocityComp.class);
		dcm = world.getMapper(DamageComp.class);
		hcm = world.getMapper(HealthComp.class);

		setUpCollisionGroups();
	}

	private void setUpCollisionGroups() {
		collisionGroups = new Array<>();
		collisionGroups.add(new CollisionGroup(Constants.Groups.PLAYER_ATTACK,
				Constants.Groups.ENEMY, new EnemyCountCollisionHandler()));
		collisionGroups.add(new CollisionGroup(Constants.Groups.ENEMY_ATTACK,
				Constants.Groups.PLAYER, new BaseCollisionHandler()));
		collisionGroups.add(new CollisionGroup(Constants.Groups.ENEMY, 
				Constants.Groups.PLAYER, new BaseCollisionHandler()));
	}

	@Override
	public void processEntities(Array<Entity> entities) {
		for (CollisionGroup cg : collisionGroups) {
			cg.checkForCollisions();
		}
	}

	private class CollisionGroup {
		private String groupOne;
		private String groupTwo;
		private CollisionHandler handler;

		public CollisionGroup(String groupOne, String groupTwo,
				CollisionHandler handler) {
			this.groupOne = groupOne;
			this.groupTwo = groupTwo;
			this.handler = handler;
		}

		public void checkForCollisions() {
			Array<Entity> groupOne = world.getManager(GroupManager.class)
					.getEntities(this.groupOne);
			Array<Entity> groupTwo = world.getManager(GroupManager.class)
					.getEntities(this.groupTwo);

			for (Entity one : groupOne) {
				for (Entity two : groupTwo) {
					if (collision(one, two)) {
						handler.handle(one, two);
					}
				}
			}
		}

		private boolean collision(Entity one, Entity two) {
			SpatialComp oneSc = scm.get(one);
			SpatialComp twoSc = scm.get(two);
			oneRect.set(oneSc.x, oneSc.y, oneSc.width, oneSc.height);
			twoRect.set(twoSc.x, twoSc.y, twoSc.width, twoSc.height);
			return oneRect.overlaps(twoRect);
		}
	}

	private interface CollisionHandler {
		public void handle(Entity one, Entity two);
	}

	// Basic handler that just takes health from each entity
	public class BaseCollisionHandler implements CollisionHandler {
		public void handle(Entity one, Entity two) {
			removeHealth(one, two);

			// Check removal
			checkAndDelete(one);
			checkAndDelete(two);
		}

		protected void removeHealth(Entity one, Entity two) {
			// Get the comps
			HealthComp oneHc = hcm.get(one);
			HealthComp twoHc = hcm.get(two);
			DamageComp oneDmg = dcm.get(one);
			DamageComp twoDmg = dcm.get(two);

			// Trade damage
			if (oneHc != null && twoDmg != null) {
				oneHc.health -= twoDmg.damage;
			}
			if (twoHc != null && oneDmg != null) {
				twoHc.health -= oneDmg.damage;
			}
		}

		// Checks whether the entity needs to be deleted and does so if
		// necessary
		protected boolean checkAndDelete(Entity e) {
			HealthComp ehc = hcm.get(e);
			if (ehc.health <= 0) {
				if (ehc.killable) {
					SpatialComp sc = scm.get(e);
					VelocityComp vc = vcm.get(e);
					EntityFactory.createExplosion(world, sc.x, sc.y, vc.xVel,
							vc.yVel);
				}
				e.deleteFromWorld();
				world.getManager(GroupManager.class).removeFromAllGroups(e);
				return true;
			}
			return false;
		}
	}

	// Collision handler that updates the score and kill count
	public class EnemyCountCollisionHandler extends BaseCollisionHandler {
		@Override
		public void handle(Entity playerGroup, Entity enemyGroup) {
			// Remove health
			super.removeHealth(playerGroup, enemyGroup);
			// Remove the player thing if necessary
			checkAndDelete(playerGroup);

			// Remove the enemy if necessary and count it
			if (checkAndDelete(enemyGroup)) {
				++game.gameInfo.killCount;
				// TODO actual score
				game.gameInfo.score += 50;
			}
		}
	}
}
