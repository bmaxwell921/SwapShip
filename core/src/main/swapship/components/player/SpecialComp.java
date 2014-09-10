package main.swapship.components.player;

import main.swapship.common.DefensiveSpecialType;
import main.swapship.common.OffensiveSpecialType;

import com.artemis.Component;

public class SpecialComp implements Component {
	
	public OffensiveSpecialType offensive;
	public int offensiveCount;
	
	public DefensiveSpecialType defensive;
	public int defensiveCount;
	
	public SpecialComp() {
		offensive = OffensiveSpecialType.NONE;
		offensiveCount = 0;
		defensive = DefensiveSpecialType.NONE;
		defensiveCount = 0;
	}
	
	@Override
	public void reset() {
		offensive = OffensiveSpecialType.NONE;
		defensive = DefensiveSpecialType.NONE;
		
		offensiveCount = 0;
		defensiveCount = 0;
	}

}
