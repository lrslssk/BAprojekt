package com.larslissek.baprojekt;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ParkGame implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	OrthographicCamera camUI;
	StretchViewport port;
	StretchViewport portUI;
	SpriteBatch batch;
	
	Stage stage;
	Stage stage2;
	
	
	FreeTypeFontGenerator generator;
	BitmapFont font;
	
	Texture parkMap;
	
	
	private float bubbletimer;
	private Texture speechbubble;
	private Image speechbubbleImage;
	private float newXpos;
	private float newYpos;
	private float newZoomLevel;
	
	private int currentDirection = -1;
	private Vector2 currentPosition;
	
	private ArrayList<String> directions;
	
	float beginTimer = 0;
	private boolean showPositionMarker;
	
	Texture positionMarker;
	
	ImageButton arrow1;
	ImageButton arrow2;
	
	Music bruecke, erstekreuzung, fluss, halloagent, pyramide, rechtenweg, standpunkt, weg, ziel;
	private boolean pyramideNotYetPlayed = true;
	private float endGameTimer;
	
	public ParkGame(MyGdxGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

		bruecke = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/bruecke.ogg"));
		erstekreuzung = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/erstekreuzung.ogg"));
		fluss = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/fluss.ogg"));
		halloagent = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/halloagent.ogg"));
		pyramide = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/pyramide.ogg"));
		rechtenweg = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/rechtenweg.ogg"));
		standpunkt = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/standpunkt.ogg"));
		weg = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/weg.ogg"));
		ziel = Gdx.audio.newMusic(Gdx.files.internal("sounds/parkgame/ziel.ogg"));
		
		
		
		newXpos = port.getWorldWidth() / 2;
		newYpos = port.getWorldHeight() / 2;
		newZoomLevel = 1f;
		
		positionMarker = new Texture(Gdx.files.internal("redcross.png"));
		
		directions = new ArrayList<String>();
		
		directions.add("      Hallo Agent!");
		directions.add("Ich erkläre dir nun den \n Weg zum Wetterschacht.");
		directions.add("Dein aktueller Standpunkt\nwird auf der Karte markiert.");
		directions.add("An der ersten Kreuzung\n   gehe nach Norden.");
		directions.add("");
		directions.add("Laufe über die Brücke.");
		directions.add("Gehe in Richtung\n  der Pyramide.");
		directions.add(""); //7
		directions.add("Überquere den Fluss.");
		directions.add(""); //9
		directions.add("Nehme an der Gabelung den\n       rechten Weg.");
		directions.add(""); //11
		directions.add("  Super! Du hast \nden Wetterschacht gefunden!");
		
		camUI = new OrthographicCamera();
		portUI = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, camUI);
		camUI.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		
		batch = new SpriteBatch();
		
		currentPosition = new Vector2(100, 100);
		
		stage = new Stage(portUI, batch);
		stage2 = new Stage(port, batch);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(stage2);
		Gdx.input.setInputProcessor(multiplexer);
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 29;
		parameter.color = Color.BLACK;
		font = generator.generateFont(parameter);
		
		
		parkMap = new Texture(Gdx.files.internal("parkplan_klein.jpg"));
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		speechbubbleImage = new Image(speechbubble);
		speechbubbleImage.setBounds(450, 600, 400, 100);
		
		stage.addActor(speechbubbleImage);
		
		arrow1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("arrow.png")))));
		arrow2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("arrow.png")))));
		
		arrow1.setSize(100, 100);
		arrow2.setSize(100, 100);
		
		arrow1.setVisible(false);
		arrow2.setVisible(false);
		
		
		arrow1.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	System.out.println("arrow 1 clicked");
	        	currentDirection++;
	        }
	    });
		
		arrow2.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	System.out.println("arrow 2 clicked");
	        	//TODO wrong turn, switch to football game
	        }
	    });
		
		stage2.addActor(arrow1);
		stage2.addActor(arrow2);
		
	}

	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		//test
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(parkMap, 0, 0);
		
		
		if(showPositionMarker){
			batch.draw(positionMarker, currentPosition.x, currentPosition.y, 30, 30);
		}

//		if(arrow1.isVisible())
//		arrow1.draw(batch, 1.0f);
//		
//		if(arrow2.isVisible())
//		arrow2.draw(batch, 1.0f);
		
		batch.end();
		
		
		
		batch.setProjectionMatrix(camUI.combined);
		batch.begin();
		
		if(bubbletimer > 0){
			batch.draw(speechbubble, 260, 500, 800, 200);
			font.draw(batch, directions.get(currentDirection) + "", 455, 655);
			bubbletimer -= delta;
		}
		
		
		batch.end();
		
//		stage.act();
//		stage.draw();
		stage2.act();
		stage2.draw();
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		camUI.update();
		
		
		if(beginTimer < 2){
			beginTimer += delta;
		}
		
		else if(currentDirection == -1){
			currentDirection++;
			bubbletimer = 3;
			halloagent.play();
		}
		
		if(currentDirection == 0 && bubbletimer <= 0){
			currentDirection++;
			bubbletimer = 7;
			weg.play();
		}
		
		if(currentDirection == 1 && bubbletimer <= 0){
			currentDirection++;
			bubbletimer = 7;
			currentPosition.set(95, 35);
			showPositionMarker = true;
			standpunkt.play();
		}
		
		if(currentDirection == 2 && bubbletimer <= 0){
			
			newXpos = 320;
			newYpos = 180;
			newZoomLevel = 0.5f;
		}
		
		if(currentDirection == 2 && bubbletimer <= 0 && cam.position.x <= 325){
			bubbletimer = Integer.MAX_VALUE;
			currentDirection++;
			erstekreuzung.play();
		}
		
		
		/*
		 * FIRST DECISION
		 */
		
		if(currentDirection == 3){
			arrow1.setPosition(85, 150);
			arrow2.setPosition(210, 70);
			
			arrow1.setVisible(true);
			arrow2.setVisible(true);
			
			arrow1.setSize(100, 50);
			arrow2.setSize(100, 50);
			
			arrow1.getImage().setRotation(-90f);
			arrow2.getImage().setRotation(170f);
		}
		
		if(currentDirection == 4){
			arrow1.setVisible(false);
			arrow2.setVisible(false);
			
			showPositionMarker = false;
			
			bubbletimer = 0;
			
			newXpos = 330;
			newYpos = 250;
		}
		
		if(currentDirection == 4 && cam.position.y > 245){
			arrow1.setVisible(true);
			arrow2.setVisible(true);
			
			arrow1.setPosition(255, 275);
			arrow2.setPosition(290, 140);
			
			arrow1.getImage().setRotation(-150f);
			arrow2.getImage().setRotation(140f);
			
			showPositionMarker = true;
			currentPosition.set(205, 183);
			
			bubbletimer = Integer.MAX_VALUE;
			currentDirection++;
			bruecke.play();
		}
		
		if(currentDirection == 6){
			arrow1.setPosition(190, 285);
			arrow2.setPosition(375, 245);
			
			arrow1.getImage().setRotation(-35f);
			arrow2.getImage().setRotation(160f);
			
			showPositionMarker = true;
			currentPosition.set(275, 250);
			if(pyramideNotYetPlayed){
				pyramide.play();
				pyramideNotYetPlayed = false;
			}
		}
		
		
		if(currentDirection == 7){
			arrow1.setVisible(false);
			arrow2.setVisible(false);
			
			showPositionMarker = false;
			
			bubbletimer = 0;
			
			newXpos = 331;
			newYpos = 450;
		}
		
		if(currentDirection == 7 && cam.position.y >= 445){
			arrow1.setVisible(true);
			arrow2.setVisible(true);
			
			showPositionMarker = true;
			currentPosition.set(142, 395);
			
			arrow1.setPosition(80, 470);
			arrow2.setPosition(238, 450);
			
			arrow1.getImage().setRotation(-68f);
			arrow2.getImage().setRotation(200f);
			
			
			bubbletimer = Integer.MAX_VALUE;
			currentDirection++;
			fluss.play();
		}
		
		
		if(currentDirection == 9){
			arrow1.setVisible(false);
			arrow2.setVisible(false);
			
			showPositionMarker = false;
			
			bubbletimer = 0;
			
			newXpos = 345;
			newYpos = 540;
		}
		
		if(currentDirection == 9 && cam.position.y >= 535){
			arrow1.setVisible(true);
			arrow2.setVisible(true);
			
			showPositionMarker = true;
			currentPosition.set(295, 530);
			
			arrow1.setPosition(405, 555);
			arrow2.setPosition(395, 610);
			
			arrow1.getImage().setRotation(-181f);
			arrow2.getImage().setRotation(195f);
			
			
			bubbletimer = Integer.MAX_VALUE;
			currentDirection++;
			rechtenweg.play();
		}
		
		
		if(currentDirection == 11){
			arrow1.setVisible(false);
			arrow2.setVisible(false);
			
			showPositionMarker = false;
			
			bubbletimer = 0;
			
			newXpos = 405;
			newYpos = 540;
			newZoomLevel = 0.3f;
		}
		
		if(currentDirection == 11 && cam.position.x >= 400){
			showPositionMarker = true;
			currentPosition.set(379, 526);
			
			bubbletimer = 7;
			currentDirection++;
			ziel.play();
			
		}
		
		if(currentDirection == 12){
			endGameTimer += delta;
		}
		
		
		
		
		
		
		
		
		
		if(newXpos != cam.position.x && newYpos != cam.position.y)
			updateCamPosition(delta, newXpos, newYpos);
		
		if(newZoomLevel != cam.zoom)
			updateCamZoom(delta, newZoomLevel);
		
		if(endGameTimer > 5){
			game.setScreen(new MainMenu(game));
		}
		
	}
	
	 public void updateCamPosition(float delta, float Xtarget, float Ytarget) {
	        Vector3 target = new Vector3(Xtarget, Ytarget, 0); 
	        Vector3 cameraPosition = cam.position;
	        final float speed = delta, ispeed = 1.0f - speed;
	        cameraPosition.scl(ispeed);
	        target.scl(speed);
	        cameraPosition.add(target);
	        cam.position.set(cameraPosition);
	}
	 
	public void updateCamZoom(float delta, float newZoom){
		if(cam.zoom > newZoom){
			cam.zoom -= delta / 2.3f;
		}
	}

	@Override
	public void resize(int width, int height) {
		port.update(width, height);
		portUI.update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}

