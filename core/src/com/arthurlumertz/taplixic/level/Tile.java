package com.arthurlumertz.taplixic.level;

import com.badlogic.gdx.graphics.*;

public class Tile {
	private Texture image;
	private boolean collision;
	private boolean destructible;
	private int health = 10;

	public Tile(Texture image, boolean collision, boolean destructible) {
		this.image = image;
		this.collision = collision;
		this.destructible = destructible;
	}

	public boolean isDestructible() {
		return destructible;
	}

	public int getHealth() {
		return health;
	}
	
	public int damage(int amount) {
		health -= amount;
		return health;
	}

	public Texture getImage() {
		return image;
	}

	public boolean isCollision() {
		return collision;
	}

}
