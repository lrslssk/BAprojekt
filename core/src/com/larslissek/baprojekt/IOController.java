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
		return prefs.getInteger("profilescore", 0);
	}
	
	public static void addToProfileScore(int amount){
		prefs.putInteger("profilescore", prefs.getInteger("profilescore", 0) + amount);
		prefs.flush();
	}
	
	public static void deleteProfile(){
		prefs.remove("existingprofile");
		prefs.remove("profilename");
		prefs.flush();
	}
	
	public static String getProfile1Name(){
		return prefs.getString("profilename", "Error");
	}
	
	public static void saveAvatar(int face, int hair, int shirt, int glasses){
		prefs.putInteger("face", face);
		prefs.putInteger("hair", hair);
		prefs.putInteger("shirt", shirt);
		prefs.putInteger("glasses", glasses);
		prefs.flush();
	}
	
	public static int[] getAvatar(){
		int[] result = new int[4];
		
		result[0] = prefs.getInteger("face");
		result[1] = prefs.getInteger("hair");
		result[2] = prefs.getInteger("shirt");
		result[3] = prefs.getInteger("glasses");
		
		return result;
	}
	
	public static void saveSchool(int school){
		prefs.putInteger("school", school);
		prefs.flush();
	}
}


