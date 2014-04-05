package main.swapship.components.other;

import main.swapship.components.TimerComp;

public class SpawnerComp extends TimerComp {

	public void setValues(int spawnRate) {
		super.startTime = spawnRate;
		super.timeLeft = 0;
	}
}
