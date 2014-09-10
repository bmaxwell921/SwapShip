package main.swapship.screens;

import main.swapship.SwapShipGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Abstract screen for SwapShip. Creates stage to add
 * Actors to
 * @author Brandon
 *
 */
public abstract class AbstractScreen implements Screen {
	
	// The game
	protected final SwapShipGame game;
	
	// Stage to add actors to
	protected Stage stage;
	
	protected AbstractScreen(final SwapShipGame game) {
		this.game = game;
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		setUp();
	}
	
	protected abstract void setUp();
	
	@Override
	public void render(float delta) {
		// Clear the screen and draw the stage
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void show() {
		// TODO do anything?
	}
	
	@Override
	public void hide() {
		// TODO do anything?
	}
	
	@Override
	public void pause() {
		// TODO do anything?
	}
	
	@Override
	public void resume() {
		// TODO do anything?
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}
