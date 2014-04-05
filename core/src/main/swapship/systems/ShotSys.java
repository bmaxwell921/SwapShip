package main.swapship.systems;

import main.swapship.common.Constants;
import main.swapship.components.DamageComp;
import main.swapship.components.FireRateComp;
import main.swapship.components.ShipColorsComp;
import main.swapship.components.SingleSpriteComp;
import main.swapship.components.SpatialComp;
import main.swapship.components.dist.PlayerComp;
import main.swapship.util.EntityFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;

public class ShotSys extends EntityProcessingSystem {

	private ComponentMapper<FireRateComp> frcm;
	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<DamageComp> dcm;
	
	private ComponentMapper<PlayerComp> pcm;
	private ComponentMapper<SingleSpriteComp> sscm;
	private ComponentMapper<ShipColorsComp> sccm;
	
	public ShotSys() {
		super(Filter.allComponents(FireRateComp.class, SpatialComp.class, DamageComp.class));
	}
	
	@Override
	public void initialize() {
		frcm = world.getMapper(FireRateComp.class);
		scm = world.getMapper(SpatialComp.class);
		pcm = world.getMapper(PlayerComp.class);
		dcm = world.getMapper(DamageComp.class);
		sscm = world.getMapper(SingleSpriteComp.class);
		sccm = world.getMapper(ShipColorsComp.class);
	}
	
	@Override
	public void process(Entity e) {
		FireRateComp frc = frcm.get(e);
		
		frc.timeLeft -= world.getDelta();
		
		if (frc.timeLeft <= 0) {
			spawnShot(e);
			frc.timeLeft = frc.startTime;
		}
	}
	
	private void spawnShot(Entity e) {
		SpatialComp sc = scm.get(e);
		DamageComp dc = dcm.get(e);
		boolean isPlayer = pcm.getSafe(e) != null;
		
		// X location should be right in the middle of whoever shot it
		float x = sc.x + sc.width / 2 - Constants.Shot.WIDTH / 2;
		
		// The y is either above the ship for players, or below for enemies
		float y = sc.y + ((isPlayer) ? sc.height : 0);
		EntityFactory.createShot(world, x, y, dc.damage, getColor(e, isPlayer), isPlayer);
	}
	
	// Gets the color of the shot, based on whether it is a player or not
	private Color getColor(Entity e, boolean isPlayer) {
		if (isPlayer) {
			return sccm.get(e).topColor;
		}
		return sscm.get(e).tint;
	}
}
