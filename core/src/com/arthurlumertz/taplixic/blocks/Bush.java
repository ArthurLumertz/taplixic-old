package com.arthurlumertz.taplixic.blocks;

import com.arthurlumertz.taplixic.items.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Bush extends Block {

	@Override
	protected void init() {
		id = 9;
		
		image = new Texture(Gdx.files.internal("tiles/bush.png"));
		
		breakSound = null;
		walkingSound = Gdx.audio.newSound(Gdx.files.internal("tiles/sounds/walking/grass.ogg"));
		
		type = Item.SWORD;
		drop = null;
		
		destructible = false;
		solid = false;
		liquid = false;
	}

}
