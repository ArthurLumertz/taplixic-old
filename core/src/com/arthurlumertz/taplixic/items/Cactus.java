package com.arthurlumertz.taplixic.items;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Cactus extends Item {

	@Override
	protected void init() {
		image = new Texture(Gdx.files.internal("items/cactus.png"));
		name = "Cactus";
		type = RESOURCE;
		
		damage = 2;
		defense = MIN_DEFENSE;
	}
	
	@Override
	public void craft() {
		
	}

}
