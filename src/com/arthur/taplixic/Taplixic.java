package com.arthur.taplixic;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.*;

import com.arthur.taplixic.entity.*;
import com.arthur.taplixic.item.*;
import com.arthur.taplixic.level.*;
import com.arthur.taplixic.renderer.*;

public class Taplixic implements Runnable {
	public static final int HEIGHT = 64 * 10;
	public static final int WIDTH = 64 * 16;

	public Spritesheet spritesheet;
	public Player player;
	public Level level;
	public LevelRenderer levelRenderer;
	public Items items;
	
	public Renderer renderer;

	private void init() {
		DisplayManager.createDisplay(WIDTH, HEIGHT, "Taplixic");
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, -1, 0);
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		this.renderer = new Renderer();

		this.spritesheet = new Spritesheet("./src/spritesheet.png");
		
		this.items = new Items(this);
		
		this.level = new Level(this, 2048, 2048);
		this.player = new Player(this);
		
		this.levelRenderer = new LevelRenderer(this);
	}

	private void tick() {
		this.player.tick();
	}

	private void render() {
		this.renderer.prepare();

		this.player.moveCameraToPlayer();

		this.levelRenderer.render();
		this.player.render();
	}

	@Override
	public void run() {
		init();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int ticks = 0;
		int frames = 0;
		double time = System.currentTimeMillis();

		while (!glfwWindowShouldClose(DisplayManager.getWindow())) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				this.tick();
				ticks++;
				delta--;
			}

			frames++;
			this.render();

			if (System.currentTimeMillis() - time >= 1000) {
				System.out.println(frames + " fps, " + ticks + " ticks");
				time += 1000;
				ticks = 0;
				frames = 0;
			}

			DisplayManager.update();
		}

		this.destroy();
	}

	private void destroy() {
		this.level.save();
		
		DisplayManager.destroy();
	}

	public static void main(String[] args) {
		new Thread(new Taplixic()).start();
	}

}
