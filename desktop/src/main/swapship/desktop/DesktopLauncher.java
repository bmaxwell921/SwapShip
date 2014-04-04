package main.swapship.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

import main.swapship.SwapShipGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		packImages();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SwapShipGame(), config);
	}
	
	private static void packImages() {
		TexturePacker2.process("../images", "../android/assets", "gameImages");
	}
}
