package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.arthurlumertz.taplixic.items.Rock;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Stone extends Block {

	@Override
	protected void init() {
		id = 2;
		
		image = new Texture(Gdx.files.internal("tiles/stone.png"));
		
		breakSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/break/stone.ogg"));
		walkingSound = null;
		
		type = Item.PICKAXE;
		drop = new Rock();
		
		destructible = true;
		solid = true;
		liquid = false;
		
		health = 10;
		previousHealth = health;
	}

}
