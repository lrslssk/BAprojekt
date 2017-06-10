package com.larslissek.baprojekt;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	
	static int V_WIDTH = 1280;
	static int V_HEIGHT = 720;
	
	@Override
	public void create () {
		Assets.load();
		setScreen(new InterviewScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
