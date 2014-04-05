package main.swapship.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Util used to store assets
 * @author Brandon
 *
 */
public class AssetUtil {
	
	private static AssetUtil instance;
	
	private AssetUtil() {
		this.cache = new ArrayMap<>();
	}
	
	// Get the instance
	public static AssetUtil getInstance() {
		if (instance == null) {
			instance = new AssetUtil();
		}
		return instance;
	}
	
	private TextureAtlas atlas;
	
	private ArrayMap<String, TextureRegion> cache;
	
	// Called by the game to get the atlas
	public void setTextureAtlas(TextureAtlas atlas) {
		this.atlas = atlas;
	}
	
	/**
	 * Gets the texture region for the specified name
	 * @param name
	 * @return
	 */
	public TextureRegion getTexture(String name) {
		// Get from cache if possible
		if (cache.containsKey(name)) {
			return cache.get(name);
		}
		
		// Otherwise, find in the atlas, cache, then return
		TextureRegion tr = atlas.findRegion(name);
		cache.put(name, tr);
		return tr;
	}

}
