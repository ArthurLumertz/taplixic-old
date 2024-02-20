package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.inventory.*;
import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;

public abstract class Block {

	protected int id;
	protected Texture image;

	protected Sound breakSound;
	protected Sound walkingSound;

	protected int previousHealth;
	protected int health;

	protected boolean solid;
	protected boolean destructible;
	protected boolean liquid;

	protected boolean invincible;
	
	protected int type;

	protected Item drop;

	public Block() {
		init();
	}
	
	public boolean damage(int amount) {
		if (health >= 0) {
			health -= amount;
		} else if (health <= 0) {
			Inventory.add(drop);
			health = previousHealth;
			return true;
		}
		return false;
	}

	protected abstract void init();

	public void dispose() {
		if (image != null) {
			image.dispose();
		}
		
		if (breakSound != null) {
			breakSound.dispose();
		}
		
		if (walkingSound != null) {
			walkingSound.dispose();
		}
	}
	
	public int getID() {
		return id;
	}
	
	public int getType() {
		return type;
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public Sound getBreakSound() {
		return breakSound;
	}

	public void setBreakSound(Sound breakSound) {
		this.breakSound = breakSound;
	}

	public Sound getWalkingSound() {
		return walkingSound;
	}

	public void setWalkingSound(Sound walkingSound) {
		this.walkingSound = walkingSound;
	}

	public int getPreviousHealth() {
		return previousHealth;
	}

	public void setPreviousHealth(int previousHealth) {
		this.previousHealth = previousHealth;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isDestructible() {
		return destructible;
	}

	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}

	public boolean isLiquid() {
		return liquid;
	}

	public void setLiquid(boolean liquid) {
		this.liquid = liquid;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public Item getDrop() {
		return drop;
	}

	public void setDrop(Item drop) {
		this.drop = drop;
	}

}
