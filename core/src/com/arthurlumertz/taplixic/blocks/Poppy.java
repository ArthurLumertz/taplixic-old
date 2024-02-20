package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Poppy extends Block {

	@Override
	protected void init() {
		id = 11;
		
		image = new Texture(Gdx.files.internal("tiles/poppy.png"));
		
		breakSound = null;
		walkingSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/walking/grass.ogg"));
		
		type = Item.RESOURCE;
		drop = null;
		
		destructible = false;
		solid = false;
		liquid = false;
	}

}
