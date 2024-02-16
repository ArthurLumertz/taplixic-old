package com.arthurlumertz.taplixic.engine;

import java.util.*;

import com.arthurlumertz.taplixic.engine.butterfly.*;
import com.arthurlumertz.taplixic.level.*;
import com.badlogic.gdx.graphics.g2d.*;

import box2dLight.*;

public class EntityRenderer {
	
	public List<Butterfly> butterflies = new ArrayList<Butterfly>();
	
	public EntityRenderer(RayHandler rayHandler) {

		for (int i = 0; i < Level.getSize(); ++i) {
			butterflies.add(new Butterfly(rayHandler));
		}
		
	}
	
	public void render(SpriteBatch batch) {
		for (Butterfly bufferfly : butterflies) {
			bufferfly.render(batch);
		}
	}
	
	public void dispose() {
		for (Butterfly bufferfly : butterflies) {
			bufferfly.dispose();
		}
	}
	
}
