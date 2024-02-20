package com.arthurlumertz.taplixic.level;

import box2dLight.*;
import com.arthurlumertz.taplixic.Screen;
import com.arthurlumertz.taplixic.blocks.Cactus;
import com.arthurlumertz.taplixic.blocks.Coal;
import com.arthurlumertz.taplixic.blocks.Rock;
import com.arthurlumertz.taplixic.blocks.*;
import com.arthurlumertz.taplixic.inventory.*;
import com.arthurlumertz.taplixic.items.*;
import com.arthurlumertz.taplixic.level.Perlin.*;
import com.arthurlumertz.taplixic.player.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;
import com.badlogic.gdx.utils.*;

import java.lang.StringBuilder;
import java.util.*;

public class Level {

    public static final String LEVEL_DIRECTORY = "saves/";
    private static final List<Item> placedItems = new ArrayList<Item>();
    private static Sound placeSound;
    private static Block[] blocks;
    private static int[][] level;
    private static int levelSize;
    private static String world;
    private final Camera camera;
    private final RayHandler rayHandler;

    public Level(RayHandler rayHandler, String world, Camera camera) {
        this.camera = camera;
        this.rayHandler = rayHandler;
        Level.world = LEVEL_DIRECTORY + world + "/";

        createTiles();

        levelSize = 512; // TODO: change this depending on what world size the user wants
        level = new int[levelSize][levelSize];

        placeSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/break/stone.ogg"));

        FileHandle file = Gdx.files.local(Level.world);
        if (file.isDirectory()) {
            load();
        } else {
            generateLevel(blocks);
        }

        save();
    }

    public static Block[] getBlocks() {
        return blocks;
    }

    public static int[][] getLevel() {
        return level;
    }

    public static int getSize() {
        return levelSize;
    }

    public static String getWorld() {
        return world;
    }

    public static void addPlacedItem(Item item) {
        placeSound.play();
        placedItems.add(item);
    }

    private void createTiles() {
        blocks = new Block[16];

        Grass grass = new Grass();
        blocks[grass.getID()] = grass;

        Dirt dirt = new Dirt();
        blocks[dirt.getID()] = dirt;

        Stone stone = new Stone();
        blocks[stone.getID()] = stone;

        Tree tree = new Tree();
        blocks[tree.getID()] = tree;

        Sand sand = new Sand();
        blocks[sand.getID()] = sand;

        Water water = new Water();
        blocks[water.getID()] = water;

        Cactus cactus = new Cactus();
        blocks[cactus.getID()] = cactus;

        Rock rock = new Rock();
        blocks[rock.getID()] = rock;

        Dandelion dandelion = new Dandelion();
        blocks[dandelion.getID()] = dandelion;

        Bush bush = new Bush();
        blocks[bush.getID()] = bush;

        Lily lily = new Lily();
        blocks[lily.getID()] = lily;

        Poppy poppy = new Poppy();
        blocks[poppy.getID()] = poppy;

        Coal coal = new Coal();
        blocks[coal.getID()] = coal;
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
                    batch.draw(blocks[tile].getImage(), xo, yo, 48, 48);
                }
            }
        }

        for (Item item : placedItems) {
            item.render(batch, rayHandler);
        }
    }

    private void generateLevel(Block[] tiles) {
        Perlin perlinNoise = new Perlin();
        perlinNoise.SetNoiseType(NoiseType.Perlin);
        perlinNoise.SetSeed((int) Math.floor(Math.random() * 1000000000));
        perlinNoise.SetFrequency(0.1F);
        perlinNoise.SetFractalOctaves(4);

        int center = levelSize / 2;
        int radius = 4;

        for (int x = 0; x < levelSize; ++x) {
            for (int y = 0; y < levelSize; ++y) {
                float scaledNoise = perlinNoise.GetNoise(x, y) + 0.4F;
                int tile = 0;

                int res = (int) Math.round(Math.random() * 20) + 1;

                if (Math.sqrt(Math.pow(x - center, 2) + Math.pow(y - center, 2)) <= radius) {
                    tile = res <= 1 ? 7 : res <= 3 ? 8 : res <= 4 ? 11 : 0; // grass and rock and flower
                } else if (scaledNoise >= 0.65F) {
                    tile = res <= 2 ? 12 : 2; // coal or rock
                } else if (scaledNoise >= 0.55F) {
                    tile = res <= 1 ? 9 : 3; // bush or tree
                } else if (scaledNoise > 0.5F) {
                    tile = res <= 1 ? 7 : res <= 3 ? 8 : res <= 4 ? 11 : 0; // grass and rock and flower
                } else if (scaledNoise > 0.4F) {
                    tile = res <= 1 ? 6 : 4; // sand or cactus
                } else {
                    tile = res <= 1 ? 10 : 5; // lily or water
                }

                level[x][y] = tile;
            }
        }
    }

    public void save() {
        try {

            System.out.println("Saving level...");

            Json json = new Json();
            FileHandle file = null;
            String data = null;

            // world
            file = Gdx.files.local(world + "world.json");
            data = json.toJson(level);
            file.writeString(data, false);

            // config
            file = Gdx.files.local(world + "config.json");
            data = json.toJson(Screen.lightness);
            file.writeString(data, false);

            // placed items
            file = Gdx.files.local(world + "placed.json");
            StringBuilder placedItemsData = new StringBuilder();
            for (Item item : placedItems) {
                placedItemsData.append(item.getName()).append(":").append(item.getPosition().x / 48).append(",").append(item.getPosition().y / 48).append("\n");
            }
            file.writeString(placedItemsData.toString(), false);

        } catch (Exception e) {
            System.err.println("Something went wrong when saving the player!");
            e.printStackTrace();
        }
    }

    public void load() {
        try {

            System.out.println("Loading level...");

            FileHandle file = null;
            Json json = new Json();

            // world
            file = Gdx.files.local(world + "world.json");
            level = json.fromJson(int[][].class, file.readString());

            // config
            file = Gdx.files.local(world + "config.json");
            Screen.lightness = json.fromJson(float.class, file);

            // placed items
            file = Gdx.files.local(world + "placed.json");
            if (file.exists()) {
                String[] lines = file.readString().split("\n");
                for (String line : lines) {
                    String[] lineSplit = line.split(":");
                    String[] linePositionSplit = lineSplit[1].split(",");
                    Item item = Inventory.getItemByName(lineSplit[0]);
                    item.setPosition(new Vector2(Float.parseFloat(linePositionSplit[0]) * 48, Float.parseFloat(linePositionSplit[1]) * 48));
                    placedItems.add(item);
                }
            }

        } catch (Exception e) {
            System.err.println("Something went wrong when loading the level!");
            e.printStackTrace();
        }
    }

    public void dispose() {
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            if (block != null) {
                block.dispose();
            }
        }
    }

}
