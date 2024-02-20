package com.arthurlumertz.taplixic;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Pixmap.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Taplixic extends Game {
    public static final String version = "v0.013";
    public static final String versionReleaseDate = "NaN";
    private SpriteBatch batch;

    public static String getVersion() {
        return version;
    }

    public static String getVersionDate() {
        return versionReleaseDate;
    }

    @Override
    public void create() {
        initializeGraphics();
        setScreen(new Screen(batch));
        setCursor();
    }

    private void initializeGraphics() {
        batch = new SpriteBatch();
    }

    private void setCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("io/cursor.png"));
        Pixmap scaled = scalePixmap(pixmap, 32, 32);
        setCursor(scaled);
        disposePixmaps(pixmap, scaled);
    }

    private Pixmap scalePixmap(Pixmap source, int width, int height) {
        Pixmap scaled = new Pixmap(width, height, source.getFormat());
        scaled.setFilter(Filter.NearestNeighbour);
        scaled.drawPixmap(source, 0, 0, source.getWidth(), source.getHeight(), 0, 0, width, height);
        return scaled;
    }

    private void setCursor(Pixmap pixmap) {
        Cursor cursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
        Gdx.graphics.setCursor(cursor);
    }

    private void disposePixmaps(Pixmap... pixmaps) {
        for (Pixmap pixmap : pixmaps) {
            pixmap.dispose();
        }
    }

    @Override
    public void render() {
        clearScreen();
        super.render();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }
}
