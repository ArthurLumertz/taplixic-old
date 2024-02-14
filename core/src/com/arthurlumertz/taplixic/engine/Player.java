package com.arthurlumertz.taplixic.engine;

import com.arthurlumertz.taplixic.entity.*;
import com.arthurlumertz.taplixic.entity.Camera;
import com.arthurlumertz.taplixic.level.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import box2dLight.*;

public class Player extends Entity {

	private Camera camera;
	private Level level;
	
	private RayHandler rayHandler;
	private PointLight pointLight;

	public Player(Camera camera, RayHandler rayHandler, Level level) {
		this.camera = camera;
		this.rayHandler = rayHandler;
		this.level = level;
	}

	@Override
	public void show() {
		setSpeed(5);

		setPosition(new Vector2((level.getSize() * 48) / 2, (level.getSize() * 48) / 2));
		setSize(new Vector2(48, 48));

		setIdle1(new Texture(Gdx.files.internal("player/idle/1.png")));
		setIdle2(new Texture(Gdx.files.internal("player/idle/2.png")));

		setAnim1(new Texture(Gdx.files.internal("player/anim/1.png")));
		setAnim2(new Texture(Gdx.files.internal("player/anim/2.png")));

		setImage(idle1);
		
		setBoundingBox(new Rectangle(position.x, position.y, size.x, size.y));
		setAttackingBox(new Rectangle(position.x, position.y, 4, 4));
		
		pointLight = new PointLight(rayHandler, 100, new Color(1, 1, 1, 0.2F), 300, position.x, position.y);
	}

	@Override
	public void render(SpriteBatch batch) {
		up = Gdx.input.isKeyPressed(Input.Keys.W);
		down = Gdx.input.isKeyPressed(Input.Keys.S);
		left = Gdx.input.isKeyPressed(Input.Keys.A);
		right = Gdx.input.isKeyPressed(Input.Keys.D);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			attacking = true;
		}

		spriteCounter++;
		if (spriteCounter > 30) {
			if (spriteNumber == 1) {
				spriteNumber = 2;
			} else {
				spriteNumber = 1;
			}
			spriteCounter = 0;
		}
		
		if (attacking) {
			// update the attacking box to the players position depending on the direction the player is facing
			if (up) setAttackingBox(new Rectangle(position.x, position.y + 48, 4, 4));
			else if (down) setAttackingBox(new Rectangle(position.x, position.y - 48, 4, 4));
			else if (left) setAttackingBox(new Rectangle(position.x - 48, position.y, 4, 4));
			else if (right) setAttackingBox(new Rectangle(position.x + 48, position.y, 4, 4));
			
			// get the current tile being attacked
			int attackingTileX = (int) (getAttackingBox().x / 48);
			int attackingTileY = (int) (getAttackingBox().y / 48);
			int attackingTileIndex = level.getLevel()[attackingTileX][attackingTileY];
			Tile attackingTile = level.getTiles()[attackingTileIndex];
			
			if (attackingTile.isDestructible()) {
				attackingTile.damage(2);
				System.out.println(attackingTile.getHealth());
			}
			
			attackingCounter++;
			if (attackingCounter > 30) {
				// switch to attacking texture (in this case im just flipping the texture
				if (spriteNumber == 1) spriteNumber = 2;
				else if (spriteNumber == 2) spriteNumber = 1;
				attacking = false;
				attackingCounter = 0;
			}
		}
		
		//System.out.println(attacking);

		if (up || down || left || right) {
			Vector2 newPosition = new Vector2(position.x, position.y);
			
			if (up) newPosition.y += speed;
			if (down) newPosition.y -= speed;
			if (left) newPosition.x -= speed;
			if (right) newPosition.x += speed;

			setBoundingBox(new Rectangle(newPosition.x, newPosition.y, size.x, size.y));
			collision = Collision.checkEntity(level, camera, this);
			
			if (!collision) {
				setPosition(newPosition);
			}
			
			if (spriteNumber == 1)
				image = anim1;
			else if (spriteNumber == 2)
				image = anim2;
		} else {
			if (spriteNumber == 1)
				image = idle1;
			else if (spriteNumber == 2)
				image = idle2;
		}
		
		pointLight.setPosition(position.cpy().add(size.x / 2, size.y / 2));
		batch.draw(image, position.x, position.y, size.x, size.y);
	}

	@Override
	public void dispose() {
		getImage().dispose();
	}

}
