package main.swapship.common;

import com.badlogic.gdx.graphics.Color;


public class Constants {
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 800;
	
	public static final int SHIP_WIDTH = 81;
	public static final int SHIP_HEIGHT = 81;
	
	public static final int SPAWN_RATE = 2;
	
	public static class MainScreen {
		public static final String PLAY = "PLAY";
		public static final String SETTINGS = "SETTINGS";
		public static final String HELP = "HELP";
	}
	
	public static class Groups {
		public static final String PLAYER = "PLAYER";
		public static final String PLAYER_ATTACK = "PLAYER_ATTACK";
		public static final String PLAYER_SPECIAL = "PLAYER_SPECIAL";
		
		
		public static final String ENEMY = "ENEMY";
		public static final String ENEMY_ATTACK = "ENEMY_ATTACK";
	}
	
	public static class Player {		
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
		
		public static final int BASE_DAMAGE = 10;
		public static final int BASE_PART_LVL = 1;
		public static final float FIRE_RATE = 1;
		
		public static final int BASE_HEALTH = 100;
	}
	
	public static class Enemy {
		public static final String[] NAMES = {"Enemy1", "Enemy2"};
		public static final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE};
		
		public static final int BASE_DAMAGE = 5;
		public static final float FIRE_RATE = 2;
		
		public static final float MAX_MOVE = 75f;
		
		public static final int BASE_HEALTH = 20;
	}
	
	public static class Shot { 
		public static final int WIDTH = 18;
		public static final int HEIGHT = 18;
		
		public static final String SHOT_NAME = "Shot";
		public static final float VEL = 300f;
	}
	
	public static class Missile {
		public static final int WIDTH = 45;
		public static final int HEIGHT = 45;
		public static final int SPAWN_COUNT = 3;
		public static final String NAME = "Missile";
		
		public static final float VEL = 500f;
	}
	
	public static class Beam {
		public static final int WIDTH = 27;
		public static final int HEIGHT = 63;
		
		public static final String NAME = "Beam";
		
		// 5 seconds
		public static final int TIME_OUT = 5;
		
		public static final int HEALTH = Integer.MAX_VALUE;
		
		public static final int BASE_DAMAGE = 20;
	}
	
	public static class Explosion {
		public static final int WIDTH = 81;
		public static final int HEIGHT = 81;
		
		public static final String NAME = "Explosion";
		
		public static final float LIFE_TIME = 0.5f;
		
		public static final float VEL_PERC = 0.35f;
	}
}
