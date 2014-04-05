package main.swapship.systems;

import main.swapship.SwapShipGame;
import main.swapship.common.Constants;
import main.swapship.components.ShipColorsComp;
import main.swapship.components.ShipSpritesComp;
import main.swapship.components.SpatialComp;
import main.swapship.util.AssetUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderSys extends EntityProcessingSystem {

	// The game, used to get the camera and sritebatch
	private final SwapShipGame game;
	private final OrthographicCamera camera;
	
	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<ShipColorsComp> sccm;
	private ComponentMapper<ShipSpritesComp> sscm;
	
	public RenderSys(final SwapShipGame game, OrthographicCamera camera) {
		super(Filter.allComponents(SpatialComp.class, ShipColorsComp.class, ShipSpritesComp.class));
		this.game = game;
		this.camera = camera;
	}
	
	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		sccm = world.getMapper(ShipColorsComp.class);
		sscm = world.getMapper(ShipSpritesComp.class);
	}

	@Override
	protected void process(Entity e) {
		SpatialComp sc = scm.get(e);
		ShipColorsComp scc = sccm.get(e);
		ShipSpritesComp ssc = sscm.get(e);
		
		// Textures to draw with
		TextureRegion topTr = AssetUtil.getInstance().getTexture(ssc.topName);
		TextureRegion midTr = AssetUtil.getInstance().getTexture(ssc.midName);
		TextureRegion botTr = AssetUtil.getInstance().getTexture(ssc.botName);
		
		game.batch.setProjectionMatrix(camera.combined);
		
		float y = sc.y;
		// Draw the parts!
		// Start at the bottom because y goes upward
		game.batch.setColor(scc.botColor);
		game.batch.draw(botTr, sc.x, y, sc.width / 2, Constants.Player.SHIP_PART_HEIGHT / 2,
				sc.width, Constants.Player.SHIP_PART_HEIGHT, 1f, 1f, 0);
		y += Constants.Player.SHIP_PART_HEIGHT;
		
		game.batch.setColor(scc.midColor);
		game.batch.draw(midTr, sc.x, y, sc.width / 2, Constants.Player.SHIP_PART_HEIGHT / 2,
				sc.width, Constants.Player.SHIP_PART_HEIGHT, 1f, 1f, 0);
		y += Constants.Player.SHIP_PART_HEIGHT;
		
		game.batch.setColor(scc.topColor);
		game.batch.draw(topTr, sc.x, y, sc.width / 2, Constants.Player.SHIP_PART_HEIGHT / 2,
				sc.width, Constants.Player.SHIP_PART_HEIGHT, 1f, 1f, 0);
	}
}
