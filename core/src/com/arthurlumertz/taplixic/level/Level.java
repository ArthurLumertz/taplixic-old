package com.arthurlumertz.taplixic.level;

import com.arthurlumertz.taplixic.entity.Camera;
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
	private int levelSize;
	private Camera camera;

	public Level(Camera camera) {
		this.camera = camera;

		createTiles(16);

		levelSize = 512;
		level = new int[levelSize][levelSize];

		FileHandle file = Gdx.files.local("level.dat");
		if (file.exists()) {
			load();
		} else {
			generateLevel(tiles);
		}
	}

	public void render(SpriteBatch batch) {
		Frustum frustum = camera.getCamera().frustum;

		for (int x = 0; x < levelSize; ++x) {
			for (int y = 0; y < levelSize; ++y) {
				int xo = x * 48;
				int yo = y * 48;
				int tile = level[xo / 48][yo / 48];

				if (tiles[tile] != null && tiles[tile].getHealth() > 0) {

					BoundingBox tileBoundingBox = new BoundingBox(new Vector3(xo, yo, 0),
							new Vector3(xo + 48, yo + 48, 0));

					if (frustum.boundsInFrustum(tileBoundingBox)) {
						batch.draw(tiles[tile].getImage(), xo, yo, 48, 48);
					}
				}
			}
		}
	}

	private void createTiles(int length) {
		tiles = new Tile[length];

		tiles[0] = new Tile(new Texture(Gdx.files.internal("tiles/grass.png")), false, false);
		tiles[1] = new Tile(new Texture(Gdx.files.internal("tiles/dirt.png")), false, false);
		tiles[2] = new Tile(new Texture(Gdx.files.internal("tiles/stone.png")), true, false);
		tiles[4] = new Tile(new Texture(Gdx.files.internal("tiles/tree.png")), true, true);
		tiles[5] = new Tile(new Texture(Gdx.files.internal("tiles/sand.png")), false, false);
		tiles[6] = new Tile(new Texture(Gdx.files.internal("tiles/water.png")), false, false);
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
				float scaledNoise = perlinNoise.GetNoise(x, y) * 0.5F + 0.5F;
				int tile = 0;

				if (Math.sqrt(Math.pow(x - center, 2) + Math.pow(y - center, 2)) <= radius) {
					tile = 0;
				} else if (scaledNoise > 0.6F) {
					tile = 4;
				} else if (scaledNoise > 0.5F) {
					tile = 0;
				} else if (scaledNoise > 0.3F) {
					tile = 5;
				} else {
					tile = 6;
				}

				level[x][y] = tile;
			}
		}
	}

	public int[][] getLevel() {
		return level;
	}

	public int getSize() {
		return levelSize;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void save() {
		try {
			FileHandle file = Gdx.files.local("level.dat");
			Json json = new Json();
			String data = json.toJson(level);
			file.writeString(data, true);
			System.out.println("Level saved to level.dat");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try {
			FileHandle file = Gdx.files.local("level.dat");
			if (file.exists()) {
				Json json = new Json();
				level = json.fromJson(int[][].class, file.readString());
				System.out.println("Level loaded from level.dat");
			} else {
				throw new RuntimeException("Level file does not exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
