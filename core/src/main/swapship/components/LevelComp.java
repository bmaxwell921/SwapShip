package main.swapship.components;

import com.artemis.Component;

public class LevelComp implements Component {

	// Each ship component has a level 
	public int topLevel;
	public int midLevel;
	public int botLevel;
	
	// Ship levels combine to form the total level
	public int overallLevel;
	
	// Damage is relative to the level
	public int damage;
	
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
	
	private void calcDamage(int baseDamage) {
		damage = baseDamage * overallLevel;
	}
	
	@Override
	public void reset() {
		topLevel = midLevel = botLevel = overallLevel = damage = 0;
	}
}
