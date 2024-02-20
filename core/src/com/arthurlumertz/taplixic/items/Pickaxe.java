package com.arthurlumertz.taplixic.items;

import com.arthurlumertz.taplixic.inventory.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Pickaxe extends Item {

	@Override
	protected void init() {
		image = new Texture(Gdx.files.internal("items/pickaxe.png"));
		name = "Pickaxe";
		type = PICKAXE;
		
		damage = 6;
		defense = MIN_DEFENSE;
	}
	
	@Override
	public void craft() {
		Stick stick = new Stick();
		Rock rock = new Rock();

		if (Inventory.countItemOccurrences(stick) >= 2 && Inventory.countItemOccurrences(rock) >= 3) {
			Inventory.remove(stick);
			Inventory.remove(stick);
			Inventory.remove(rock);
			Inventory.remove(rock);
			Inventory.remove(rock);
			Inventory.add(this);
		}
	}

}
