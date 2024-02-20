package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Coal extends Block {

	@Override
	protected void init() {
		id = 12;
		
		image = new Texture(Gdx.files.internal("tiles/coal.png"));
		
		breakSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/break/stone.ogg"));
		walkingSound = null;
		
		type = Item.PICKAXE;
		drop = new com.arthurlumertz.taplixic.items.Coal();
		
		destructible = true;
		solid = true;
		liquid = false;
		
		health = 10;
		previousHealth = health;
	}

}
