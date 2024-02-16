package com.arthurlumertz.taplixic.level;

import com.arthurlumertz.taplixic.Screen;
import com.arthurlumertz.taplixic.entity.Camera;
import com.arthurlumertz.taplixic.item.*;
import com.arthurlumertz.taplixic.level.Perlin.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;
import com.badlogic.gdx.utils.*;

public class Level {

	private Tile[] tiles;
	private int[][] level;
	private static int levelSize;

	private Camera camera;

	private String world;
	private int worldNum;

	public Level(String world, Camera camera) {
		this.camera = camera;
		this.world = world + "/level.dat";
		this.worldNum = Integer.parseInt(world.replaceAll("[^0-9]", ""));

		createTiles(16);

		levelSize = 512; // TODO: change this depending on what world size the user wants
		level = new int[levelSize][levelSize];

		FileHandle file = Gdx.files.local(this.world);
		if (file.exists()) {
			load();
		} else {
			generateLevel(tiles);
		}

		FileHandle configFile = Gdx.files.local(world + "/config.dat");
		if (configFile.exists()) {
			loadLight();
		} else {
			Screen.lightness = 1.0F;
		}

		saveLight();
	}

	public void render(SpriteBatch batch) {
		Frustum frustum = camera.getCamera().frustum;

		for (int x = 0; x < levelSize; ++x) {
			for (int y = 0; y < levelSize; ++y) {
				int tile = level[x][y];

				int xo = x * 48;
				int yo = y * 48;

				BoundingBox tileBoundingBox = new BoundingBox(new Vector3(xo, yo, 0), new Vector3(xo + 48, yo + 48, 0));

				if (frustum.boundsInFrustum(tileBoundingBox)) {
					batch.draw(tiles[tile].getImage(), xo, yo, 48, 48);
				}
			}
		}
	}

	private void createTiles(int length) {
		tiles = new Tile[length];

		// grass
		Item grassItem = new Item("Grass", "tiles/grass.png", 1, 0);
		tiles[0] = new Tile(grassItem, new Texture(Gdx.files.internal("tiles/grass.png")), false, false, 0, false);
		// dirt
		Item dirtItem = new Item("Dirt", "tiles/dirt.png", 1, 0);
		tiles[1] = new Tile(dirtItem, new Texture(Gdx.files.internal("tiles/dirt.png")), false, false, 0, false);
		// stone
		Item stoneItem = new Item("Rock", "items/rock.png", 1, 0);
		tiles[2] = new Tile(stoneItem, new Texture(Gdx.files.internal("tiles/stone.png")), true, true, 10, false);
		// tree
		Item treeItem = new Item("Plank", "items/plank.png", 1, 0);
		tiles[4] = new Tile(treeItem, new Texture(Gdx.files.internal("tiles/tree.png")), true, true, 5, false);
		// sand
		Item sandItem = new Item("Sand", "tiles/sand.png", 1, 0);
		tiles[5] = new Tile(sandItem, new Texture(Gdx.files.internal("tiles/sand.png")), false, false, 0, false);
		// water
		Item waterItem = new Item("Water", "tiles/water.png", 1, 0);
		tiles[6] = new Tile(waterItem, new Texture(Gdx.files.internal("tiles/water.png")), false, false, 0, true);
		// cactus
		Item cactusItem = new Item("Cactus", "items/cactus.png", 3, 0);
		tiles[7] = new Tile(cactusItem, new Texture(Gdx.files.internal("tiles/cactus.png")), true, true, 3, false);
		// rock
		Item rockItem = new Item("Rock", "items/rock.png", 1, 0);
		tiles[8] = new Tile(rockItem, new Texture(Gdx.files.internal("tiles/rock.png")), false, false, 0, false);
		// flower
		Item flowerItem = new Item("Flower", "tiles/flower.png", 1, 0);
		tiles[9] = new Tile(flowerItem, new Texture(Gdx.files.internal("tiles/flower.png")), false, false, 0, false);
		// bush
		Item bushItem = new Item("Bush", "tiles/bush.png", 1, 0);
		tiles[10] = new Tile(bushItem, new Texture(Gdx.files.internal("tiles/bush.png")), false, false, 1, false);
		// lily
		Item lilyItem = new Item("Lily", "tiles/lily.png", 1, 0);
		tiles[11] = new Tile(lilyItem, new Texture(Gdx.files.internal("tiles/lily.png")), false, false, 1, false);
	}

	private void generateLevel(Tile[] tiles) {
		Perlin perlinNoise = new Perlin();
		perlinNoise.SetNoiseType(NoiseType.Perlin);
		perlinNoise.SetSeed((int) Math.floor(Math.random() * 1000000000));
		perlinNoise.SetFrequency(0.1F);
		perlinNoise.SetFractalOctaves(4);

		int center = levelSize / 2;
		int radius = 4;

		for (int x = 0; x < levelSize; ++x) {
			for (int y = 0; y < levelSize; ++y) {
				float scaledNoise = perlinNoise.GetNoise(x, y) * 1.0F + 0.4F;
				int tile = 0;

				if (Math.sqrt(Math.pow(x - center, 2) + Math.pow(y - center, 2)) <= radius) {
					int res = (int) Math.round(Math.random() * 20) + 1;
					tile = res <= 1 ? 8 : res <= 3 ? 9 : 0; // grass and rock
				} else if (scaledNoise >= 0.65F) {
					tile = 2;
				} else if (scaledNoise >= 0.55F) {
					int res = (int) Math.round(Math.random() * 20) + 1;
					tile = res <= 1 ? 10 : 4; // bush or tree
				} else if (scaledNoise > 0.5F) {
					int res = (int) Math.round(Math.random() * 20) + 1;
					tile = res <= 1 ? 8 : res <= 3 ? 9 : 0; // grass and rock
				} else if (scaledNoise > 0.4F) {
					int res = (int) Math.round(Math.random() * 20) + 1;
					tile = res <= 1 ? 7 : 5; // sand or cactus
				} else {
					int res = (int) Math.round(Math.random() * 20) + 1;
					tile = res <= 1 ? 11 : 6; // lily or water
				}

				level[x][y] = tile;
			}
		}
	}

	public int[][] getLevel() {
		return level;
	}

	public static int getSize() {
		return levelSize;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void saveLight() {
		try {
			FileHandle configFile = Gdx.files.local("level0" + worldNum + "/config.dat");
			String data = "" + Screen.lightness;
			configFile.writeString(data, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadLight() {
		try {
			FileHandle configFile = Gdx.files.local("level0" + worldNum + "/config.dat");
			if (configFile.exists()) {
				Screen.lightness = Float.parseFloat(configFile.readString());
			} else {
				throw new RuntimeException("Failed to load level0" + worldNum + "/config.dat!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			FileHandle file = Gdx.files.local(world);
			Json json = new Json();
			String data = json.toJson(level);
			file.writeString(data, false);
			saveLight();

			System.out.println("Level saved to " + world);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getWorld() {
		return world;
	}

	public int getWorldNum() {
		return worldNum;
	}

	public void load() {
		try {
			FileHandle file = Gdx.files.local(world);
			if (file.exists()) {
				Json json = new Json();
				level = json.fromJson(int[][].class, file.readString());
				System.out.println("Level loaded from " + world);
			} else {
				throw new RuntimeException("Level file does not exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
