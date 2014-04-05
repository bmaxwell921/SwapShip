package main.swapship.systems;

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
import com.badlogic.gdx.Gdx;
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

	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<VelocityComp> vcm;
	private ComponentMapper<DamageComp> dcm;
	private ComponentMapper<HealthComp> hcm;

	private Array<CollisionGroup> collisionGroups;

	// Used to check collision
	private Rectangle oneRect;
	private Rectangle twoRect;

	public CollisionSys() {
		super(Filter.allComponents(SpatialComp.class, DamageComp.class));
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
				Constants.Groups.ENEMY, new CollisionHandler() {

					// We deal damage to the enemy and destroy the shot.
					// One is a shot, two is an enemy.
					@Override
					public void handle(Entity one, Entity two) {
						HealthComp enemyHp = hcm.get(two);
						VelocityComp enemyVel = vcm.get(two);
						SpatialComp enemyLoc = scm.get(two);
						DamageComp shotDmg = dcm.get(one);

						enemyHp.health -= shotDmg.damage;
						if (enemyHp.health <= 0) {
							EntityFactory.createExplosion(world, enemyLoc.x,
									enemyLoc.y, enemyVel.xVel, enemyVel.yVel);
							two.deleteFromWorld();
						}
						HealthComp hc = hcm.getSafe(one);
						// If it has no health, it's a bullet so remove
						if (hc == null) {
							one.deleteFromWorld(); // Remove the bullet
						}
						
						// Otherwise it's some kind of special so let it go
					}
				}));
		collisionGroups.add(new CollisionGroup(Constants.Groups.ENEMY_ATTACK,
				Constants.Groups.PLAYER, new CollisionHandler() {

					// TODO this will need to change because it's game over when
					// the player is dead
					@Override
					public void handle(Entity one, Entity two) {
						HealthComp playerHp = hcm.get(two);
						VelocityComp playerVel = vcm.get(two);
						SpatialComp playerLoc = scm.get(two);
						DamageComp shotDmg = dcm.get(one);

						playerHp.health -= shotDmg.damage;
						if (playerHp.health <= 0) {
							EntityFactory
									.createExplosion(world, playerLoc.x,
											playerLoc.y, playerVel.xVel,
											playerVel.yVel);
							two.deleteFromWorld();
						}
						one.deleteFromWorld(); // Remove the bullet
					}
				}));
		collisionGroups.add(new CollisionGroup(Constants.Groups.ENEMY,
				Constants.Groups.PLAYER, new CollisionHandler() {

					// One is the enemy, two is the player
					@Override
					public void handle(Entity one, Entity two) {
						HealthComp playerHp = hcm.get(two);
						VelocityComp playerVel = vcm.get(two);
						SpatialComp playerLoc = scm.get(two);
						DamageComp enemyDmg = dcm.get(one);

						playerHp.health -= enemyDmg.damage;
						if (playerHp.health <= 0) {
							EntityFactory
									.createExplosion(world, playerLoc.x,
											playerLoc.y, playerVel.xVel,
											playerVel.yVel);
							two.deleteFromWorld();
						}
						VelocityComp enemyVel = vcm.get(one);
						SpatialComp enemyLoc = scm.get(one);
						EntityFactory.createExplosion(world, enemyLoc.x,
								enemyLoc.y, enemyVel.xVel, enemyVel.yVel);
						one.deleteFromWorld(); // Remove the enemy
					}

				}));
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
}
