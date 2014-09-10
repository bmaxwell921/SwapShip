package main.swapship.screens;

import main.swapship.SwapShipGame;
import main.swapship.common.Constants;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainScreen extends AbstractScreen {
	
	public MainScreen(final SwapShipGame game) {
		super(game);
	}
	
	@Override
	protected void setUp() {
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
				game.setScreen(new SelectScreen(game));
			}
		});
		
		table.row();
		
		final TextButton SETTINGS = new TextButton("SETTINGS", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(SETTINGS);
		
		table.row();
		
		final TextButton HELP = new TextButton("HELP", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(HELP);
	}
}
