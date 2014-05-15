package main.swapship;

import main.swapship.common.Constants;

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
	
	// Any additional info needed?
	public enum Level {
		UNCHOSEN, ONE, TWO, THREE, INFINITE,
	}
	
	// The ship they selected
	public ShipType type;
	
	// The level to play
	public Level level;
	
	public GameInfo() {
		type = ShipType.UNCHOSEN;
		level = Level.UNCHOSEN;
	}
}
