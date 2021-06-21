package com.tianya.springboot.javafx.screen;

import de.felixroske.jfxsupport.SplashScreen;

public class CustomScreen extends SplashScreen {
	
	@Override
	public boolean visible() {
		return true;
	}
	
	@Override
	public String getImagePath() {
		return "/img/screen.jpg";
	}

}
