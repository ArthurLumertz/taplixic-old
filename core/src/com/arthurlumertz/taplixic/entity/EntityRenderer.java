package com.arthurlumertz.taplixic.entity;

import java.util.*;

import com.arthurlumertz.taplixic.*;
import com.arthurlumertz.taplixic.level.*;
import com.badlogic.gdx.graphics.g2d.*;

public class EntityRenderer {
	
	public List<Butterfly> butterflies = new ArrayList<Butterfly>();
	
	public EntityRenderer(Screen sc) {
		for (int i = 0; i < Level.getSize(); ++i) {
			butterflies.add(new Butterfly(sc.camera, sc.rayHandler));
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
