package com.arthur.taplixic.phys;

public class AABB {
	public float x, y, width, height;
	
	public AABB(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean intersects(AABB other) {
		return (this.x < other.x + other.width &&
				this.x + this.width > other.x &&
				this.y < other.y + other.height &&
				this.y + this.height > other.y);
	}

}
