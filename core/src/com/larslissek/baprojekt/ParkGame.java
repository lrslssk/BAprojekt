package com.larslissek.baprojekt;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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
	private String currentItemName;
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
	
	public ParkGame(MyGdxGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		//cam.zoom = 0.4f;
		
		newXpos = port.getWorldWidth() / 2;
		newYpos = port.getWorldHeight() / 2;
		newZoomLevel = 1f;
		
		positionMarker = new Texture(Gdx.files.internal("redcross.png"));
		
		directions = new ArrayList<String>();
		
		directions.add("      Hallo <Name>!");
		directions.add("Ich erkläre dir nun den \n Weg zum Wetterschacht.");
		directions.add("Dein aktueller Standpunkt\nwird auf der Karte markiert.");
		directions.add("An der ersten Kreuzung\n   gehe nach Norden.");
		
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
	        }
	    });
		
		arrow2.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	System.out.println("arrow 2 clicked");
	        }
	    });
		
		stage2.addActor(arrow1);
		stage2.addActor(arrow2);
		
//		arrow1.rotateBy(90f);
//		arrow2.rotateBy(90f);
	}

	
	protected void showSpeechBubble(String string) {
		bubbletimer = 5f;
		currentItemName = string;
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
		}
		
		if(currentDirection == 0 && bubbletimer <= 0){
			currentDirection++;
			bubbletimer = 7;
		}
		
		if(currentDirection == 1 && bubbletimer <= 0){
			currentDirection++;
			bubbletimer = 7;
			currentPosition.set(95, 35);
			showPositionMarker = true;
		}
		
		if(currentDirection == 2 && bubbletimer <= 0){
			
			newXpos = 320;
			newYpos = 180;
			newZoomLevel = 0.5f;
		}
		
		if(currentDirection == 2 && bubbletimer <= 0 && cam.position.x <= 325){
			bubbletimer = Integer.MAX_VALUE;
			currentDirection++;
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
		
		if(newXpos != cam.position.x && newYpos != cam.position.y)
			updateCamPosition(delta, newXpos, newYpos);
		
		if(newZoomLevel != cam.zoom)
			updateCamZoom(delta, newZoomLevel);
		
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
			cam.zoom -= delta / 4.5f;
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

