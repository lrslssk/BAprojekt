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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class HobbyGameScreen implements Screen {

	
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
	private boolean soundStarted = false;
	private boolean gameStarted = false;
	
	Stage stage;
	TextButtonStyle style;
	
	TextButton answer1button;
	TextButton answer2button;
	TextButton answer3button;
	
	Skin skin;
	private TextureAtlas atlas;
	
	public HobbyGameScreen(MyGdxGame game) {
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
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		atlas = new TextureAtlas(Gdx.files.internal("ui/ui-orange.atlas"));
		skin.addRegions(atlas);
		
		style = new TextButtonStyle();
		style.over = skin.getDrawable("button_01");
		style.up = skin.getDrawable("button_03");
		style.font = generator.generateFont(parameter);
		
		
		answer1button = new TextButton("Antwort 1", style);
		answer1button.setSize(300, 75);
		answer1button.setPosition(MyGdxGame.V_WIDTH / 2 - answer1button.getWidth() / 2, 20);
		stage.addActor(answer1button);
		
		answer1button.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {

	        }
	    });
		
		answer2button = new TextButton("Antwort 2", style);
		answer2button.setSize(300, 75);
		answer2button.setPosition(MyGdxGame.V_WIDTH / 2 - answer1button.getWidth() / 2, 20);
		stage.addActor(answer2button);
		
		answer2button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				
			}
		});
		
		answer3button = new TextButton("Antwort 3", style);
		answer3button.setSize(300, 75);
		answer3button.setPosition(MyGdxGame.V_WIDTH / 2 - answer1button.getWidth() / 2, 20);
		stage.addActor(answer3button);
		
		answer3button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				
			}
		});
		
		answer1button.setVisible(false);
		answer1button.setDisabled(true);
		
		answer2button.setVisible(false);
		answer2button.setDisabled(true);
		
		answer3button.setVisible(false);
		answer3button.setDisabled(true);
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		background.draw(batch);
		
		
		if(showBubble){
			batch.draw(speechbubble, 270, 480, 700, 200);
			font.draw(batch, currentTextline  + "", 350, 645);
		}
		
		answer1button.setSize(300, 75);
		answer1button.setPosition(MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - answer1button.getWidth() / 2, 40);
		
		answer2button.setSize(300, 75);
		answer2button.setPosition(2 * MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - answer2button.getWidth() / 2, 40);
		
		answer3button.setSize(300, 75);
		answer3button.setPosition(3 * MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - answer3button.getWidth() / 2, 40);
		
		batch.end();
		
		
		stage.act();
		stage.draw();
		
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		background.setBounds(0, 0, 1280, 720);
		
		startTimer += delta;
		
		
		if(startTimer >= 2 && !soundStarted){
			//TODO record right soundfile
			//sound.play();
			showBubble = true;
			soundStarted = true;
			currentTextline = "OHJE! Hier ist wohl etwas\ndurcheinander geraten!";
		}
		
		
		else if(startTimer >= 12){
			currentTextline = "";
			showBubble = false;
			gameStarted  = true;
		}
		
		
		else if(startTimer >= 8){
			currentTextline = "Hilfst du mir,\ndie Hobbys zu sortieren?";
		}
		
		if(gameStarted && !answer1button.isVisible()){
			answer1button.setVisible(true);
			answer1button.setDisabled(false);
			
			answer2button.setVisible(true);
			answer2button.setDisabled(false);
			
			answer3button.setVisible(true);
			answer3button.setDisabled(false);
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
