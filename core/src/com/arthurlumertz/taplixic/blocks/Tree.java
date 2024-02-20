package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Tree extends Block {

	@Override
	protected void init() {
		id = 3;
		
		image = new Texture(Gdx.files.internal("tiles/tree.png"));
		
		breakSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/break/tree.ogg"));
		walkingSound = null;
		
		type = Item.AXE;
		drop = new Plank();
		
		destructible = true;
		solid = true;
		liquid = false;
		
		health = 4;
		previousHealth = health;
	}

}
