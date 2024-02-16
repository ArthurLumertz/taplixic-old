package com.arthurlumertz.taplixic.engine;

import java.util.*;

import com.arthurlumertz.taplixic.entity.*;
import com.arthurlumertz.taplixic.entity.Camera;
import com.arthurlumertz.taplixic.io.*;
import com.arthurlumertz.taplixic.item.*;
import com.arthurlumertz.taplixic.level.*;
import com.arthurlumertz.taplixic.particle.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;

import box2dLight.*;

public class Player extends Entity {

	private Camera camera;
	private static Level level;

	private RayHandler rayHandler;
	private PointLight pointLight;

	private final List<Particle> particles = new ArrayList<Particle>();

	private int prevSpeed = 0;

	private float elapsedTime;

	public Player(Camera camera, RayHandler rayHandler, Level level) {
		this.camera = camera;
		this.rayHandler = rayHandler;
		Player.level = level;

		FileHandle file = Gdx.files.local("level0" + level.getWorldNum() + "/player");
		if (file.exists()) {
			show();
			load();
		} else {
			show();
		}
		
		save();
	}

	@Override
	public void show() {
		setSpeed(3);
		prevSpeed = getSpeed();

		setPosition(new Vector2((Level.getSize() * 48) / 2, (Level.getSize() * 48) / 2));
		setSize(new Vector2(48, 48));

		setIdle1(new Texture(Gdx.files.internal("player/idle/1.png")));
		setIdle2(new Texture(Gdx.files.internal("player/idle/2.png")));

		setAnim1(new Texture(Gdx.files.internal("player/anim/1.png")));
		setAnim2(new Texture(Gdx.files.internal("player/anim/2.png")));

		setLiquid1(new Texture(Gdx.files.internal("player/liquid/1.png")));
		setLiquid2(new Texture(Gdx.files.internal("player/liquid/2.png")));

		setImage(idle1);

		setBoundingBox(new Rectangle(position.x + 8, position.y + 12, size.x - 16, size.y - 24));
		setAttackingBox(new Rectangle(position.x, position.y, 4, 4));

		setAttackSound(Gdx.audio.newSound(Gdx.files.internal("player/sound/attack.ogg")));

		pointLight = new PointLight(rayHandler, 100, new Color(1, 1, 1, 0.4F), 300, position.x, position.y);
	}

	@Override
	public void render(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		if (elapsedTime >= 60.0F) {
			save();
			elapsedTime = 0;
		}

		if (State.STATE == State.PLAY) {
			handleInput();

			if (attacking) {
				attack(batch);
			}

			if (up || down || left || right) {
				Vector2 newPosition = new Vector2(position.x, position.y);

				if (up)
					newPosition.y += speed;
				if (down)
					newPosition.y -= speed;
				if (left)
					newPosition.x -= speed;
				if (right)
					newPosition.x += speed;

				setBoundingBox(new Rectangle(newPosition.x + 8, newPosition.y + 12, size.x - 16, size.y - 24));
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

			for (Particle particle : particles) {
				particle.update(Gdx.graphics.getDeltaTime());
				particle.render(batch);
			}

			// get the current tile being attacked
			int tileX = (int) (getPosition().x / 48);
			int tileY = (int) (getPosition().y / 48);
			Tile currentTile = level.getTiles()[level.getLevel()[tileX][tileY]];

			if (currentTile.isLiquid()) {
				setSpeed(2);

				if (spriteNumber == 1)
					image = liquid1;
				else if (spriteNumber == 2)
					image = liquid2;
			} else {
				setSpeed(prevSpeed);
				prevSpeed = getSpeed();
			}
		}

		batch.setProjectionMatrix(camera.getCamera().combined);
		pointLight.setPosition(position.cpy().add(size.x / 2, size.y / 2));
		batch.draw(image, position.x, position.y, size.x, size.y);

	}

	private void attack(SpriteBatch batch) {
		float reach = 16F;

		switch (direction) {
		case "up":
			setAttackingBox(new Rectangle(position.x, position.y + (size.y), size.x, size.y));
			break;
		case "down":
			setAttackingBox(new Rectangle(position.x, position.y - (size.y - reach), size.x, size.y));
			break;
		case "left":
			setAttackingBox(new Rectangle(position.x - (size.x - reach), position.y, size.x, size.y));
			break;
		case "right":
			setAttackingBox(new Rectangle(position.x + (size.x), position.y, size.x, size.y));
			break;
		}

		// get the current tile being attacked
		int attackingTileX = (int) (getAttackingBox().x / 48);
		int attackingTileY = (int) (getAttackingBox().y / 48);
		Tile attackingTile = level.getTiles()[level.getLevel()[attackingTileX][attackingTileY]];

		if (attackingTile.isDestructible() && !attackingTile.isInvincible()) {
			// play the attack sound
			getAttackSound().play(2.0F);
			attackingTile.setHealth(attackingTile.getHealth() - 1);

			Particle particle = new Particle(batch,
					new Vector2((attackingTileX * 48) + (48 / 2), (attackingTileY * 48) + (48 / 2)),
					attackingTile.getImage());
			particles.add(particle);
			particles.add(particle);

			if (attackingTile.getHealth() <= 0) {
				// when tile destroyed
				if (inventory.size() < 24) {
					inventory.add(attackingTile.getItem());
				} else {
					System.out.println("Inventory full!");
				}
				level.getLevel()[attackingTileX][attackingTileY] = 1;
				attackingTile.setHealth(attackingTile.getPrevHealth());
			}

			attackingTile.setInvincible(true);
		}

		attackingCounter++;
		if (attackingCounter > 10) {
			attackingTile.setInvincible(false);
			// switch to attacking texture (in this case im just flipping the texture
			if (spriteNumber == 1)
				spriteNumber = 2;
			else if (spriteNumber == 2)
				spriteNumber = 1;
			attacking = false;
			attackingCounter = 0;
		}
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			up = true;
			direction = "up";
		} else {
			up = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			down = true;
			direction = "down";
		} else {
			down = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			left = true;
			direction = "left";
		} else {
			left = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			right = true;
			direction = "right";
		} else {
			right = false;
		}

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
	}

	@Override
	public void dispose() {
		getImage().dispose();
		particles.removeIf(Particle::isFinished);
	}

	public void save() {
		try {
			FileHandle file;
			Json json = new Json();

			// speed
			file = Gdx.files.local("level0" + level.getWorldNum() + "/player/speed.dat");
			file.writeString(json.toJson(speed), false);

			// position
			file = Gdx.files.local("level0" + level.getWorldNum() + "/player/position.dat");
			file.writeString(json.toJson(position), false);

			// inventory
			FileHandle inventoryFile = Gdx.files.local("level0" + level.getWorldNum() + "/player/inventory.dat");
			StringBuilder inventoryData = new StringBuilder();
			for (Item item : inventory) {
				inventoryData.append(item.getName()).append(",").append(item.getImagePath()).append(",")
						.append(item.getDamage()).append("\n");
			}
			inventoryFile.writeString(inventoryData.toString(), false);

			System.out.println("Player saved to level0" + level.getWorldNum() + "/player");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Player load() {
		try {
			FileHandle folder = Gdx.files.local("level0" + level.getWorldNum() + "/player");
			if (folder.isDirectory()) {
				FileHandle file;
				Json json = new Json();

				// speed
				file = Gdx.files.local("level0" + level.getWorldNum() + "/player/speed.dat");
				speed = json.fromJson(int.class, file.readString());
				System.out.println(speed);

				// position
				file = Gdx.files.local("level0" + level.getWorldNum() + "/player/position.dat");
				position = json.fromJson(Vector2.class, file.readString());

				// inventory
				FileHandle inventoryFile = Gdx.files.local("level0" + level.getWorldNum() + "/player/inventory.dat");
				String[] inventoryData = inventoryFile.readString().split("\n");
				inventory.clear();
				for (String itemString : inventoryData) {
					String[] itemArray = itemString.split(",");
					Item item = new Item();
					item.setName(itemArray[0]);
					item.setImagePath(itemArray[1]);
					item.setImage(new Texture(itemArray[1]));
					item.setDamage(Integer.parseInt(itemArray[2]));
					inventory.add(item);
//					System.out.println("Added item! " + item.getName());
				}

				System.out.println("Player loaded from level0" + level.getWorldNum() + "/player");
			} else {
				throw new RuntimeException("Player file does not exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
