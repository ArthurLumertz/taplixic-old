package com.arthurlumertz.taplixic.engine;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public abstract class Entity {
	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	protected Texture image;
	protected Texture idle1, idle2;
	protected Texture anim1, anim2;

	protected Vector2 position;
	protected Vector2 size;
	protected Rectangle boundingBox;
	protected Rectangle attackingBox;

	protected boolean collision;

	protected boolean up, down, left, right;
	protected int speed;

	protected int spriteCounter;
	protected int spriteNumber;
	protected int invincibleCounter;
	protected int attackingCounter;

	protected boolean attacking;
	protected boolean invincible;

	public Entity() {
		position = new Vector2(0, 0);
		size = new Vector2(48, 48);
	}

	public abstract void show();

	public abstract void render(SpriteBatch batch);

	public abstract void dispose();

	public Rectangle getAttackingBox() {
		return attackingBox;
	}

	public void setAttackingBox(Rectangle attackingBox) {
		this.attackingBox = attackingBox;
	}

	public int getAttackingCounter() {
		return attackingCounter;
	}

	public void setAttackingCounter(int attackingCounter) {
		this.attackingCounter = attackingCounter;
	}

	public int getInvincibleCounter() {
		return invincibleCounter;
	}

	public void setInvincibleCounter(int invincibleCounter) {
		this.invincibleCounter = invincibleCounter;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public Texture getIdle1() {
		return idle1;
	}

	public void setIdle1(Texture idle1) {
		this.idle1 = idle1;
	}

	public Texture getIdle2() {
		return idle2;
	}

	public void setIdle2(Texture idle2) {
		this.idle2 = idle2;
	}

	public Texture getAnim1() {
		return anim1;
	}

	public void setAnim1(Texture anim1) {
		this.anim1 = anim1;
	}

	public Texture getAnim2() {
		return anim2;
	}

	public void setAnim2(Texture anim2) {
		this.anim2 = anim2;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpriteCounter() {
		return spriteCounter;
	}

	public void setSpriteCounter(int spriteCounter) {
		this.spriteCounter = spriteCounter;
	}

	public int getSpriteNumber() {
		return spriteNumber;
	}

	public void setSpriteNumber(int spriteNumber) {
		this.spriteNumber = spriteNumber;
	}

}
