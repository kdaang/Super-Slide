package com.superslide.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.superslide.game.SuperSlide;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SuperSlide.WIDTH;
		config.height = SuperSlide.HEIGHT;
		config.title = SuperSlide.TITLE;
		new LwjglApplication(new SuperSlide(), config);
	}
}
