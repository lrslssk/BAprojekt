package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Assets {

	public static Music DWDIntro;
	public static Music profileCreationSound;
	
	
	public static Sprite[] faces;
	public static Sprite[] hair;
	public static Sprite[] glasses;
	public static Sprite[] shirts;
	
	
	public static void load(){
		DWDIntro = Gdx.audio.newMusic(Gdx.files.internal("sounds/misc/DWD.mp3"));
		profileCreationSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/misc/newProfileSound.ogg"));
		
		faces = new Sprite[3];
		hair = new Sprite[25];
		glasses = new Sprite[2];
		shirts = new Sprite[5];
		
		for(int i = 0; i < faces.length; i++){
			faces[i] = new Sprite(new Texture(Gdx.files.internal("avatar/face" + (i+1) + ".png")));
		}
		
		for(int i = 0; i < hair.length; i++){
			hair[i] = new Sprite(new Texture(Gdx.files.internal("avatar/hair" + (i+1) + ".png")));
		}
		
		for(int i = 0; i < glasses.length; i++){
			glasses[i] = new Sprite(new Texture(Gdx.files.internal("avatar/glasses" + (i+1) + ".png")));
		}
		
		for(int i = 0; i < shirts.length; i++){
			shirts[i] = new Sprite(new Texture(Gdx.files.internal("avatar/shirt" + (i+1) + ".png")));
		}
	}
	
	
}
