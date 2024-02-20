package com.arthurlumertz.taplixic;

import box2dLight.*;
import com.arthurlumertz.taplixic.entity.*;
import com.arthurlumertz.taplixic.gui.*;
import com.arthurlumertz.taplixic.io.*;
import com.arthurlumertz.taplixic.level.*;
import com.arthurlumertz.taplixic.player.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;

public class Screen extends ScreenAdapter {

    public static float lightness = 1.0F;
    public static float lightnessFactor = 0.00016722F;
    private final SpriteBatch batch;
    public World world;
    public RayHandler rayHandler;
    public Camera camera;
    public Entity player;
    public Level level;
    public EntityRenderer entityRenderer;
    public Gui gui;
    private float elapsedTime;

    public Screen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        gui = new Gui(this);
    }

    public void start(String world) {
        State.STATE = State.PLAY;

        this.world = new World(new Vector2(0, 0), true);
        rayHandler = new RayHandler(this.world);

        camera = new Camera();

        level = new Level(rayHandler, world, camera);

        player = new Player(camera, rayHandler);

        camera.setPosition(player.getPosition());

        entityRenderer = new EntityRenderer(this);
    }

    @Override
    public void render(float delta) {
        if (State.STATE != State.TITLE) {
            elapsedTime += delta;
            if (elapsedTime >= 60.0F) {
                save();
                elapsedTime = 0;
            }

            camera.update(player, batch);
            rayHandler.setCombinedMatrix(camera.getCamera());
            rayHandler.setAmbientLight(lightness);

            batch.begin();
            level.render(batch);
            player.render(batch);
            entityRenderer.render(batch);
            batch.end();

            rayHandler.updateAndRender();
            handleInput();
        }

        if (State.STATE == State.PLAY) {
            lightness -= lightnessFactor;
            if (lightness <= 0.2F || lightness >= 1) {
                lightnessFactor = -lightnessFactor;
            }
        }

        gui.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.gl.glViewport(0, 0, width, height);
        gui.resize(width, height);
    }

    @Override
    public void dispose() {
        save();

        entityRenderer.dispose();
        player.dispose();
        level.dispose();
        rayHandler.dispose();
        gui.dispose();
    }

    public void save() {
        level.save();
        player.save();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(960, 540);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            save();
        }

        /* SWITCHING STATES */
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            if (State.STATE == State.PLAY) {
                State.STATE = State.INVENTORY;
            } else {
                State.STATE = State.PLAY;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (State.STATE == State.PLAY) {
                State.STATE = State.OPTIONS;
            } else {
                State.STATE = State.PLAY;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) || Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            if (State.STATE == State.PLAY) {
                State.STATE = State.CRAFTING;
            } else {
                State.STATE = State.PLAY;
            }
        }
    }

}
