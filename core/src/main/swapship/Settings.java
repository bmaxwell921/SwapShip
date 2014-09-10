package main.swapship;

import main.swapship.common.Constants;

/**
 * Class used to hold the various settings for SwapShip
 * @author Brandon
 *
 */
public class Settings {
	
	// The volume for the music, [0, 1]
	public float musicVol;
	
	// The volume for sound effects [0, 1]
	public float sfxVol;
	
	// The amount of offset for the y vector - tilt control
	public float yFlat;
	
	public Settings() {
		this.musicVol = Constants.Sound.MUSIC_DEFAULT;
		this.sfxVol = Constants.Sound.SFX_DEFAULT;
		this.yFlat = 0;
	}
}
