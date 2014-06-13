package main.swapship;

import main.swapship.common.Constants;

import com.badlogic.gdx.graphics.Color;

/**
 * Class to hold information about the game to be played
 * @author Brandon
 *
 */
public class GameInfo {
	
	// Two base types of ships
	public enum ShipType {
		UNCHOSEN(), 
		ARTEMIS(Constants.Player.ARTEMIS_TOP, Constants.Player.ARTEMIS_MID, Constants.Player.ARTEMIS_BOT), 
		GANAMEDE(Constants.Player.GDX_TOP, Constants.Player.GDX_MID, Constants.Player.GDX_BOT);
		
		// File name for the top of the ship image
		public String shipTop;
		
		// File name for the middle
		public String shipMid;
		
		// File name for the bottom
		public String shipBot;
		
		private ShipType() {
			this(null, null, null);
		}
		
		private ShipType(String topImg, String midImg, String botImg) {
			this.shipTop = topImg;
			this.shipMid = midImg;
			this.shipBot = botImg;
		}
	}
	
	public enum Level {
		UNCHOSEN(Color.BLACK), ONE(Color.RED), TWO(Color.GREEN), THREE(Color.BLUE), INFINITE(Color.YELLOW);
		
		public Color tint;
		
		private Level(Color tint) {
			this.tint = tint;
		}
	}
	
	public enum State {
		BEGINNING, BOSS, END,
	}
	
	// The ship they selected
	public ShipType ship;
	
	// The level to play
	public Level level;
	
	// How many enemy ships have been destroyed
	public int killCount;
	
	// How many enemy ships needed to destroy until boss spawn
	public int targetKc;
	
	// Current score
	public int score;
	
	public State state;
	
	public GameInfo() {
		ship = ShipType.UNCHOSEN;
		level = Level.UNCHOSEN;
		killCount = 0;
		targetKc = -1;
		score = 0;
		state = State.BEGINNING;
	}
}
