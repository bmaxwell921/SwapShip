package main.swapship;

import main.swapship.common.Constants;

/**
 * Class used to hold the various settings for SwapShip
 * @author Brandon
 *
 */
public class Settings {
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
	
	// The ship they selected
	public ShipType type;
	
	// The volume for the music, [0, 1]
	public float musicVol;
	
	// The volume for sound effects [0, 1]
	public float sfxVol;
	
	// The amount of offset for the y vector - tilt control
	public float yFlat;
	
	public Settings() {
		this.type = ShipType.UNCHOSEN;
		this.musicVol = Constants.Sound.MUSIC_DEFAULT;
		this.sfxVol = Constants.Sound.SFX_DEFAULT;
		this.yFlat = 0;
	}
}
