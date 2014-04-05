package main.swapship.systems;

import main.swapship.SwapShipGame;
import main.swapship.components.SpatialComp;
import main.swapship.components.other.SingleSpriteComp;
import main.swapship.util.AssetUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SingleSpriteRenderSys extends EntityProcessingSystem {

	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<SingleSpriteComp> sscm;

	private final SwapShipGame game;
	private OrthographicCamera camera;

	public SingleSpriteRenderSys(final SwapShipGame game,
			OrthographicCamera camera) {
		super(Filter.allComponents(SpatialComp.class, SingleSpriteComp.class));
		this.game = game;
		this.camera = camera;
	}

	@Override
	public void initialize() {
		scm = world.getMapper(SpatialComp.class);
		sscm = world.getMapper(SingleSpriteComp.class);
	}

	@Override
	public void process(Entity e) {
		SpatialComp sc = scm.get(e);
		SingleSpriteComp ssc = sscm.get(e);

		TextureRegion tr = AssetUtil.getInstance().getTexture(ssc.name);
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.setColor(ssc.tint);
		game.batch.draw(tr, sc.x, sc.y, sc.width / 2, sc.height / 2, sc.width,
				sc.height, 1, 1, 0);
	}
}
