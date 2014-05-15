package main.swapship.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

import main.swapship.SwapShipGame;
import main.swapship.common.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		packImages();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Swap Ship";
		config.width = Constants.SCREEN_WIDTH;
		config.height = Constants.SCREEN_HEIGHT;
		config.y = 0;
		config.x = 480;
		new LwjglApplication(new SwapShipGame(), config);
	}
	
	private static void packImages() {
		TexturePacker2.process("../images/game", "../android/assets/game", "gameImages");
		TexturePacker2.process("../images/ui", "../android/assets/ui", "uiskin");
	}
}
