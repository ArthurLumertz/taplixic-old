package com.arthurlumertz.taplixic.particle;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Particle {

    private final Vector4 position;
    private final Vector2 velocity;
    private final TextureRegion textureRegion;
    private float opacity;

    public Particle(Vector2 position, Texture texture) {
        this.position = new Vector4(position, 8, 8);
        this.velocity = new Vector2(MathUtils.random(5.0F, 10.0F) * MathUtils.randomSign(), -30);

        this.opacity = 1.0F;
        this.textureRegion = new TextureRegion(texture, 8, 16, 2, 2);

        this.position.x -= this.position.z / 2;
        this.position.y -= -this.position.w;
    }

    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta, 0, 0);

        position.z += 2 * delta;
        position.w += 2 * delta;
        opacity -= 0.95F * delta;
    }

    public void render(SpriteBatch batch) {
        batch.setColor(0.8F, 0.8F, 0.8F, opacity);
        batch.draw(textureRegion, position.x, position.y, position.z, position.w);
        batch.setColor(Color.WHITE);
    }

    public boolean isFinished() {
        return opacity <= 0;
    }
}
