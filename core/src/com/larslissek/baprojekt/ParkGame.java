package com.larslissek.baprojekt;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ParkGame implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	OrthographicCamera camUI;
	StretchViewport port;
	StretchViewport portUI;
	SpriteBatch batch;
	
	Stage stage;
	
	
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
		
		directions = new ArrayList<String>();
		
		directions.add("Hallo <Name>!");
		directions.add("Ich erkläre dir nun den Weg zum Wetterschacht.");
		directions.add("Dein aktueller Standpunkt wird auf der Karte markiert.");
		
		camUI = new OrthographicCamera();
		portUI = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, camUI);
		camUI.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		
		batch = new SpriteBatch();
		
		
		
		stage = new Stage(portUI, batch);
		Gdx.input.setInputProcessor(stage);
		
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
		
		

		
		batch.end();
		
		
		
		batch.setProjectionMatrix(camUI.combined);
		batch.begin();
		
		if(bubbletimer > 0){
			batch.draw(speechbubble, 450, 600, 400, 100);
			font.draw(batch, directions.get(currentDirection) + "", 495, 675);
			bubbletimer -= delta;
		}
		
		batch.end();
		
//		stage.act();
//		stage.draw();
		
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
		
		if(currentDirection == 0 && bubbletimer == 0){
			currentDirection++;
			bubbletimer = 3;
		}
		
		if(newXpos != cam.position.x && newYpos != cam.position.y)
			updateCamPosition(delta, newXpos, newYpos);
		
		if(newZoomLevel != cam.zoom)
			updateCamZoom(delta, newZoomLevel);
		
		
//		if(Gdx.input.isTouched()){
//			newXpos = 250;
//			newYpos = 140;
//			newZoomLevel = 0.5f;
//		}
	}
	
	 public void updateCamPosition(float delta, float Xtarget, float Ytarget) {
	        Vector3 target = new Vector3(Xtarget, Ytarget, 0); 
	        Vector3 cameraPosition = cam.position;
	        final float speed = delta, ispeed = 1.0f - (speed * 0.7f);
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

