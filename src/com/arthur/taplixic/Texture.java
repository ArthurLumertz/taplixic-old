package com.arthur.taplixic;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	private Spritesheet spritesheet;

	private int x, y;
	private float minU, maxU, minV, maxV;

	public Texture(Taplixic taplixic, int x, int y) {
		this.spritesheet = taplixic.spritesheet;
		this.x = x;
		this.y = y;

		this.minU = (this.x / (float) spritesheet.getWidth());
		this.maxU = ((this.x + 32) / (float) spritesheet.getWidth());
		this.minV = (this.y / (float) spritesheet.getHeight());
		this.maxV = ((this.y + 32) / (float) spritesheet.getHeight());
	}

	public void render(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		spritesheet.bind();

		glTexCoord2f(minU, minV);
		glVertex2f(x, y);

		glTexCoord2f(maxU, minV);
		glVertex2f(x + width, y);

		glTexCoord2f(maxU, maxV);
		glVertex2f(x + width, y + height);

		glTexCoord2f(minU, maxV);
		glVertex2f(x, y + height);

		spritesheet.unbind();
		glEnd();
	}

}
