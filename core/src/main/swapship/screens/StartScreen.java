package main.swapship.screens;

import main.swapship.SwapShipGame;
import main.swapship.common.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class StartScreen implements Screen {
	
	// The game
	private final SwapShipGame game;
	
	// Stage for viewport
	private Stage stage;
	
	public StartScreen(final SwapShipGame game) {
		this.game = game;
		this.stage = new Stage();
		
		this.addButtons();
	}
	
	private void addButtons() {
		Skin skin = new Skin();
		TextButton tb = new TextButton("PLAY", skin);
		tb.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("TOUCH", "Touch down event");
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("TOUCH", "Touch up event");
			}
		});
		stage.addActor(tb);
		
//		TextActor play = new TextActor(game.optionFont, Constants.MainScreen.PLAY, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
//		play.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//		play.addListener(new InputListener() {
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("TOUCH", "Touch down event");
//				return true;
//			}
//			
//			@Override
//			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("TOUCH", "Touch up event");
//			}
//		});
//		stage.addActor(play);
		
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
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
	
	private class TextActor extends Actor {
		// Font to draw with
		private BitmapFont font;
		
		// Text to draw
		private String text;
		
		// Where to draw
		private float x, y;
		
		public TextActor(BitmapFont font, String text, float x, float y) {
			this.font = font;
			this.text = text;
			
			TextBounds tb = font.getBounds(text);
			this.x = x - tb.width / 2;
			this.y = y - tb.height / 2;
		}
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			Color color = Color.WHITE;
			batch.setColor(color.r, color.g, color.b, color.a);
			font.draw(batch, text, x, y);
		}
	}
}
