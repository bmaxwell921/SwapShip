package main.swapship;

import main.swapship.common.Constants;
import main.swapship.screens.GameScreen;
import main.swapship.util.AssetUtil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SwapShipGame extends Game {

	public SpriteBatch batch;

	@Override
	public void create() {
		Constants.Player.MAX_Y = Gdx.graphics.getHeight() / 3;
		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this));
		
		AssetUtil.getInstance().setTextureAtlas(new TextureAtlas(Gdx.files.internal("gameImages.atlas")));
	}

	@Override
	public void render() {
		batch.begin();
		super.render();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
