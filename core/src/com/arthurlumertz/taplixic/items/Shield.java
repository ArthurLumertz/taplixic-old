package com.arthurlumertz.taplixic.items;

import com.arthurlumertz.taplixic.inventory.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Shield extends Item {

	@Override
	protected void init() {
		image = new Texture(Gdx.files.internal("items/shield.png"));
		name = "Shield";
		type = SHIELD;
		
		damage = MIN_DAMAGE;
		defense = 4;
	}
	
	@Override
	public void craft() {
		Rock rock = new Rock();

		if (Inventory.countItemOccurrences(rock) >= 4) {
			Inventory.remove(rock);
			Inventory.remove(rock);
			Inventory.remove(rock);
			Inventory.remove(rock);
			Inventory.add(this);
		}
	}

}
