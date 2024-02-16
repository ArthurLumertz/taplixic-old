package com.arthurlumertz.taplixic.gui;

import com.arthurlumertz.taplixic.Screen;
import com.arthurlumertz.taplixic.io.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Gui {
	
	private Screen sc;
	
	private SpriteBatch batch;
	
	private StartScreen start;
	private Inventory inventory;

	public Gui(Screen sc, SpriteBatch batch) {
		this.sc = sc;
		this.batch = batch;
		
		init();
	}
	
	public void init() {
		start = new StartScreen(sc);
		inventory = new Inventory();
	}
	
	public void render(float delta) {
		if (State.STATE == State.TITLE) {
			start.render(batch, delta);
			Gdx.input.setInputProcessor(start.getState());
		}
		
		if (State.STATE == State.INVENTORY) {
			inventory.render(batch);
			Gdx.input.setInputProcessor(inventory.getState());
		}
	}
	
	public void resize(int width, int height) {
		if (State.STATE == State.TITLE) {
			start.resize(width, height);
		}
		
		if (State.STATE == State.INVENTORY) {
			inventory.resize(width, height);
		}
	}
	
	public void dispose() {
		start.dispose();
		inventory.dispose();
	}
	
}
