package main.swapship.screens;

import main.swapship.SwapShipGame;
import main.swapship.GameInfo.State;
import main.swapship.common.Constants;
import main.swapship.components.other.SpawnerComp;
import main.swapship.factories.EntityFactory;
import main.swapship.systems.CollisionSys;
import main.swapship.systems.CullingSys;
import main.swapship.systems.MovementSys;
import main.swapship.systems.ShotSys;
import main.swapship.systems.TargetSys;
import main.swapship.systems.TimeDelSys;
import main.swapship.systems.enemies.EnemySpawnSys;
import main.swapship.systems.enemies.PathFollowSys;
import main.swapship.systems.player.InputSys;
import main.swapship.systems.player.MoveWithPlayerSys;
import main.swapship.systems.player.PlayerBoundSys;
import main.swapship.systems.render.PlayerRenderSys;
import main.swapship.systems.render.SingleSpriteRenderSys;

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
	
	// The player, reference here for easy access for HUD
	private Entity player;
	
	public GameScreen(final SwapShipGame game) {
		this.game = game;
		this.camera = new OrthographicCamera();	
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		createWorld();
		createEnemySpawner();
		
		game.gameInfo.targetKc = 5;
	}
	
	private void createWorld() {
		world = new World();
		world.setManager(new GroupManager());
		
		createPlayer();
		world.setSystem(new PlayerRenderSys(game, camera));
		world.setSystem(new SingleSpriteRenderSys(game, camera));
		world.setSystem(new MovementSys());
		world.setSystem(new InputSys(camera));
		world.setSystem(new ShotSys());
		world.setSystem(new EnemySpawnSys());
		world.setSystem(new TargetSys());
		world.setSystem(new CollisionSys(game));
		world.setSystem(new TimeDelSys());
		world.setSystem(new PathFollowSys());
		world.setSystem(new CullingSys());
		world.setSystem(new PlayerBoundSys());
		world.setSystem(new MoveWithPlayerSys(player));
		world.initialize();
	}
	
	private void createPlayer() {
		player = EntityFactory.createPlayer(world);
	}
	
	private void createEnemySpawner() {
		Entity spawner = world.createEntity();
		SpawnerComp sc = world.createComponent(SpawnerComp.class);
		sc.setValues(Constants.SPAWN_RATE);
		spawner.addComponent(sc);
		
		spawner.addToWorld();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		world.setDelta(delta);
		world.process();
		
		if (game.gameInfo.killCount >= game.gameInfo.targetKc && game.gameInfo.state == State.BEGINNING) {
			EntityFactory.createBoss(world, game.gameInfo.level);
			game.gameInfo.state = State.BOSS;
		}
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
