package main.swapship;

import main.swapship.common.Constants;
import main.swapship.screens.SelectScreen;
import main.swapship.util.AssetUtil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SwapShipGame extends Game {

	// Settings for the game
	public Settings settings;
	
	// SpriteBatch used to draw images
	public SpriteBatch batch;
	
	// Skin used for all menus
	public Skin skin;

	@Override
	public void create() {
		Constants.Player.MAX_Y = Gdx.graphics.getHeight() / 3;
		this.settings = new Settings();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
//		this.setScreen(new MainScreen(this));
		this.setScreen(new SelectScreen(this));
		AssetUtil.getInstance().setTextureAtlas(new TextureAtlas(Gdx.files.internal("game/gameImages.atlas")));
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
		skin.dispose();
	}
}
