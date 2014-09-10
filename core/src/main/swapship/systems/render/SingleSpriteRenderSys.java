package main.swapship.systems.render;

import main.swapship.SwapShipGame;
import main.swapship.components.SpatialComp;
import main.swapship.components.VelocityComp;
import main.swapship.components.other.SingleSpriteComp;
import main.swapship.util.AssetUtil;
import main.swapship.util.GameUtil;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SingleSpriteRenderSys extends EntityProcessingSystem {

	private ComponentMapper<SpatialComp> scm;
	private ComponentMapper<VelocityComp> vcm;
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
		vcm = world.getMapper(VelocityComp.class);
	}

	@Override
	public void process(Entity e) {
		SpatialComp sc = scm.get(e);
		VelocityComp vc = vcm.getSafe(e);
		
		SingleSpriteComp ssc = sscm.get(e);

		float rotation = vc != null ? GameUtil.calcRotation(vc.xVel, vc.yVel) : 0;
		TextureRegion tr = AssetUtil.getInstance().getTexture(ssc.name);
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.setColor(ssc.tint);
		game.batch.draw(tr, sc.x, sc.y, sc.width / 2, sc.height / 2, sc.width,
				sc.height, 1, 1, rotation);
	}
}
