package com.arthurlumertz.taplixic.gui;

import com.arthurlumertz.taplixic.Screen;
import com.arthurlumertz.taplixic.io.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Gui {

    private static Screen sc;
    private static StartScreen start;
    private static Inventory inventory;
    private static Options options;
    private static Crafting crafting;
    private static Main main;
    private final SpriteBatch batch;

    public Gui(Screen sc) {
        Gui.sc = sc;

        batch = new SpriteBatch();

        init();
    }

    public static void init() {
        start = new StartScreen(sc);
        inventory = new Inventory();
        options = new Options(start, sc);
        crafting = new Crafting();
        main = new Main();
    }

    public void render(float delta) {
        Gdx.input.setInputProcessor(null);

        if (State.STATE != State.TITLE) {
            main.render(batch);
        }

        if (State.STATE == State.TITLE) {
            start.render(batch, delta);
            Gdx.input.setInputProcessor(start.getState());
        }

        if (State.STATE == State.INVENTORY) {
            inventory.render(batch);
            Gdx.input.setInputProcessor(inventory.getState());
        }

        if (State.STATE == State.OPTIONS) {
            options.render(batch);
            Gdx.input.setInputProcessor(options.getState());
        }

        if (State.STATE == State.CRAFTING) {
            crafting.render(batch);
            Gdx.input.setInputProcessor(crafting.getState());
        }

    }

    public void resize(int width, int height) {
        if (State.STATE == State.TITLE) {
            start.resize(width, height);
        }

        if (State.STATE == State.INVENTORY) {
            inventory.resize(width, height);
        }

        if (State.STATE == State.OPTIONS) {
            options.resize(width, height);
        }

        if (State.STATE == State.CRAFTING) {
            crafting.resize(width, height);
        }
    }

    public void dispose() {
        start.dispose();
        inventory.dispose();
        options.dispose();
        crafting.dispose();
        main.dispose();
    }

}
