package com.arthurlumertz.taplixic.gui;

import com.arthurlumertz.taplixic.player.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Main {

	private Texture heartBlank;
	private Texture heartHalf;
	private Texture heartFull;

	private Texture staminaBlank;
	private Texture staminaHalf;
	private Texture staminaFull;

	public Main() {
		init();
	}

	public void init() {
		heartBlank = new Texture(Gdx.files.internal("io/heart_blank.png"));
		heartHalf = new Texture(Gdx.files.internal("io/heart_half.png"));
		heartFull = new Texture(Gdx.files.internal("io/heart_full.png"));

		staminaBlank = new Texture(Gdx.files.internal("io/stamina_blank.png"));
		staminaHalf = new Texture(Gdx.files.internal("io/stamina_half.png"));
		staminaFull = new Texture(Gdx.files.internal("io/stamina_full.png"));
	}

	public void render(SpriteBatch batch) {
		batch.begin();

		int indicatorSize = 32;
		int gap = 8;

		for (int i = 0; i < Player.getMaxHealth() / 2; ++i) {
			int x = i * (indicatorSize + gap) + gap;
			Texture heartTexture;

			if (i < Player.getHealth() / 2) {
				heartTexture = heartFull;
			} else if (i == Player.getHealth() / 2 && Player.getHealth() % 2 == 1) {
				heartTexture = heartHalf;
			} else {
				heartTexture = heartBlank;
			}

			batch.draw(heartTexture, x, gap, indicatorSize, indicatorSize);
		}
		
		for (int i = 0; i < Player.getMaxStamina() / 2; ++i) {
			int x = i * (indicatorSize + gap) + gap;
			Texture staminaTexture;

			if (i < Player.getStamina() / 2) {
				staminaTexture = staminaFull;
			} else if (i == Player.getStamina() / 2 && Player.getStamina() % 2 == 1) {
				staminaTexture = staminaHalf;
			} else {
				staminaTexture = staminaBlank;
			}

			batch.draw(staminaTexture, x, (indicatorSize + (int) Math.pow(gap, 2) / 2), indicatorSize, indicatorSize);
		}

		batch.end();
	}

	public void dispose() {
		heartBlank.dispose();
		heartHalf.dispose();
		heartFull.dispose();
	}

}
