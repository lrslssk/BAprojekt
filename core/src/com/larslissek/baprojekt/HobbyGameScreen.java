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
	private boolean gameEnded = false;
	
	Stage stage;
	TextButtonStyle style;
	
	TextButton answer1button;
	TextButton answer2button;
	TextButton answer3button;
	
	Skin skin;
	private TextureAtlas atlas;
	
	Sprite lesen;
	Sprite fahrradfahren;
	Sprite fussball;
	Sprite klettern;
	Sprite schwimmen;
	Sprite segeln;
	Sprite tennis;
	
	int currentPicture = 0;
	
	Sprite paul;
	private float endGameTimer = 0;
	
	
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
	        	if(currentPicture == 1 || currentPicture == 4){
	        		currentPicture++;
	        	}
	        }
	    });
		
		answer2button = new TextButton("Antwort 2", style);
		answer2button.setSize(300, 75);
		answer2button.setPosition(MyGdxGame.V_WIDTH / 2 - answer1button.getWidth() / 2, 20);
		stage.addActor(answer2button);
		
		answer2button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
	        	if(currentPicture == 0 || currentPicture == 2){
	        		currentPicture++;
	        	}
			}
		});
		
		answer3button = new TextButton("Antwort 3", style);
		answer3button.setSize(300, 75);
		answer3button.setPosition(MyGdxGame.V_WIDTH / 2 - answer1button.getWidth() / 2, 20);
		stage.addActor(answer3button);
		
		answer3button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
	        	if(currentPicture == 3 || currentPicture == 5 || currentPicture == 6){
	        		currentPicture++;
	        	}
			}
		});
		
		answer1button.setSize(300, 75);
		answer1button.setPosition(MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - answer1button.getWidth() / 2, 40);
		
		answer2button.setSize(300, 75);
		answer2button.setPosition(2 * MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - answer2button.getWidth() / 2, 40);
		
		answer3button.setSize(300, 75);
		answer3button.setPosition(3 * MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - answer3button.getWidth() / 2, 40);
		
		
		
		answer1button.setVisible(false);
		answer1button.setDisabled(true);
		
		answer2button.setVisible(false);
		answer2button.setDisabled(true);
		
		answer3button.setVisible(false);
		answer3button.setDisabled(true);
		
		
		
		lesen = new Sprite(new Texture(Gdx.files.internal("hobbygame/book.jpg")));
		fahrradfahren = new Sprite(new Texture(Gdx.files.internal("hobbygame/fahrradfahren.jpg")));
		fussball = new Sprite(new Texture(Gdx.files.internal("hobbygame/fussball.jpg")));
		klettern = new Sprite(new Texture(Gdx.files.internal("hobbygame/klettern.jpg")));
		schwimmen = new Sprite(new Texture(Gdx.files.internal("hobbygame/schwimmen.jpg")));
		segeln = new Sprite(new Texture(Gdx.files.internal("hobbygame/segeln.jpg")));
		tennis = new Sprite(new Texture(Gdx.files.internal("hobbygame/tennis.jpg")));
		
		lesen.setBounds(165, 280, 200, 200);
		fahrradfahren.setBounds(290, 60, 200, 200);
		fussball.setBounds(415, 280, 200, 200);
		klettern.setBounds(540, 60, 200, 200);
		schwimmen.setBounds(665, 280, 200, 200);
		segeln.setBounds(790, 60, 200, 200);
		tennis.setBounds(915, 280, 200, 200);
		
		
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
		
		if (!gameStarted) {
			lesen.draw(batch);
			fahrradfahren.draw(batch);
			fussball.draw(batch);
			klettern.draw(batch);
			schwimmen.draw(batch);
			segeln.draw(batch);
			tennis.draw(batch);
		}
		
		if(gameStarted && currentPicture == 0){
			lesen.setBounds(280, 200, 700, 400);
			lesen.draw(batch);
			
			answer1button.setText("Schwimmen");
			answer2button.setText("Lesen");
			answer3button.setText("Tennis");
		}
		
		else if(gameStarted && currentPicture == 1){
			fahrradfahren.setBounds(280, 200, 700, 400);
			fahrradfahren.draw(batch);
			
			answer1button.setText("Radfahren");
			answer2button.setText("Segeln");
			answer3button.setText("Klettern");
		}
		
		else if(gameStarted && currentPicture == 2){
			fussball.setBounds(280, 200, 700, 400);
			fussball.draw(batch);
			
			answer1button.setText("Laufen");
			answer2button.setText("Fußball");
			answer3button.setText("Badminton");
		}
		
		else if(gameStarted && currentPicture == 3){
			klettern.setBounds(280, 200, 700, 400);
			klettern.draw(batch);
			
			answer1button.setText("Billard");
			answer2button.setText("Hochsprung");
			answer3button.setText("Klettern");
		}
		
		else if(gameStarted && currentPicture == 4){
			schwimmen.setBounds(280, 200, 700, 400);
			schwimmen.draw(batch);
			
			answer1button.setText("Schwimmen");
			answer2button.setText("Eislaufen");
			answer3button.setText("Reisen");
		}
		
		else if(gameStarted && currentPicture == 5){
			segeln.setBounds(280, 200, 700, 400);
			segeln.draw(batch);
			
			answer1button.setText("Tretboot fahren");
			answer2button.setText("Schwimmen");
			answer3button.setText("Segeln");
		}
		
		else if(gameStarted && currentPicture == 6){
			tennis.setBounds(280, 200, 700, 400);
			tennis.draw(batch);
			
			answer1button.setText("Basketball");
			answer2button.setText("Handball");
			answer3button.setText("Tennis");
		}
		
		
		
		if(showBubble){
			batch.draw(speechbubble, 270, 480, 700, 200);
			font.draw(batch, currentTextline  + "", 350, 645);
		}
		
		
		if(gameEnded){
			showBubble = true;
			currentTextline = "Sehr gut gemacht!\nVielen Dank für deine Hilfe!";
			
			paul.setPosition(500, 100);
			paul.draw(batch);
			
			endGameTimer += delta;
		}
		
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
		
		
		else if(startTimer >= 12 && !gameEnded){
			currentTextline = "";
			showBubble = false;
			gameStarted  = true;
		}
		
		
		else if(startTimer >= 8 && !gameEnded){
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
		
		if(currentPicture >= 7){
			
			answer1button.setVisible(false);
			answer1button.setDisabled(true);
			
			answer2button.setVisible(false);
			answer2button.setDisabled(true);
			
			answer3button.setVisible(false);
			answer3button.setDisabled(true);
			
			gameEnded = true;
		}
		
		
		if(endGameTimer >= 9){
			//TODO Set Screen to HobbyPickScreen
		}
		
		if(endGameTimer >= 3){
			currentTextline = "Welches ist dein\nLieblingshobby?";
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
