package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Sand extends Block {

	@Override
	protected void init() {
		id = 4;
		
		image = new Texture(Gdx.files.internal("tiles/sand.png"));
		
		breakSound = null;
		walkingSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/walking/sand.ogg"));
		
		type = Item.RESOURCE;
		drop = null;
		
		destructible = false;
		solid = false;
		liquid = false;
	}

}
