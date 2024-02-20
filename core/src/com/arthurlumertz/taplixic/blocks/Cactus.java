package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Cactus extends Block {

	@Override
	protected void init() {
		id = 6;
		
		image = new Texture(Gdx.files.internal("tiles/cactus.png"));
		
		breakSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/break/cactus.ogg"));
		walkingSound = null;
		
		type = Item.AXE;
		drop = new com.arthurlumertz.taplixic.items.Cactus();
		
		destructible = true;
		solid = true;
		liquid = false;
		
		health = 2;
		previousHealth = health;
	}

}
