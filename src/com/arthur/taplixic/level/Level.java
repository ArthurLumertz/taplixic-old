package com.arthur.taplixic.level;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import com.arthur.taplixic.*;

public class Level implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Taplixic taplixic;
	
	public int width;
	public int height;

	public byte[] blocks;
	public Tile tiles[] = new Tile[16];

	public Level(Taplixic taplixic, int width, int height) {
		this.taplixic = taplixic;
		
		this.width = width;
		this.height = height;

		this.blocks = new byte[width * height];
		this.createTiles(taplixic.spritesheet);
		
		File levelFile = new File("level.dat");
		if (levelFile.exists()) {
			this.load();
		} else {
			this.generate();
		}
	}

	public void generate() {
		Random random = new Random();

		FastNoise perlin = new FastNoise();
		perlin.SetNoiseType(FastNoise.NoiseType.Perlin);

		long seed = random.nextLong();

		perlin.SetSeed((int) seed);
		perlin.SetFrequency(0.1F);
		perlin.SetFractalOctaves(4);

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				int index = y * width + x;

				float scaledNoise = (float) perlin.GetNoise(x, y) * 0.5F + 0.5F;

				if (scaledNoise > 0.6F) {
					blocks[index] = 5;
				} else if (scaledNoise > 0.5F) {
					byte res = (byte) (random.nextInt(3)); // 0, 1, 2

					if (res == 0) {
						blocks[index] = 0;
					} else if (res == 1) {
						blocks[index] = 4;
					} else { // 2
						if (random.nextDouble() > 0.25D) {
							blocks[index] = (byte) (random.nextInt(2) + 6);
						}
					}

				} else if (scaledNoise > 0.3F) {
					blocks[index] = 3; // sand
				} else if (scaledNoise > 0.2F) {
					blocks[index] = 2; // water
				} else {
					blocks[index] = 2; // water
				}
			}
		}

	}
	
	public void createTiles(Spritesheet spritesheet) {
		// grass
		tiles[0] = new Tile();
		tiles[0].texture = new Texture(taplixic, 0, 0);
		tiles[0].collision = false;

		// earth
		tiles[1] = new Tile();
		tiles[1].texture = new Texture(taplixic, 32, 0);
		tiles[1].collision = false;

		// water
		tiles[2] = new Tile();
		tiles[2].texture = new Texture(taplixic, 64, 0);
		tiles[2].collision = false;

		// sand
		tiles[3] = new Tile();
		tiles[3].texture = new Texture(taplixic, 96, 0);
		tiles[3].collision = false;

		// tree
		tiles[4] = new Tile();
		tiles[4].texture = new Texture(taplixic, 128, 0);
		tiles[4].collision = true;

		// stone
		tiles[5] = new Tile();
		tiles[5].texture = new Texture(taplixic, 160, 0);
		tiles[5].collision = false;

		// rock
		tiles[6] = new Tile();
		tiles[6].texture = new Texture(taplixic, 192, 0);
		tiles[6].collision = false;
		
		// flower
		tiles[7] = new Tile();
		tiles[7].texture = new Texture(taplixic, 224, 0);
		tiles[7].collision = false;
	}

	public void load() {
		try {
			DataInputStream dis = new DataInputStream(new GZIPInputStream(new FileInputStream("level.dat")));
			dis.readFully(this.blocks);
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream("level.dat")));
			dos.write(this.blocks);
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
