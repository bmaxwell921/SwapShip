package main.swapship.components.player;

import com.artemis.Component;

/**
 * Component holding onto the names of the sprites that make up this Entity
 * @author Brandon
 *
 */
public class ShipSpritesComp implements Component {

	public String topName;
	public String midName;
	public String botName;

	/**
	 * 
	 * @param topName
	 * @param midName
	 * @param botName
	 */
	public void setValues(String topName, String midName, String botName) {
		this.topName = topName;
		this.midName = midName;
		this.botName = botName;
	}
	
	public void setTop(String topName) {
		this.topName = topName;
	}
	
	public void setMid(String midName) {
		this.midName = midName;
	}
	
	public void setBot(String botName) {
		this.botName = botName;
	}
	
	
	@Override
	public void reset() {
		this.topName = this.botName = this.midName = "";
	}
	
	
}
