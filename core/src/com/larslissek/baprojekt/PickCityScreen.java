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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class PickCityScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	Texture background;
	
	
	Drawable drawable;
	
	Stage stage;
	
	
	Texture blondesMaedchen;
	Sprite maedchen;
	float startTimer = 0;
	private float maedchenalpha = 0;
	
	private Texture speechbubble;
	
	BitmapFont font;
	private String currentTextline = "";
	private boolean showBubble = false;
	
	Music sound;
	private boolean soundStarted = false;
	
	
	ImageButton dortmundButton;
	ImageButton bochumButton;
	ImageButton recklinghausenButton;
	ImageButton weselButton;
	
	Texture ruhrgebietKarte;
	
	
	int currentFace;
	int currentHair;
	int currentShirt;
	int currentGlasses;
	String name;
	int school;
	private boolean showMap = false;
	
	public PickCityScreen(MyGdxGame game, int currentFace, int currentHair, int currentShirt, int currentGlasses, String name, int school) {
		this.game = game;
		this.currentFace = currentFace;
		this.currentHair = currentHair;
		this.currentShirt = currentShirt;
		this.currentGlasses = currentGlasses;
		this.name = name;
		this.school = school;
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
		
		background = new Texture(Gdx.files.internal("DWDcover2.jpg"));
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		
		
		blondesMaedchen = new Texture(Gdx.files.internal("blondesmaedchen.png"));
		maedchen = new Sprite(blondesMaedchen);
		maedchen.setPosition(100, 20);
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("redcircle.png"))));
		dortmundButton = new ImageButton(drawable);
		dortmundButton.setSize(40, 40);
		dortmundButton.setPosition(820, 320);
		
		stage.addActor(dortmundButton);
		
		dortmundButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				IOController.saveAvatar(currentFace, currentHair, currentShirt, currentGlasses);
				IOController.createProfile(name);
				IOController.saveSchool(school);
				IOController.saveCity("dortmund");
				game.setScreen(new ProfileSelectionScreen(game));
			}
		});
		
		dortmundButton.setDisabled(true);
		dortmundButton.setVisible(false);
		
		
		bochumButton = new ImageButton(drawable);
		bochumButton.setSize(40, 40);
		bochumButton.setPosition(700, 300);
		
		stage.addActor(bochumButton);
		
		bochumButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				IOController.saveAvatar(currentFace, currentHair, currentShirt, currentGlasses);
				IOController.createProfile(name);
				IOController.saveSchool(school);
				IOController.saveCity("bochum");
				game.setScreen(new ProfileSelectionScreen(game));
			}
		});
		
		bochumButton.setDisabled(true);
		bochumButton.setVisible(false);
		
		
		recklinghausenButton = new ImageButton(drawable);
		recklinghausenButton.setSize(40, 40);
		recklinghausenButton.setPosition(620, 450);
		
		stage.addActor(recklinghausenButton);
		
		recklinghausenButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				IOController.saveAvatar(currentFace, currentHair, currentShirt, currentGlasses);
				IOController.createProfile(name);
				IOController.saveSchool(school);
				IOController.saveCity("recklinghausen");
				game.setScreen(new ProfileSelectionScreen(game));
			}
		});
		
		recklinghausenButton.setDisabled(true);
		recklinghausenButton.setVisible(false);
		
		
		weselButton = new ImageButton(drawable);
		weselButton.setSize(40, 40);
		weselButton.setPosition(360, 410);
		
		stage.addActor(weselButton);
		
		weselButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				IOController.saveAvatar(currentFace, currentHair, currentShirt, currentGlasses);
				IOController.createProfile(name);
				IOController.saveSchool(school);
				IOController.saveCity("wesel");
				game.setScreen(new ProfileSelectionScreen(game));
			}
		});
		
		weselButton.setDisabled(true);
		weselButton.setVisible(false);
		
		ruhrgebietKarte = new Texture(Gdx.files.internal("Ruhrgebiet-Karte.png"));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0);
		maedchen.draw(batch, maedchenalpha);
		
		
		if(showBubble){
			batch.draw(speechbubble, -30, 300, 700, 200);
			font.draw(batch, currentTextline  + "", 50, 465);
		}
		
		if(showMap){
			batch.draw(ruhrgebietKarte, 300, 160, 800, 400);
		}
		
		
		batch.end();
		
		stage.act();
		stage.draw();

		
		batch.begin();
		
		
		if(dortmundButton.isVisible()){
			font.draw(batch, "Dortmund", 850, 380);
			font.draw(batch, "Bochum", 710, 290);
			font.draw(batch, "Recklinghausen", 650, 445);
			font.draw(batch, "Wesel", 390, 475);
		}
		
		
		batch.end();
		
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		startTimer += delta;
		
		if(startTimer >= 2 && maedchenalpha + delta / 4 <= 0.99){
			maedchenalpha += delta / 4;
		}
		
		else if(startTimer >= 2 && !soundStarted){
			//TODO
			//sound.play();
			showBubble = true;
			soundStarted = true;
			currentTextline = "In welcher Stadt soll der\nFall stattfinden?";
		}
		
		
		if(startTimer >= 12){
			showBubble = false;
			showMap  = true;
		}
		
		if(startTimer >= 12){
			dortmundButton.setDisabled(false);
			dortmundButton.setVisible(true);
			
			bochumButton.setDisabled(false);
			bochumButton.setVisible(true);
			
			recklinghausenButton.setDisabled(false);
			recklinghausenButton.setVisible(true);
			
			weselButton.setDisabled(false);
			weselButton.setVisible(true);
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
