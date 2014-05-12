package main.swapship;

import main.swapship.common.Constants;
import main.swapship.screens.MainScreen;
import main.swapship.util.AssetUtil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

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
		createSkin();
		
		this.setScreen(new MainScreen(this));
		AssetUtil.getInstance().setTextureAtlas(new TextureAtlas(Gdx.files.internal("gameImages.atlas")));
	}
	
	private void createSkin() {
		// TODO probably should read this from a file
		skin = new Skin();
		
		// Setting up fonts
		BitmapFont titleFont = new BitmapFont();
		titleFont.scale(2f);
		skin.add(Constants.UI.TITLE_FONT, titleFont);	
		skin.add(Constants.UI.OPTIONS_FONT, new BitmapFont());
		
		// White pixel, used later
		Pixmap white = new Pixmap(1, 1, Format.RGBA8888);
		white.setColor(Color.WHITE);
		white.fill();
		skin.add(Constants.UI.WHITE_PX, new Texture(white));
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.newDrawable(Constants.UI.WHITE_PX, Color.DARK_GRAY);
		tbs.down = skin.newDrawable(Constants.UI.WHITE_PX, Color.DARK_GRAY);
		tbs.over = skin.newDrawable(Constants.UI.WHITE_PX, Color.LIGHT_GRAY);
		tbs.font = skin.getFont(Constants.UI.OPTIONS_FONT);
		skin.add(Constants.UI.OPTION_BUTTON, tbs);
		
		LabelStyle ls = new LabelStyle();
		ls.font = skin.getFont(Constants.UI.TITLE_FONT);
		skin.add(Constants.UI.TITLE_LABEL, ls);
		
		ImageButtonStyle artemisStyle = new ImageButtonStyle();
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
