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

public class PickHobbyScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	
	Sprite background;
	
	
	float startTimer = 0;
	
	private Texture speechbubble;
	
	BitmapFont font;
	private String currentTextline = "";
	private boolean showBubble = false;
	
	Music sound;
	
	Sprite lesen;
	Sprite fahrradfahren;
	Sprite fussball;
	Sprite klettern;
	Sprite schwimmen;
	Sprite segeln;
	Sprite tennis;
	
	
	Sprite paul;
	
	
	public PickHobbyScreen(MyGdxGame game) {
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
		
		background = new Sprite(new Texture(Gdx.files.internal("DWDcover2.jpg")));
		
		
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		sound = Assets.profileCreationSound;
		
		
		
		
		
		
		lesen = new Sprite(new Texture(Gdx.files.internal("hobbygame/book.jpg")));
		fahrradfahren = new Sprite(new Texture(Gdx.files.internal("hobbygame/fahrradfahren.jpg")));
		fussball = new Sprite(new Texture(Gdx.files.internal("hobbygame/fussball.jpg")));
		klettern = new Sprite(new Texture(Gdx.files.internal("hobbygame/klettern.jpg")));
		schwimmen = new Sprite(new Texture(Gdx.files.internal("hobbygame/schwimmen.jpg")));
		segeln = new Sprite(new Texture(Gdx.files.internal("hobbygame/segeln.jpg")));
		tennis = new Sprite(new Texture(Gdx.files.internal("hobbygame/tennis.jpg")));
		
		lesen.setBounds(115, 480, 200, 200);
		fussball.setBounds(390, 480, 200, 200);
		schwimmen.setBounds(690, 480, 200, 200);
		tennis.setBounds(965, 480, 200, 200);
		fahrradfahren.setBounds(240, 130, 200, 200);
		klettern.setBounds(540, 130, 200, 200);
		segeln.setBounds(840, 130, 200, 200);
		
		
		paul = new Sprite(new Texture(Gdx.files.internal("paul.png")));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		background.draw(batch);
		
		lesen.draw(batch);
		fahrradfahren.draw(batch);
		fussball.draw(batch);
		klettern.draw(batch);
		schwimmen.draw(batch);
		segeln.draw(batch);
		tennis.draw(batch);
		
		
		
		if(showBubble){
			batch.draw(speechbubble, 270, 480, 700, 200);
			font.draw(batch, currentTextline  + "", 350, 645);
		}
		
		font.draw(batch, "Lesen", 167, 450);
		font.draw(batch, "Fußball", 425, 450);
		font.draw(batch, "Schwimmen", 707, 450);
		font.draw(batch, "Tennis", 1010, 450);
		font.draw(batch, "Radfahren", 259, 100);
		font.draw(batch, "Klettern", 565, 100);
		font.draw(batch, "Segeln", 885, 100);
		
		batch.end();
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		background.setBounds(0, 0, 1280, 720);
		
		startTimer += delta;
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
