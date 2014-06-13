package main.swapship.systems.render;

import main.swapship.SwapShipGame;
import main.swapship.common.Constants;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.player.ShipColorsComp;
import main.swapship.components.player.ShipSpritesComp;
import main.swapship.util.AssetUtil;
import main.swapship.util.VectorUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerRenderSys extends EntityProcessingSystem {

	// The game, used to get the camera and sritebatch
	private final SwapShipGame game;
	private final OrthographicCamera camera;
	
	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<VelocityComp> vcm;
	private ComponentMapper<ShipColorsComp> sccm;
	private ComponentMapper<ShipSpritesComp> sscm;
	
	public PlayerRenderSys(final SwapShipGame game, OrthographicCamera camera) {
		super(Filter.allComponents(SpatialComp.class, VelocityComp.class, ShipColorsComp.class, ShipSpritesComp.class));
		this.game = game;
		this.camera = camera;
	}
	
	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		sccm = world.getMapper(ShipColorsComp.class);
		sscm = world.getMapper(ShipSpritesComp.class);
		vcm = world.getMapper(VelocityComp.class);
	}

	@Override
	protected void process(Entity e) {
		SpatialComp sc = scm.get(e);
		VelocityComp vc = vcm.get(e);
		ShipColorsComp scc = sccm.get(e);
		ShipSpritesComp ssc = sscm.get(e);
		
		// Textures to draw with
		TextureRegion topTr = AssetUtil.getInstance().getTexture(ssc.topName);
		TextureRegion midTr = AssetUtil.getInstance().getTexture(ssc.midName);
		TextureRegion botTr = AssetUtil.getInstance().getTexture(ssc.botName);
		
		game.batch.setProjectionMatrix(camera.combined);
		float rotation = VectorUtil.calcRotation(vc.xVel, vc.yVel);
		// Draw the parts!
		// Start at the bottom because y goes upward
		game.batch.setColor(scc.botColor);
		game.batch.draw(botTr, sc.x, sc.y, sc.width / 2, sc.height / 2,
				sc.width, sc.height, 1f, 1f, rotation);
		
		game.batch.setColor(scc.midColor);
		game.batch.draw(midTr, sc.x, sc.y, sc.width / 2, sc.height / 2,
				sc.width, sc.height, 1f, 1f, rotation);
		
		game.batch.setColor(scc.topColor);
		game.batch.draw(topTr, sc.x, sc.y, sc.width / 2, sc.height / 2,
				sc.width, sc.height, 1f, 1f, rotation);
	}
}
