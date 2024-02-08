package com.arthur.taplixic.level;

import com.arthur.taplixic.*;
import com.arthur.taplixic.entity.*;

public class LevelRenderer {
	private Level level;
	private Player player;
	
	public LevelRenderer(Taplixic taplixic) {
		this.level = taplixic.level;
		this.player = taplixic.player;
	}

	public void render() {
		int playerTileX = (int) (player.x / Tile.size);
	    int playerTileY = (int) (player.y / Tile.size);

	    int renderDistanceX = Taplixic.WIDTH  / Tile.size;
	    int renderDistanceY = Taplixic.HEIGHT / Tile.size;

	    int startX = Math.max(0, playerTileX - renderDistanceX);
	    int startY = Math.max(0, playerTileY - renderDistanceY);
	    int endX = Math.min(level.width - 1, playerTileX + renderDistanceX);
	    int endY = Math.min(level.height - 1, playerTileY + renderDistanceY);

	    for (int y = startY; y <= endY; y++) {
	        for (int x = startX; x <= endX; x++) {
	            int index = y * level.width + x;
	            if (index >= 0 && index < level.blocks.length) {
	                byte blockType = level.blocks[index];

	                float xo = (x * Tile.size);
	                float yo = (y * Tile.size);

	                renderSolidBlock(blockType, xo, yo);
	            }
	        }
	    }
	}

	private void renderSolidBlock(int blockType, float x, float y) {
		level.tiles[blockType].texture.render(x, y, Tile.size, Tile.size);
	}

}
