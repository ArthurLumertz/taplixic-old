package com.arthurlumertz.taplixic.entity;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public abstract class Entity {
	protected Texture image;

	protected Texture idle1, idle2;
	protected Texture anim1, anim2;
	protected Texture liquid1, liquid2;

	protected Vector2 position;
	protected Vector2 size;
	protected Vector2 velocity;

	protected Rectangle boundingBox;
	protected Rectangle attackingBox;

	protected boolean collision;

	protected String direction;
	protected boolean up, down, left, right;

	protected int speed;
	protected int previousSpeed;

	protected Sound attackSound;

	protected static int maxHealth, health;
	protected static int maxStamina, stamina;

	protected int spriteCounter;
	protected int spriteNumber;
	protected int invincibleCounter;
	protected int attackingCounter;

	protected boolean attacking;
	protected boolean invincible;

	protected static Item selectedItem;

	protected Pixmap walkingParticle = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

	public Entity() {
		position = new Vector2(0, 0);
		size = new Vector2(48, 48);
		velocity = new Vector2(0, 0);
		direction = "down";
	}

	public abstract void show();

	public abstract void render(SpriteBatch batch);

	public abstract void dispose();

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

	public Texture getLiquid1() {
		return liquid1;
	}

	public void setLiquid1(Texture liquid1) {
		this.liquid1 = liquid1;
	}

	public Texture getLiquid2() {
		return liquid2;
	}

	public void setLiquid2(Texture liquid2) {
		this.liquid2 = liquid2;
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

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Rectangle getAttackingBox() {
		return attackingBox;
	}

	public void setAttackingBox(Rectangle attackingBox) {
		this.attackingBox = attackingBox;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
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

	public int getPreviousSpeed() {
		return previousSpeed;
	}

	public void setPreviousSpeed(int previousSpeed) {
		this.previousSpeed = previousSpeed;
	}

	public Sound getAttackSound() {
		return attackSound;
	}

	public void setAttackSound(Sound attackSound) {
		this.attackSound = attackSound;
	}

	public static int getMaxHealth() {
		return maxHealth;
	}

	public static void setMaxHealth(int maxHealth) {
		Entity.maxHealth = maxHealth;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int health) {
		Entity.health = health;
	}

	public static int getMaxStamina() {
		return maxStamina;
	}

	public static void setMaxStamina(int maxStamina) {
		Entity.maxStamina = maxStamina;
	}

	public static int getStamina() {
		return stamina;
	}

	public static void setStamina(int stamina) {
		Entity.stamina = stamina;
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

	public int getInvincibleCounter() {
		return invincibleCounter;
	}

	public void setInvincibleCounter(int invincibleCounter) {
		this.invincibleCounter = invincibleCounter;
	}

	public int getAttackingCounter() {
		return attackingCounter;
	}

	public void setAttackingCounter(int attackingCounter) {
		this.attackingCounter = attackingCounter;
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

	public static Item getSelectedItem() {
		return selectedItem;
	}

	public static void setSelectedItem(Item selectedItem) {
		Entity.selectedItem = selectedItem;
	}

	public Pixmap getWalkingParticle() {
		return walkingParticle;
	}

	public void setWalkingParticle(Pixmap walkingParticle) {
		this.walkingParticle = walkingParticle;
	}

	public void load() {
	}

	public void save() {
	}

}
