package com.arthurlumertz.taplixic.gui;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;

import java.util.List;
import java.util.*;

public class Crafting {
    private final SpriteBatch craftingBatch;
    private final Texture cursorImage;
    private final Sound craftingAudio;
    private final List<Item> craftingList = new ArrayList<Item>();
    private Stage stage;
    private Skin skin;
    private FreeTypeFontGenerator generator;
    private Label itemDetailsLabel;
    private int cursorX = 48;
    private int cursorY = 396;

    public Crafting() {
        craftingBatch = new SpriteBatch();

        cursorImage = new Texture(Gdx.files.internal("io/select.png"));
        craftingAudio = Gdx.audio.newSound(Gdx.files.internal("sound/select.ogg"));

        init();
        createCraftingList();
    }

    public Stage getState() {
        return stage;
    }

    public void init() {
        stage = new Stage();

        skin = new Skin();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PixelifySans-Regular.ttf"));
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

        Table table = new Table();
        table.setBackground(textureRegionDrawable);
        table.setFillParent(true);
        table.top();
        stage.addActor(table);

        LabelStyle labelStyle = new LabelStyle();
        font.getData().scale(1.75F);
        labelStyle.font = skin.getFont("default");

        Label label = new Label("Crafting", labelStyle);
        table.add(label).padTop(10);
        table.row();

        labelStyle.font = skin.getFont("smallDefault");
        itemDetailsLabel = new Label("", labelStyle);
        itemDetailsLabel.setAlignment(Align.left);
        table.add(itemDetailsLabel).expand().top().right().pad(40).padRight(96);
    }

    public void render(SpriteBatch batch) {
        updateStage();
        drawInventory();
    }

    private void updateStage() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void createCraftingList() {
        craftingList.add(new Stick());
        craftingList.add(new Torch());
        craftingList.add(new Sword());
        craftingList.add(new Pickaxe());
        craftingList.add(new Axe());
        craftingList.add(new Shield());
    }

    private void drawInventory() {
        craftingBatch.begin();

        final int xStart = 48;
        final int yStart = Gdx.graphics.getHeight() - (48 * 4);
        final int slotSize = 48;

        int xSlot = xStart;
        int ySlot = yStart;

        for (int i = 0; i < craftingList.size(); i++) {
            Item item = craftingList.get(i);

            if (isCursorOnItem(xSlot, ySlot)) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//					System.out.println(item.getName());
                    craftingAudio.play();
                    item.craft();
                }

                updateItemDetailsLabel(item);
            }

            craftingBatch.draw(item.getImage(), xSlot, ySlot, slotSize, slotSize);
            xSlot += slotSize;

            if ((i + 1) % 6 == 0) {
                xSlot = xStart;
                ySlot -= slotSize;
            }
        }

        updateCursor(xStart, yStart, slotSize);
        craftingBatch.draw(cursorImage, cursorX, cursorY, 48, 48);

        craftingBatch.end();
    }

    private boolean isCursorOnItem(int xSlot, int ySlot) {
        return cursorX >= xSlot && cursorX <= xSlot && cursorY >= ySlot && cursorY <= ySlot;
    }

    private void updateItemDetailsLabel(Item item) {
        String details = "Name: " + item.getName() + "\nDamage: " + item.getDamage() + "\nDefense: "
                + item.getDefense();
        itemDetailsLabel.setText(details);
    }

    private void updateCursor(int xStart, int yStart, int slotSize) {
        int snappedCursorX = MathUtils.clamp((Gdx.input.getX() / 48) * 48, xStart, xStart + (slotSize * 6) - 48);
        int snappedCursorY = Gdx.graphics.getHeight()
                - (MathUtils.clamp(Gdx.input.getY() / 48, 0, (yStart - slotSize) / 48) * 48) - 48;

        snappedCursorX = MathUtils.clamp(snappedCursorX, xStart, xStart + (slotSize * 6) - 48);
        snappedCursorY = MathUtils.clamp(snappedCursorY, 0, yStart);

        if (cursorX != snappedCursorX || cursorY != snappedCursorY) {
            craftingAudio.play();
        }

        cursorX = snappedCursorX;
        cursorY = snappedCursorY;
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
