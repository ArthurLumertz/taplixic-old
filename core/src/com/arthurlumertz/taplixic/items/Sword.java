package com.arthurlumertz.taplixic.items;

import com.arthurlumertz.taplixic.inventory.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Sword extends Item {

    @Override
    protected void init() {
        image = new Texture(Gdx.files.internal("items/sword.png"));
        name = "Sword";
        type = SWORD;

        damage = 6;
        defense = MIN_DEFENSE;
    }

    @Override
    public void craft() {
        Stick stick = new Stick();
        Rock rock = new Rock();

        if (Inventory.countItemOccurrences(stick) >= 1 && Inventory.countItemOccurrences(rock) >= 2) {
            Inventory.remove(stick);
            Inventory.remove(stick);
            Inventory.remove(rock);
            Inventory.remove(rock);
            Inventory.add(this);
        }
    }

}
