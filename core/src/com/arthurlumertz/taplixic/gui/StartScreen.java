package com.arthurlumertz.taplixic.gui;

import com.arthurlumertz.taplixic.*;
import com.arthurlumertz.taplixic.Screen;
import com.arthurlumertz.taplixic.io.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class StartScreen {
	private Stage stage;
	private Skin skin;
	private FreeTypeFontGenerator generator;

	private Texture dirt;

	private int worldLimit = 3;

	public Stage getState() {
		return stage;
	}

	public StartScreen(Screen sc) {
		Sound selectAudio = Gdx.audio.newSound(Gdx.files.internal("sound/select.ogg"));

		stage = new Stage();

		skin = new Skin();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PixelifySans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 18;
		parameter.shadowColor = Color.BLACK;
		parameter.shadowOffsetX = 1;
		parameter.shadowOffsetY = 1;

		BitmapFont font = generator.generateFont(parameter);
		font.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		skin.add("default", font);

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		Table table = new Table();
		table.setFillParent(true);
		table.top();
		stage.addActor(table);

		Texture logo = new Texture(Gdx.files.internal("logo.png"));
		Image image = new Image(logo);
		table.add(image).padTop(64);
		table.row();

		for (int i = 0; i <= worldLimit; ++i) {
			final int worldNumber = i;

			String message = "Load level " + worldNumber;

			FileHandle folder = Gdx.files.local("level0" + worldNumber);
			if (folder.isDirectory()) {
				double folderSizeInKB = getFolderSizeInKB(folder);
				message = "Load level " + worldNumber + " (" + String.format("%.2f", folderSizeInKB) + " MB)";
			}

			TextButton button = new TextButton(message, skin);
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					selectAudio.play();
					sc.start("level0" + worldNumber);
					State.STATE = State.PLAY;
					dispose();
				}
			});
			button.pad(10);
			table.add(button).width(210);
			table.row();
		}

		TextButton button = new TextButton("Quit", skin);
		button.setSize(110, 50);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		button.pad(10);
		table.add(button).width(210).padTop(10);
		table.row();

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");

		Label byWhom = new Label("Taplixic " + Taplixic.getVersion(), labelStyle);
		byWhom.setPosition(10, 10);
		stage.addActor(byWhom);

		Label lastUpdate = new Label("Last update " + Taplixic.getVersionDate(), labelStyle);
		lastUpdate.setPosition(Gdx.graphics.getWidth() - (lastUpdate.getWidth() + 10), 10);
		stage.addActor(lastUpdate);

		init();
	}

	public void init() {
		dirt = new Texture(Gdx.files.internal("tiles/dirt.png"));
	}

	public void render(SpriteBatch batch, float delta) {
		batch.begin();
		batch.setColor(new Color(1F, 1F, 1F, 0.6F));
		for (int x = 0; x < 20; ++x) {
			for (int y = 0; y < 16; ++y) {
				int xo = x * 48;
				int yo = y * 48;
				batch.draw(dirt, xo, yo, 48, 48);
			}
		}
		batch.setColor(Color.WHITE);
		batch.end();

		stage.act(delta);
		stage.draw();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void dispose() {
		generator.dispose();
		stage.dispose();
		skin.dispose();
	}

	private static double getFolderSizeInKB(FileHandle folder) {
		if (!folder.isDirectory()) {
			long kilobytes = folder.length() / 1024;
			return (double) (kilobytes / 1024.0);
		}

		float sizeInKB = 0;
		FileHandle[] files = folder.list();

		for (FileHandle file : files) {
			sizeInKB += getFolderSizeInKB(file);
		}

		return sizeInKB;
	}

}
