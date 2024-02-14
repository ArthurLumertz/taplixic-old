package com.arthurlumertz.taplixic;

import com.arthurlumertz.taplixic.engine.*;
import com.arthurlumertz.taplixic.entity.*;
import com.arthurlumertz.taplixic.level.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;

import box2dLight.*;

public class Screen extends ScreenAdapter {

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	public World world;
	public RayHandler rayHandler;

	public Camera camera;
	public Entity player;
	public Level level;

	public float lightness = 1.0F;
	public float lightnessFactor = 0.0002F;

	public Screen(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
	}

	@Override
	public void show() {
		world = new World(new Vector2(0, 0), true);
		rayHandler = new RayHandler(world);

		camera = new Camera();
		
		level = new Level(camera);
		
		player = new Player(camera, rayHandler, level);
		player.show();
		camera.setPosition(player.getPosition());
	}

	@Override
	public void render(float delta) {
		camera.update(player, batch);
		rayHandler.setCombinedMatrix(camera.getCamera().combined.cpy());
		rayHandler.setAmbientLight(lightness);

		batch.begin();

		level.render(batch);
		player.render(batch);

		batch.end();

		rayHandler.updateAndRender();

		lightness -= lightnessFactor;
		if (lightness <= 0.2F || lightness >= 1) {
			lightnessFactor = -lightnessFactor;
		}

		handleInput();
	}

	@Override
	public void dispose() {
		System.out.println("Disposing resources...");
		player.dispose();
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
		}
	}

}
