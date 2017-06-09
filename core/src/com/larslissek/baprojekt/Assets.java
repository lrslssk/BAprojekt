package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Assets {

	public static Music DWDIntro;
	public static Music profileCreationSound;
	
	
	public static void load(){
		DWDIntro = Gdx.audio.newMusic(Gdx.files.internal("sounds/misc/DWD.mp3"));
		profileCreationSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/misc/newProfileSound.ogg"));
	}
}
