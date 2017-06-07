package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class IOController {

	public static Preferences prefs = Gdx.app.getPreferences("DWDprefs");

	
	
	public static boolean doesProfileExist() {
		
		if(IOController.prefs.contains("existingprofile"))
			return true;
		
		else
			return false;
			
	}
	
	public static void createProfile(String name){
		
		prefs.putString("profilename", name);
		prefs.putString("existingprofile", "yes");
		prefs.flush();
		
	}
	
	public static int getProfileScore(){
		return prefs.getInteger("profilescore", -1);
	}
	
	public static void addToProfileScore(int amount){
		prefs.putInteger("profilescore", prefs.getInteger("profilescore", -1) + amount);
	}
	
	public static void deleteProfile(){
		prefs.remove("existingprofile");
		prefs.remove("profilename");
		prefs.flush();
	}
}


