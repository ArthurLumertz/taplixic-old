package com.arthurlumertz.taplixic;

import com.arthurlumertz.taplixic.engine.*;
import com.arthurlumertz.taplixic.entity.*;
import com.arthurlumertz.taplixic.gui.*;
import com.arthurlumertz.taplixic.io.*;
import com.arthurlumertz.taplixic.level.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;

import box2dLight.*;

public class Screen extends ScreenAdapter {

	private SpriteBatch batch;

	public World world;
	public RayHandler rayHandler;

	public Camera camera;
	public Entity player;
	public Level level;
	public EntityRenderer entityRenderer;
	public Gui gui;

	public static float lightness;
	public static float lightnessFactor = 0.000016722F; // around 5 minutes
	
	private float elapsedTime;

	public Screen(SpriteBatch batch) {
		this.batch = batch;
	}

	@Override
	public void show() {
		gui = new Gui(this, batch);
	}
	
	public void start(String world) {
		this.world = new World(new Vector2(0, 0), true);
		rayHandler = new RayHandler(this.world);

		camera = new Camera();
		
		level = new Level(world, camera);
		level.save();
		
		player = new Player(camera, rayHandler, level);

		camera.setPosition(player.getPosition());
		
		entityRenderer = new EntityRenderer(rayHandler);
		
		State.STATE = State.TITLE;
	}

	@Override
	public void render(float delta) {
		if (State.STATE != State.TITLE) {
			elapsedTime += delta;
			if (elapsedTime >= 60.0F) {
				System.out.println("60.0 seconds");
				player.save();
				level.save();
				elapsedTime = 0;
			}
			
			camera.update(player, batch);
			rayHandler.setCombinedMatrix(camera.getCamera());
			rayHandler.setAmbientLight(lightness);

			batch.begin();
			level.render(batch);
			player.render(batch);
			entityRenderer.render(batch);
			batch.end();
			
			rayHandler.updateAndRender();
			handleInput();
		}
		
		if (State.STATE == State.PLAY) {
			lightness -= lightnessFactor;
			if (lightness <= 0.2F || lightness >= 1) {
				lightnessFactor = -lightnessFactor;
			}
		}
		
		gui.render(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		gui.resize(width, height);
	}

	@Override
	public void dispose() {
		System.out.println("Disposing resources...");
		level.save();
		gui.dispose();
		player.dispose();
		entityRenderer.dispose();
		rayHandler.dispose();
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setWindowedMode(960, 540);
			} else {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			level.save();
			player.save();
		}
		
		/* SWITCHING STATES */
		if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
			if (State.STATE == State.PLAY) {
				State.STATE = State.INVENTORY;
			} else {
				State.STATE = State.PLAY;
			}
		}
	}

}
