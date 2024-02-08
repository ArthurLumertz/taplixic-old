package com.arthur.taplixic.renderer;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	public void prepare() {
		glClearColor(0.4F, 0.8F, 1.0F, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT);
	}

}
