package com.arthurlumertz.taplixic.entity;

import com.arthurlumertz.taplixic.Screen;
import com.arthurlumertz.taplixic.level.*;
import com.arthurlumertz.taplixic.player.Camera;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;

import box2dLight.*;

public class Butterfly extends Entity {

	private Camera camera;
	private RayHandler rayHandler;

	private PointLight pointLight;

	private float gravity = 0.5F;
	private float opacity = 1.0F;

	public Butterfly(Camera camera, RayHandler rayHandler) {
		this.camera = camera;
		this.rayHandler = rayHandler;

		show();
	}

	@Override
	public void show() {
		setPosition(new Vector2(MathUtils.random(0, Level.getSize() * 48), MathUtils.random(0, Level.getSize() * 48)));
		setSize(new Vector2(16, 16));
		setVelocity(new Vector2(MathUtils.random(1, 3), MathUtils.random(1, 3)));

		int res = (int) Math.round(Math.random());
		String resString = res == 1 ? "blue" : "pink";

		Color resColor = new Color();
		if (res == 1) {
			resColor = new Color(0.25F, 0, 1.0F, 0.5F);
		} else {
			resColor = new Color(1.0F, 0, 0.75F, 0.5F);
		}

		setImage(new Texture(Gdx.files.internal("entities/butterfly/" + resString + ".png")));

		pointLight = new PointLight(rayHandler, 100, resColor, 100, position.x, position.y);
	}

	@Override
	public void render(SpriteBatch batch) {
		Frustum frustum = camera.getCamera().frustum;
		BoundingBox boundingBox = new BoundingBox(new Vector3(position.x, position.y, 0),
				new Vector3(position.x + size.x, position.y + size.y, 0));

		if (frustum.boundsInFrustum(boundingBox)) {
			position.x += velocity.x;
			position.y += (velocity.y + gravity);

			if (position.x >= Level.getSize() * 48) {
				velocity.x = -velocity.x;
			}

			if (position.x <= 0) {
				velocity.x = -velocity.x;
			}

			if (position.y >= Level.getSize() * 48) {
				velocity.y = -velocity.y;
			}

			if (position.y <= 0) {
				velocity.y = -velocity.y;
			}

			if (Screen.lightness <= 0.5F) {
				if (opacity < 1.0F)
					opacity += 0.03F;
				pointLight.setActive(true);

				spriteCounter++;
				if (spriteCounter >= 12) {
					gravity = -gravity;
					spriteCounter = 0;
				}
			} else {
				if (opacity > 0.0F)
					opacity -= 0.03F;
				if (opacity <= 0F) {
					pointLight.setActive(false);
					opacity = 0F;
				}
			}

			batch.setColor(1F, 1F, 1F, opacity);
			pointLight.setPosition(position.cpy().add(size.x / 2, size.y / 2));
			batch.draw(getImage(), position.x, position.y, size.x, size.y);
			batch.setColor(Color.WHITE);
		}
	}

	@Override
	public void dispose() {
		getImage().dispose();
		pointLight.dispose();
	}

}