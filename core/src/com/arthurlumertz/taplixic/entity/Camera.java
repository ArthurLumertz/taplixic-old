package com.arthurlumertz.taplixic.entity;

import com.arthurlumertz.taplixic.engine.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Camera {

	private OrthographicCamera camera;

	public Camera() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void update(Entity player, SpriteBatch batch) {
		camera.update();
		camera.position.lerp(
				new Vector3(player.getPosition().x + (player.getSize().x / 2), player.getPosition().y + (player.getSize().y / 2), 0),
				0.05F);
		batch.setProjectionMatrix(camera.combined);
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setPosition(Vector2 vector) {
		camera.position.set(vector.x, vector.y, 0);
	}

	public Vector2 getPosition() {
		return new Vector2(camera.position.x, camera.position.y);
	}

}
