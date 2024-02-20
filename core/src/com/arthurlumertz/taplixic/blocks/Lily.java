package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Lily extends Block {

	@Override
	protected void init() {
		id = 10;
		
		image = new Texture(Gdx.files.internal("tiles/lily.png"));
		
		breakSound = null;
		walkingSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/walking/water.ogg"));
		
		type = Item.RESOURCE;
		drop = null;
		
		destructible = false;
		solid = false;
		liquid = false;
	}

}
