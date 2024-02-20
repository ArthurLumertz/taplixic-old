package com.arthurlumertz.taplixic.items;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Rock extends Item {

	@Override
	protected void init() {
		image = new Texture(Gdx.files.internal("items/rock.png"));
		name = "Rock";
		type = RESOURCE;
		
		damage = MIN_DAMAGE;
		defense = MIN_DEFENSE;
	}
	
	@Override
	public void craft() {
		
	}

}
