package main.swapship.screens;

import main.swapship.SwapShipGame;
import main.swapship.systems.InputSys;
import main.swapship.systems.MovementSys;
import main.swapship.systems.PlayerRenderSys;
import main.swapship.systems.ShotSys;
import main.swapship.systems.SingleSpriteRenderSys;
import main.swapship.util.EntityFactory;

import com.artemis.Entity;
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
	
	// The player, reference here for easy access
	private Entity player;
	
	public GameScreen(final SwapShipGame game) {
		this.game = game;
		this.camera = new OrthographicCamera();	
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		createWorld();
		createPlayer();
	}
	
	private void createWorld() {
		world = new World();
		world.setSystem(new PlayerRenderSys(game, camera));
		world.setSystem(new SingleSpriteRenderSys(game, camera));
		world.setSystem(new MovementSys());
		world.setSystem(new InputSys());
		world.setSystem(new ShotSys());
		world.setManager(new GroupManager());
		world.initialize();
	}
	
	private void createPlayer() {
		player = EntityFactory.createPlayer(world);
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
		world.dispose();
	}

	
}
