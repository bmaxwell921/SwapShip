package main.swapship.screens;

import main.swapship.SwapShipGame;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {

	// The game
	private final SwapShipGame game;
	
	private OrthographicCamera camera;
	
	// Artemis world
	private World world;
	
	public GameScreen(final SwapShipGame game) {
		this.game = game;
		this.camera = new OrthographicCamera();	
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		createWorld();
	}
	
	private void createWorld() {
		world = new World();
		
		world.setManager(new GroupManager());
		world.initialize();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		world.setDelta(delta);
		world.process();
		
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		
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
		// TODO Auto-generated method stub
		
	}

	
}
