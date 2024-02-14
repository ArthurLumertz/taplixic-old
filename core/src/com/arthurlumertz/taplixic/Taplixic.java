package com.arthurlumertz.taplixic;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;

public class Taplixic extends Game {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		Screen sc = new Screen(batch, shapeRenderer);
		super.setScreen(sc);

		Pixmap pixmap = new Pixmap(Gdx.files.internal("io/cursor.png"));
		Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
		Gdx.graphics.setCursor(cursor);
		pixmap.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		super.dispose();
	}
}
