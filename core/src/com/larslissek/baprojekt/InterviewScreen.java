package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class InterviewScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	Texture background;
	Texture school1;
	Texture school2;
	Texture school3;
	
	ImageButton school1Button;
	ImageButton school2Button;
	ImageButton school3Button;
	
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
	private boolean noButtonPressedYet = true;
	private boolean isSchoolPicked = false;
	
	float afterSchoolPickTimer = 0;
	
	
	Skin skin;
	private TextureAtlas atlas;
	TextField nameField;
	
	TextButtonStyle style;
	
	TextButton confirmButton;
	protected String playerName;
	
	private boolean nameEntered = false;
	private CharSequence nameString = "Name:";
	
	private float nextScreenTimer = 0;
	
	String name = "";
	int school;
	
	public InterviewScreen(MyGdxGame game) {
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
		
		background = new Texture(Gdx.files.internal("DWDcover2.jpg"));
		school1 = new Texture(Gdx.files.internal("school1.jpg"));
		school2 = new Texture(Gdx.files.internal("school2.jpg"));
		school3 = new Texture(Gdx.files.internal("school3.jpg"));
		
		
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		school1Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(school1)));
		
		stage.addActor(school1Button);
		
		school1Button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("School1 pressed");
				
				noButtonPressedYet = false;
				
				school2Button.setDisabled(true);
				school2Button.setVisible(false);
				
				school3Button.setDisabled(true);
				school3Button.setVisible(false);
				
				isSchoolPicked = true;
				
				school = 0;
			}
		});
		
		
		school2Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(school2)));
		
		stage.addActor(school2Button);
		
		school2Button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("School2 pressed");
				
				noButtonPressedYet = false;
				
				school1Button.setDisabled(true);
				school1Button.setVisible(false);
				
				school3Button.setDisabled(true);
				school3Button.setVisible(false);
				
				isSchoolPicked = true;
				
				school = 1;
			}
		});
		
		
		school3Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(school3)));
		
		stage.addActor(school3Button);
		
		school3Button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("School3 pressed");
				
				noButtonPressedYet = false;
				
				school1Button.setDisabled(true);
				school1Button.setVisible(false);
				
				school2Button.setDisabled(true);
				school2Button.setVisible(false);
				
				isSchoolPicked = true;
				
				school = 2;
			}
		});
		
		
		school1Button.setSize(300, 200);
		school1Button.setPosition(MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - school1Button.getWidth() / 2, 400);
		
		school2Button.setSize(300, 200);
		school2Button.setPosition(2 * MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - school2Button.getWidth() / 2, 400);
		
		school3Button.setSize(300, 200);
		school3Button.setPosition(3 * MyGdxGame.V_WIDTH / 3 - MyGdxGame.V_WIDTH / 6 - school3Button.getWidth() / 2, 400);
		
		blondesMaedchen = new Texture(Gdx.files.internal("blondesmaedchen.png"));
		maedchen = new Sprite(blondesMaedchen);
		maedchen.setPosition(100, 20);
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		//TODO Record appropriate with Larry
		//sound = Assets.profileCreationSound;
		
		school1Button.setDisabled(true);
		school1Button.setVisible(false);
		
		school2Button.setDisabled(true);
		school2Button.setVisible(false);
		
		school3Button.setDisabled(true);
		school3Button.setVisible(false);
		
		
		skin = new Skin();
		atlas = new TextureAtlas(Gdx.files.internal("ui/ui-orange.atlas"));
		skin.addRegions(atlas);
		
		
		TextFieldStyle TFstyle = new TextFieldStyle();
		TFstyle.background = skin.getDrawable("textbox_02");
		TFstyle.cursor = skin.getDrawable("textbox_cursor_02");
		TFstyle.font = font;
		TFstyle.fontColor = Color.BLACK;
		TFstyle.focusedBackground = skin.getDrawable("textbox_01");
		
		nameField = new TextField("", TFstyle);
		nameField.setPosition(100, 520);
		nameField.setSize(350, 50);
		nameField.setMaxLength(10);
		
		stage.addActor(nameField);
		nameField.setVisible(false);
		nameField.setDisabled(true);
		
		
		style = new TextButtonStyle();
		style.over = skin.getDrawable("button_01");
		style.up = skin.getDrawable("button_03");
		style.font = font;
		
		
		confirmButton = new TextButton("OK", style);
		confirmButton.setSize(300, 75);
		confirmButton.setPosition(490/2 - confirmButton.getWidth() / 2, 120);
		stage.addActor(confirmButton);
		
		
		confirmButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	String name = nameField.getText();
	        	
	        	if(name.length() <= 10){
	        		playerName = name;
	        		nameEntered = true;
	        	}
	        }
	    });
		
		confirmButton.setDisabled(true);
		confirmButton.setVisible(false);
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
		
		
		
		if(isSchoolPicked && afterSchoolPickTimer > 6){
			font.draw(batch, nameString , 590, 430);
		}
		
		batch.end();
		
		stage.act();
		stage.draw();

		
		
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
			currentTextline = "Zuerst verrate mir doch zu\nwelcher Schule du gehst.";
		}
		
		
		if(startTimer >= 12){
			showBubble = false;
		}
		
		//Schulbilder einblenden
		if(startTimer >= 13 && noButtonPressedYet){
			school1Button.setDisabled(false);
			school1Button.setVisible(true);
			
			school2Button.setDisabled(false);
			school2Button.setVisible(true);
			
			school3Button.setDisabled(false);
			school3Button.setVisible(true);
		}
		
		if(isSchoolPicked){
			afterSchoolPickTimer += delta;
		}
		
		if(isSchoolPicked && afterSchoolPickTimer > 2){
			showBubble = true;
			currentTextline = "Alles klar!\nWie ist dein Name?\nSchreibe ihn mir bitte auf!";
			
			hideAllSchools();
		}
		
		if(isSchoolPicked && afterSchoolPickTimer > 6){
			showBubble = false;
			currentTextline = "";
			
			nameField.setVisible(true);
			nameField.setDisabled(false);
			
			confirmButton.setVisible(true);
			confirmButton.setDisabled(false);
		}
		
		if(nameEntered){
			nextScreenTimer += delta;
		}
		
		if(nameEntered){
			confirmButton.setDisabled(true);
			confirmButton.setVisible(false);
			
			nameField.setVisible(false);
			nameField.setDisabled(true);
			
			nameString = "";
			
			
			showBubble = true;
			currentTextline = "Das ist ein schöner Name!\nZeige mir wie du aussiehst!";
		}
		
		if(nextScreenTimer > 4){
			game.setScreen(new CreateAvatarScreen(game, playerName, school));
		}
		
		
		nameField.setPosition(MyGdxGame.V_WIDTH / 2 - nameField.getWidth() / 2, MyGdxGame.V_HEIGHT / 2 - nameField.getHeight() / 2);
		nameField.setSize(350, 75);
		
		confirmButton.setSize(150, 75);
		confirmButton.setPosition(MyGdxGame.V_WIDTH / 2 - confirmButton.getWidth() / 2, 240);
		
	}

	private void hideAllSchools() {
		school1Button.setDisabled(true);
		school1Button.setVisible(false);
		
		school2Button.setDisabled(true);
		school2Button.setVisible(false);
		
		school3Button.setDisabled(true);
		school3Button.setVisible(false);
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
