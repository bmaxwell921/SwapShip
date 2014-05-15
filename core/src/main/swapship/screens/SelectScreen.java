package main.swapship.screens;



import main.swapship.SwapShipGame;
import main.swapship.common.Constants;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Screen used to select the ship and level to play
 * @author Brandon
 *
 */
public class SelectScreen extends AbstractScreen { 
	public SelectScreen(final SwapShipGame game) {
		super(game);
	}
	
	@Override
	public void setUp() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.debug();
		
		final Label shipLabel = new Label("SHIPS", game.skin, Constants.UI.TITLE_LABEL);
		table.add(shipLabel);
		table.row();
		final ImageButton artemisButton = new ImageButton(game.skin, "artemis-button");
		table.add(artemisButton);
		final ImageButton ganymedeButton = new ImageButton(game.skin, "ganymede-button");
		table.add(ganymedeButton);
	}
}
