package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SchoolScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	
	Sprite background;
	
	
	Sprite paul;
	float startTimer = 0;
	private float paulAlpha = 0;
	
	private Texture speechbubble;
	
	BitmapFont font;
	private String currentTextline = "";
	private boolean showBubble = false;
	
	Music sound;
	private boolean soundStarted = false;
	
	public SchoolScreen(MyGdxGame game) {
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
		
		switch (IOController.getSchool()) {
		case 0:
			background = new Sprite(new Texture(Gdx.files.internal("schule1big.jpg")));
			break;
		case 1:
			background = new Sprite(new Texture(Gdx.files.internal("schule2big.jpg")));
			break;
		case 2:
			background = new Sprite(new Texture(Gdx.files.internal("schule3big.jpg")));
			break;

		default:
			break;
		}
		
		
		paul = new Sprite(new Texture(Gdx.files.internal("paul.png")));
		paul.setPosition(100, 20);
		
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
		background.draw(batch);
		
		paul.draw(batch, paulAlpha);
		
		if(showBubble){
			batch.draw(speechbubble, -30, 350, 700, 200);
			font.draw(batch, currentTextline  + "", 50, 515);
		}
		
		batch.end();
		
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		background.setBounds(0, 0, 1280, 720);
		
		startTimer += delta;
		
		if(startTimer >= 2 && paulAlpha + delta / 4 <= 0.99){
			paulAlpha += delta / 4;
		}
		
		else if(startTimer >= 2 && !soundStarted){
			//TODO record right soundfile
			//sound.play();
			showBubble = true;
			soundStarted = true;
			currentTextline = "Hi, mein Name ist Paul.";
		}
		
		
		else if(startTimer >= 21.5){
			game.setScreen(new HobbyGameScreen(game));
		}
		
		else if(startTimer >= 17.5){
			currentTextline = "Verrate mir deine Hobbys,\nvielleicht werden wir\nja Freunde.";
		}
		
		else if(startTimer >= 14){
			currentTextline = "Ich würde dich gerne\nkennenlernen!";
		}
		
		else if(startTimer >= 11){
			currentTextline = "Du bist neu hier\nan der Schule, oder?";
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
