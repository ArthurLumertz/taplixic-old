package com.arthur.taplixic.entity;

import com.arthur.taplixic.*;
import com.arthur.taplixic.phys.*;

public class Entity {
	public Texture image;
	public Texture up1, up2, down1, down2, left1, left2, right1, right2;
	
	public boolean up, down, left, right;
	public String direction;
	
	public int spriteCounter;
	public int spriteNum;
	
	public double x, y;
	public double prevX, prevY;
	
	public double speed, health, stamina;
	
	public AABB boundingBox;
	public boolean collision;
	
	public void init() {}
	public void tick() {}
	public void render() {}
	
	public void damage(double amount) {
		if (health > 0)
			health -= amount;
	}
	
	public void heal(double amount) {
		if (health < 12)
			health += amount;
	}

}
