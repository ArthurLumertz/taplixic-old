package com.arthurlumertz.taplixic.items;

import com.arthurlumertz.taplixic.inventory.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Stick extends Item {

	@Override
	protected void init() {
		image = new Texture(Gdx.files.internal("items/stick.png"));
		name = "Stick";
		type = RESOURCE;
		
		damage = MIN_DAMAGE;
		defense = MIN_DEFENSE;
	}
	
	@Override
	public void craft() {
		Plank plank = new Plank();
		
		if (Inventory.countItemOccurrences(plank) >= 1) {
			Inventory.remove(plank);
			Inventory.add(this);
		}
	}

}
