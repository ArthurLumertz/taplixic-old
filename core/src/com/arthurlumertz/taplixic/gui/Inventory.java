package com.arthurlumertz.taplixic.gui;

import com.arthurlumertz.taplixic.engine.*;
import com.arthurlumertz.taplixic.item.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;

public class Inventory {
	private Stage stage;
	private Skin skin;
	private Table table;
	private FreeTypeFontGenerator generator;
	private Label itemDetailsLabel;

	private SpriteBatch inventoryBatch;

	private Texture cursorImage;
	private int cursorX, cursorY;

	private Sound inventoryAudio;

	public Inventory() {
		inventoryBatch = new SpriteBatch();

		cursorImage = new Texture(Gdx.files.internal("io/select.png"));
		inventoryAudio = Gdx.audio.newSound(Gdx.files.internal("sound/select.ogg"));
		
		cursorX = 8;
	    cursorY = Gdx.graphics.getHeight() - 48 - 108 + 8;

		init();
	}

	public Stage getState() {
		return stage;
	}

	public void init() {
		stage = new Stage();

		skin = new Skin();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PixelifySans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
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

		table = new Table();
		table.setBackground(textureRegionDrawable);
		table.setFillParent(true);
		table.top();
		stage.addActor(table);

		LabelStyle labelStyle = new LabelStyle();
		font.getData().scale(1.75F);
		labelStyle.font = skin.getFont("default");

		Label label = new Label("Inventory", labelStyle);
		table.add(label).padTop(10);
		table.row();

		labelStyle.font = skin.getFont("smallDefault");
		itemDetailsLabel = new Label("", labelStyle);
		itemDetailsLabel.setAlignment(Align.left);
		table.add(itemDetailsLabel).expand().top().right().pad(40).padRight(96);
	}

	public void render(SpriteBatch batch) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		int itemSize = 64;
		int padding = 8;
		int itemsPerRow = 6;
		int shiftDown = (128 + padding);

		inventoryBatch.begin();
		for (int i = 0; i < Player.getInventory().size(); i++) {
			Item item = Player.getInventory().get(i);

			if (item != null) {
				int row = i / itemsPerRow;
				int col = i % itemsPerRow;

				int x = col * (itemSize + padding);
				int y = Gdx.graphics.getHeight() - (row + 1) * (itemSize + padding) - shiftDown;
				inventoryBatch.draw(item.getImage(), x + padding, y + padding, itemSize, itemSize);

				if (cursorX >= x && cursorX <= x + itemSize && cursorY >= y && cursorY <= y + itemSize) {
					String details = "Name: " + item.getName() + "\nDamage: " + item.getDamage() + "\nDefense: " + item.getDefense();
					itemDetailsLabel.setText(details);
				}
			}
		}

		int cursorRow = (Gdx.graphics.getHeight() - cursorY - shiftDown - padding) / (itemSize + padding);
		int cursorCol = cursorX / (itemSize + padding);
		int cursorXPos = cursorCol * (itemSize + padding);
		int cursorYPos = Gdx.graphics.getHeight() - (cursorRow + 1) * (itemSize + padding) - shiftDown + padding;
		inventoryBatch.draw(cursorImage, cursorXPos + padding, cursorYPos, itemSize + padding / 2 - 1,
				itemSize + padding / 2 - 1);

		inventoryBatch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (cursorRow > 0) {
				inventoryAudio.play();
				cursorRow--;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if (cursorRow < Player.getInventory().size() / itemsPerRow) {
				inventoryAudio.play();
				cursorRow++;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			if (cursorCol > 0) {
				inventoryAudio.play();
				cursorCol--;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			if (cursorCol < itemsPerRow - 1) {
				inventoryAudio.play();
				cursorCol++;
			}
		}

		cursorX = cursorCol * (itemSize + padding);
		cursorY = Gdx.graphics.getHeight() - cursorRow * (itemSize + padding) - itemSize - shiftDown + padding;
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
