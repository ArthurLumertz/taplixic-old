package com.arthurlumertz.taplixic.items;

import box2dLight.*;
import com.arthurlumertz.taplixic.inventory.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Torch extends Item {

    @Override
    protected void init() {
        image = new Texture(Gdx.files.internal("items/torch/1.png"));

        anim1 = new Texture(Gdx.files.internal("items/torch/1.png"));
        anim2 = new Texture(Gdx.files.internal("items/torch/2.png"));
        anim3 = new Texture(Gdx.files.internal("items/torch/3.png"));
        anim4 = new Texture(Gdx.files.internal("items/torch/4.png"));

        name = "Torch";
        type = PLACEABLE;

        damage = MIN_DAMAGE;
        defense = MIN_DEFENSE;

        spriteNumber = 1;
        spriteCounter = 0;

        solid = false;
    }

    public void render(SpriteBatch batch, RayHandler rayHandler) {
        if (!dirty) {
            PointLight pointLight = new PointLight(rayHandler, 100, new Color(0xffa500aa), 250, position.x + (48 / 2), position.y + (48 / 2));
            dirty = true;
        }
        spriteCounter++;
        if (spriteCounter > 20) {
            switch (spriteNumber) {
                case 1:
                    spriteNumber = 2;
                    break;
                case 2:
                    spriteNumber = 3;
                    break;
                case 3:
                    spriteNumber = 4;
                    break;
                case 4:
                    spriteNumber = 1;
                    break;
            }
            spriteCounter = 0;
        }

        switch (spriteNumber) {
            case 1:
                image = anim1;
                break;
            case 2:
                image = anim2;
                break;
            case 3:
                image = anim3;
                break;
            case 4:
                image = anim4;
                break;
        }

        //System.out.println(spriteNumber);
        //pointLight.setPosition(position.cpy().add(size.x / 2, size.y / 2));
        batch.draw(image, position.x, position.y, 48, 48);
    }

    @Override
    public void craft() {
        Stick stick = new Stick();
        Coal coal = new Coal();

        if (Inventory.countItemOccurrences(stick) >= 1 && Inventory.countItemOccurrences(coal) >= 1) {
            Inventory.remove(stick);
            Inventory.remove(coal);
            Inventory.add(this);
        }
    }

}
