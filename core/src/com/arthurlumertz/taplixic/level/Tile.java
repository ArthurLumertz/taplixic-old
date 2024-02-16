package com.arthurlumertz.taplixic.level;

import com.arthurlumertz.taplixic.item.*;
import com.badlogic.gdx.graphics.*;

public class Tile {

	private Item item;
	private Texture image;
	private boolean collision;

	private int prevHealth, health;
	private boolean destructible;
	private boolean invincible;
	private boolean liquid;

	public Tile(Item item, Texture image, boolean collision, boolean destructible, int health, boolean liquid) {
		this.item = item;
		this.image = image;
		this.collision = collision;
		this.destructible = destructible;
		this.health = health;
		this.prevHealth = health;
		this.liquid = liquid;
		this.invincible = false;
	}

	public Item getItem() {
		return item;
	}

	public Texture getImage() {
		return image;
	}

	public boolean isCollision() {
		return collision;
	}

	public int getHealth() {
		return health;
	}

	public int getPrevHealth() {
		return prevHealth;
	}

	public void setHealth(int amount) {
		health = amount;
	}

	public boolean isDestructible() {
		return destructible;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public boolean isLiquid() {
		return liquid;
	}

}
