package com.arthurlumertz.taplixic;

import com.badlogic.gdx.backends.lwjgl3.*;

public class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowIcon("tiles/dirt.png");
		config.setWindowedMode(960, 540);
		config.setForegroundFPS(60);
		config.setTitle("Taplixic " + Taplixic.getVersion());
		new Lwjgl3Application(new Taplixic(), config);
	}

}
