package com.arthur.taplixic.item;

import com.arthur.taplixic.*;

public class Items {
	public Item[] items = new Item[16];
	
	public static final int FIST = 0;
	
	public Items(Taplixic taplixic) {
		items[FIST] = new Item();
		items[FIST].name = "Fist";
		items[FIST].description = "Your hands";
		items[FIST].texture = new Texture(taplixic, 0, 0);
	}

}
