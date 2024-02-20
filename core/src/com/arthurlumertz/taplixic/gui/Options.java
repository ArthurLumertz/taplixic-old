package com.arthurlumertz.taplixic.gui;

import com.arthurlumertz.taplixic.Screen;
import com.arthurlumertz.taplixic.io.*;
import com.badlogic.gdx.*;
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

public class Options {
	
	private StartScreen start;
	private Screen sc;
	
	private Stage stage;
	private Skin skin;
	private Table table;
	private FreeTypeFontGenerator generator;

	public Options(StartScreen start, Screen sc) {
		this.start = start;
		this.sc = sc;
		init();
	}

	public Stage getState() {
		return stage;
	}

	public void init() {
		stage = new Stage();

		skin = new Skin();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PixelifySans-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 16;
		parameter.shadowColor = Color.BLACK;
		parameter.shadowOffsetX = 1;
		parameter.shadowOffsetY = 1;

		BitmapFont font = generator.generateFont(parameter);
		font.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		skin.add("default", font);

		BitmapFont fontSmall = generator.generateFont(parameter);
		fontSmall.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		skin.add("smallDefault", fontSmall);

		Pixmap backgroundPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		backgroundPixmap.setColor(new Color(0F, 0F, 0F, 0.75F));
		backgroundPixmap.fill();

		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(
				new TextureRegion(new Texture(backgroundPixmap)));
		
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("smallDefault");
		skin.add("default", textButtonStyle);
		
		table = new Table();
		table.setBackground(textureRegionDrawable);
		table.setFillParent(true);
		table.top();
		stage.addActor(table);

		LabelStyle labelStyle = new LabelStyle();
		font.getData().scale(1.75F);
		labelStyle.font = skin.getFont("default");

		Label label = new Label("Options", labelStyle);
		table.add(label).padTop(10).padBottom(64);
		table.row();
		
		TextButton continueButton = new TextButton("Continue", textButtonStyle);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				State.STATE = State.PLAY;
			}
		});
		continueButton.pad(10);
		table.add(continueButton).width(210);
		table.row();
		
		TextButton exitTitleButton = new TextButton("Quit and save", textButtonStyle);
		exitTitleButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sc.save();
				sc.player.dispose();
				sc.level.dispose();
				start.init();
			}
		});
		exitTitleButton.pad(10);
		table.add(exitTitleButton).width(210);
		table.row();
	}

	public void render(SpriteBatch batch) {
		stage.act(Gdx.graphics.getDeltaTime());
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

}
