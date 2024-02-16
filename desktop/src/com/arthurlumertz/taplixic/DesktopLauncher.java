package com.arthurlumertz.taplixic;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.arthurlumertz.taplixic.Taplixic;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
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
