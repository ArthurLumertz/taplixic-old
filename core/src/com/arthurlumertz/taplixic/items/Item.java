package com.arthurlumertz.taplixic.items;

import box2dLight.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public abstract class Item {

    public static final int MIN_DAMAGE = 1;
    public static final int MIN_DEFENSE = 0;
    public static final int INSTANT_BREAK = 1;
    public static final int SWORD = 0;
    public static final int PICKAXE = 1;
    public static final int AXE = 3;
    public static final int SHIELD = 4;
    public static final int RESOURCE = 5;
    public static final int PLACEABLE = 6;
    protected Texture image;
    protected Texture anim1, anim2, anim3, anim4;
    protected String name;
    protected int damage;
    protected int defense;
    protected int type;
    protected Vector2 position;
    protected Sound walkingSound;
    protected Sound breakSound;
    protected int previousHealth;
    protected int health;
    protected int spriteCounter;
    protected int spriteNumber;
    protected boolean solid;
    protected boolean dirty;

    public Item() {
        init();
    }

    public static int getMinDamage() {
        return MIN_DAMAGE;
    }

    public static int getMinDefense() {
        return MIN_DEFENSE;
    }

    public static int getInstantBreak() {
        return INSTANT_BREAK;
    }

    public static int getSword() {
        return SWORD;
    }

    public static int getPickaxe() {
        return PICKAXE;
    }

    public static int getAxe() {
        return AXE;
    }

    public static int getShield() {
        return SHIELD;
    }

    public static int getResource() {
        return RESOURCE;
    }

    public static int getPlaceable() {
        return PLACEABLE;
    }

    protected abstract void init();

    public void render(SpriteBatch batch, RayHandler rayHandler) {
    }

    public abstract void craft();

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Texture getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getType() {
        return type;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public Sound getWalkingSound() {
        return walkingSound;
    }

    public Sound getBreakSound() {
        return breakSound;
    }

    public int getPreviousHealth() {
        return previousHealth;
    }

    public int getHealth() {
        return health;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isDirty() {
        return dirty;
    }

}
