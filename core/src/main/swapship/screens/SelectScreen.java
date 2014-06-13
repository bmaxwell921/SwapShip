package main.swapship.screens;



import main.swapship.GameInfo.Level;
import main.swapship.GameInfo.ShipType;
import main.swapship.SwapShipGame;
import main.swapship.common.Constants;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen used to select the ship and level to play
 * @author Brandon
 *
 */
public class SelectScreen extends AbstractScreen { 
	
	private ButtonGroup shipGroup;
	private ButtonGroup levelGroup;
	
	public SelectScreen(final SwapShipGame game) {
		super(game);
	}
	
	@Override
	public void setUp() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.debug();
		
		setUpShipChoice(table);
		table.row();
		setUpLevelChoice(table);
		table.row();
		
		final TextButton go = new TextButton("Go!", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(go);
		go.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Make sure they've checked something
				if (game.gameInfo.level != Level.UNCHOSEN && game.gameInfo.ship != ShipType.UNCHOSEN) {
					game.setScreen(new GameScreen(game));
					return;
				}
				go.setChecked(false);
			}
		});
		
		// Set the defaults as checked
		shipGroup.getButtons().get(0).setChecked(true);
		levelGroup.getButtons().get(0).setChecked(true);
	}

	// Sets up the buttons used to select the ship
	private void setUpShipChoice(Table table) {
		// The label for ships
		final Label shipLabel = new Label("SHIPS", game.skin, Constants.UI.TITLE_LABEL);
		table.add(shipLabel);
		table.row();

		// Button to choose the artemis ship
		final ImageButton artemisButton = new ImageButton(game.skin, "artemis-button");
		table.add(artemisButton);
		artemisButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameInfo.ship = ShipType.ARTEMIS;
			}
		});
		
		// Button to choose the ganymede ship
		final ImageButton ganymedeButton = new ImageButton(game.skin, "ganymede-button");
		table.add(ganymedeButton);
		ganymedeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameInfo.ship = ShipType.GANAMEDE;
			}
		});
		
		// only allow one to be selected
		shipGroup = new ButtonGroup(artemisButton, ganymedeButton);
	}
	
	private void setUpLevelChoice(Table table) {
		// Add a label for the options
		final Label levelLabel = new Label("LEVELS", game.skin, Constants.UI.TITLE_LABEL);
		table.add(levelLabel);
		table.row();
		
		final TextButton l1 = new TextButton("Level 1", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(l1);
		l1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameInfo.level = Level.ONE;
			}
		});
		table.row();
		final TextButton l2 = new TextButton("Level 2", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(l2);
		l2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameInfo.level = Level.TWO;
			}
		});
		table.row();
		final TextButton l3 = new TextButton("Level 3", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(l3);
		l3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameInfo.level = Level.THREE;
			}
		});
		table.row();
		final TextButton infinite = new TextButton("Infinite", game.skin, Constants.UI.OPTION_BUTTON);
		table.add(infinite);
		infinite.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameInfo.level = Level.INFINITE;
			}
		});
		
		levelGroup = new ButtonGroup(l1, l2, l3, infinite);
	}
}
