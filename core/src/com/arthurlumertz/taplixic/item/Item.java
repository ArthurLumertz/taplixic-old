package com.arthurlumertz.taplixic.item;

import com.badlogic.gdx.graphics.*;

public class Item {

	private Texture image;
	private String imagePath;
	private String name;
	private int damage;
	private int defense;

	public Item() {
	}

	public Item(String name, String imagePath, int damage, int defense) {
		this.name = name;
		this.imagePath = imagePath;
		this.image = new Texture(imagePath);
		this.damage = damage;
		this.defense = defense;
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	

}
