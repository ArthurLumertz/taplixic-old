package com.arthurlumertz.taplixic.particle;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Particle {

	private Vector2 position;
	private Vector2 velocity;
	private float width;
	private float height;
	private float opacity;
	private TextureRegion textureRegion;

	public Particle(SpriteBatch batch, Vector2 position, Texture texture) {
		this.position = position;
		this.velocity = new Vector2(MathUtils.random(5f, 10f) * MathUtils.randomSign(), -30);
		
		this.width = 8;
		this.height = 8;
		this.opacity = 1.0f; // Initial opacity
		this.textureRegion = new TextureRegion(texture, 8, 16, 2, 2);

		this.position.x -= this.width / 2;
		this.position.y -= -this.height;
	}

	public void update(float delta) {
		position.add(velocity.x * delta, velocity.y * delta);

		width += 2 * delta;
		height += 2 * delta;
		opacity -= 0.8f * delta;
	}

	public void render(Batch batch) {
		batch.setColor(0.8F, 0.8F, 0.8F, opacity);
		batch.draw(textureRegion, position.x, position.y, width, height);
		batch.setColor(Color.WHITE);
	}

	public boolean isFinished() {
		return opacity <= 0;
	}
}
