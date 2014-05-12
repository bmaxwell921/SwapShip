package main.swapship.screens;

import main.swapship.SwapShipGame;
import main.swapship.common.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainScreen implements Screen {

	// THE GAME. Holds skin info
	private final SwapShipGame game;
	
	private Stage stage;
	
	public MainScreen(final SwapShipGame game) {
		this.game = game;
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		createMenu();
	}
	
	private void createMenu() {
		// Table for everything to go in
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.debug();
		
		final Label title = new Label("SWAPSHIP", game.skin, Constants.UI.TITLE_LABEL);
		table.add(title);
		table.row();
		
		// Buttons to other menus
		final TextButton PLAY = new TextButton("PLAY", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(PLAY);
		PLAY.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game));
			}
		});
		
		table.row();
		
		final TextButton SETTINGS = new TextButton("SETTINGS", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(SETTINGS);
		
		table.row();
		
		final TextButton HELP = new TextButton("HELP", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(HELP);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {	
		stage.dispose();
	}

}
