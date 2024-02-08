package com.arthur.taplixic.entity;

import static org.lwjgl.opengl.GL11.*;

import java.io.*;
import java.util.*;

import org.lwjgl.glfw.*;

import com.arthur.taplixic.*;
import com.arthur.taplixic.item.*;
import com.arthur.taplixic.level.*;
import com.arthur.taplixic.phys.*;
import com.arthur.taplixic.renderer.*;

public class Player extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Taplixic taplixic;
	private Level level;
	
	public List<Item> inventory = new ArrayList<Item>();

	public Player(Taplixic taplixic) {
		this.taplixic = taplixic;
		this.level = taplixic.level;
		
		this.init();
	}

	private void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	private void resetPosition() {
		double x, y;
	    int currentTileType;
	    do {
	        x = Math.random() * level.width * Tile.size / 2;
	        y = Math.random() * level.height * Tile.size / 2;

	        int tileX = (int) (x / Tile.size);
	        int tileY = (int) (y / Tile.size);
	        currentTileType = level.blocks[tileY * level.width + tileX];

		    setPosition(x, y);
	    } while (level.tiles[currentTileType].collision);
	}

	@Override
	public void init() {
		int scrollX = 0;
		this.down1 = new Texture(taplixic, scrollX, 32);
		scrollX += 32;
		this.down2 = new Texture(taplixic, scrollX, 32);
		scrollX += 32;
		this.up1 = new Texture(taplixic, scrollX, 32);
		scrollX += 32;
		this.up2 = new Texture(taplixic, scrollX, 32);
		scrollX += 32;
		this.left1 = new Texture(taplixic, scrollX, 32);
		scrollX += 32;
		this.left2 = new Texture(taplixic, scrollX, 32);
		scrollX += 32;
		this.right1 = new Texture(taplixic, scrollX, 32);
		scrollX += 32;
		this.right2 = new Texture(taplixic, scrollX, 32);

		this.direction = "down";
		this.image = down1;

		this.speed = 5.0D;
		this.health = 12.0D;
		this.stamina = 12.0D;

		this.prevX = this.x;
		this.prevY = this.y;
		this.boundingBox = new AABB((float) this.x + 16, (float) this.y + 16, Tile.size / 1.6F, Tile.size / 1.6F);
		
		inventory.add(taplixic.items.items[Items.FIST]);

		this.resetPosition();
	}

	@Override
	public void tick() {
		this.handleInput();

		if (up || down || left || right) {
			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				} else
					image = up2;
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				} else
					image = down2;
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				} else
					image = left2;
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				} else
					image = right2;
				break;
			}

			collision = false;
			collision = checkCollision();
			if (!collision) {
				double newX = x;
				double newY = y;

				if (up)
					newY -= speed;
				if (down)
					newY += speed;
				if (left)
					newX -= speed;
				if (right)
					newX += speed;

				// Update the bounding box position
				boundingBox.x = (float) newX;
				boundingBox.y = (float) newY;

				// Check if the new position causes collision
				collision = checkCollision();

				if (!collision) {
					// Update the position if no collision occurs
					x = newX;
					y = newY;
				} else {
					// Revert the bounding box position
					boundingBox.x = (float) x;
					boundingBox.y = (float) y;
				}

				if (x >= level.width || x <= 0 || y >= level.height || y <= 0) {
					collision = true;
				}
			} else {
				this.x = this.prevX;
				this.y = this.prevY;

				collision = false;
			}

			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
		int tileX = (int) (this.x / Tile.size);
		int tileY = (int) (this.y / Tile.size);
		
		int currentTile = level.blocks[(int) (tileY * level.width + tileX)];
		int currentTileType = level.blocks[currentTile];
		
		if (currentTileType == 2) {
			System.out.println("in water!");
		}
	}
	
	public void moveCameraToPlayer() {
		double x = ((Taplixic.WIDTH - Tile.size) / 2 - this.x);
		double y = ((Taplixic.HEIGHT - Tile.size) / 2 - this.y);

		glLoadIdentity();
		glTranslated(x, y, 0.D);
	}

	@Override
	public void render() {
		image.render((float) x, (float) y, Tile.size, Tile.size);
	}

	private void handleInput() {
		if (GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_W) == GLFW.GLFW_TRUE
				|| GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_UP) == GLFW.GLFW_TRUE) {
			direction = "up";
			up = true;
		} else
			up = false;

		if (GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_S) == GLFW.GLFW_TRUE
				|| GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_TRUE) {
			direction = "down";
			down = true;
		} else
			down = false;

		if (GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_A) == GLFW.GLFW_TRUE
				|| GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_TRUE) {
			direction = "left";
			left = true;
		} else
			left = false;

		if (GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_D) == GLFW.GLFW_TRUE
				|| GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_TRUE) {
			direction = "right";
			right = true;
		} else
			right = false;

		if (GLFW.glfwGetKey(DisplayManager.getWindow(), GLFW.GLFW_KEY_R) == GLFW.GLFW_TRUE) {
			resetPosition();
		}
	}

	public boolean checkCollision() {
		int tileX = (int) (this.x / Tile.size);
		int tileY = (int) (this.y / Tile.size);

		int leftTileX = tileX - 1;
		int rightTileX = tileX + 1;
		int upTileY = tileY - 1;
		int downTileY = tileY + 1;

		for (int i = leftTileX; i <= rightTileX; i++) {
			for (int j = upTileY; j <= downTileY; j++) {
				if (i >= 0 && i < level.width && j >= 0 && j < level.height) {
					int index = j * level.width + i;
					byte tileType = level.blocks[index];

					float blockX = i * Tile.size;
					float blockY = j * Tile.size;

					if (level.tiles[tileType].collision) {
						AABB tileBoundingBox = new AABB(blockX, blockY, Tile.size, Tile.size);

						if (boundingBox.intersects(tileBoundingBox))
							return true;
					}
				}
			}
		}

		return false;
	}
}
