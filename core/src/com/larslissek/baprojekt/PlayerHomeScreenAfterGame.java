package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class PlayerHomeScreenAfterGame implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	Texture background;
	
	Drawable drawable;
	
	Stage stage;
	
	float startTimer = 0;
	
	private Texture speechbubble;
	
	BitmapFont font;
	private String currentTextline = "";
	private boolean showBubble = false;
	
	Music sound;
	private boolean soundStarted = false;
	
	public PlayerHomeScreenAfterGame(MyGdxGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		
		batch = new SpriteBatch();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended2.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 29;
		
		font = generator.generateFont(parameter);
		font.setColor(0, 0, 0, 1);
		
		background = new Texture(Gdx.files.internal("testroom.jpg"));
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		sound = Assets.profileCreationSound;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0);
		
		if(showBubble){
			batch.draw(speechbubble, 270, 450, 700, 200);
			font.draw(batch, currentTextline  + "", 350, 615);
		}
		
		batch.end();
		
		stage.act();
		stage.draw();
		
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		startTimer += delta;
		
		
		if(startTimer >= 2 && !soundStarted){
			//TODO entsprechendes Soundfile recorden
			//sound.play();
			showBubble = true;
			soundStarted = true;
			currentTextline = "Sehr gut!";
		}
		
		if(startTimer >= 13){
			//TODO maybe fade-out effect?
			game.setScreen(new NightWorldMap(game));
		}
		
		if(startTimer >= 11.5){
			currentTextline = "Gute Nacht!";
		}
		
		else if(startTimer >= 8){
			currentTextline = "Gehe jetzt bitte schlafen,\ndamit du morgen fit bist.";
		}
		
		else if(startTimer >= 5){
			currentTextline = "Es ist schon spät,\nund du musst morgen\nfrüh zur Schule";
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
		port.update(width, height);
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
