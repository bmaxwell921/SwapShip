package main.swapship.components;

import com.artemis.Component;

public class LevelComp implements Component {

	// Each ship component has a level 
	public int topLevel;
	public int midLevel;
	public int botLevel;
	
	// Ship levels combine to form the total level
	public int overallLevel;
	
	
	public void setValues(int topLevel, int midLevel, int botLevel, int baseDamage) {
		this.topLevel = topLevel;
		this.midLevel = midLevel;
		this.botLevel = botLevel;
		calcOverallLevel();
		calcDamage(baseDamage);
	}
	
	private void calcOverallLevel() {
		overallLevel = topLevel + midLevel + botLevel;
	}
	
	public int calcDamage(int baseDamage) {
		return baseDamage * overallLevel;
	}
	
	@Override
	public void reset() {
		topLevel = midLevel = botLevel = overallLevel = 0;
	}
}
