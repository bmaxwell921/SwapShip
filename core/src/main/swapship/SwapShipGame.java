package main.swapship;

import main.swapship.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SwapShipGame extends Game {

	public SpriteBatch batch;
	public BitmapFont titleFont;
	public BitmapFont optionFont;

	@Override
	public void create() {
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		titleFont.scale(2f);
		optionFont = new BitmapFont();
		this.setScreen(new GameScreen(this));
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
		titleFont.dispose();
		optionFont.dispose();
	}
}
