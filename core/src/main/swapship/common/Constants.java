package main.swapship.common;

import com.badlogic.gdx.graphics.Color;


public class Constants {
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 800;
	
	public static class MainScreen {
		public static final String PLAY = "PLAY";
		public static final String SETTINGS = "SETTINGS";
		public static final String HELP = "HELP";
	}
	
	public static class Groups {
		public static final String PLAYER = "PLAYER";
		public static final String PLAYER_ATTACK = "PLAYER_ATTACK";
		
		public static final String ENEMY = "ENEMY";
		public static final String ENEMY_ATTACK = "ENEMY_ATTACK";
	}
	
	public static class Player {
		public static final int WIDTH = 81;
		public static final int HEIGHT = 81;
		
		public static final String ARTEMIS_TOP = "Artemis_Top";
		public static final String ARTEMIS_MID = "Artemis_Mid";
		public static final String ARTEMIS_BOT = "Artemis_Bot";
		
		public static final String GDX_TOP = "Ganymede_Top";
		public static final String GDX_MID = "Ganymede_Mid";
		public static final String GDX_BOT = "Ganymede_Bot";
		
		public static final Color DEFAULT_COLOR = Color.GREEN; 
		public static final int SHIP_PART_HEIGHT = 27;
		
		public static final float DEGREE_AMT = 0.1f;
		
		public static final float START_VEL = 0f;		
		public static final float MAX_MOVE = 150f;
		
		public static final int MIN_Y = 20;
		public static final int MAX_Y = 350;
	}
}
