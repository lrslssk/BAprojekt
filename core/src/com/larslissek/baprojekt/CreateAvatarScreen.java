package com.larslissek.baprojekt;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class CreateAvatarScreen implements Screen {

	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	Texture background;
	
	ImageButton friendbookButton;
	
	Drawable drawable;
	
	Stage stage;
	
	
	float startTimer = 0;
	
	private Texture speechbubble;
	
	BitmapFont font;
	private String currentTextline = "";
	private boolean showBubble = false;
	
	Sprite[] faces;
	Sprite[] hair;
	Sprite[] glasses;
	Sprite[] shirts;
	
	int currentShirt;
	int currentFace;
	int currentGlasses = -1;
	int currentHair;
	
	
	Skin skin;
	private TextureAtlas atlas;
	
	
	TextButtonStyle style;
	TextButtonStyle style2;
	
	
	TextButton nextFaceButton;
	TextButton previousFaceButton;
	
	TextButton nextShirtButton;
	TextButton previousShirtButton;
	
	TextButton nextGlassesButton;
	TextButton previousGlassesButton;
	
	TextButton nextHairButton;
	TextButton previousHairButton;
	
	ImageButton confirmButton;
	
	String name = "";
	int school;
	
	public CreateAvatarScreen(MyGdxGame game, String name, int school) {
		this.game = game;
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
		
		
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		faces = Arrays.copyOf(Assets.faces, Assets.faces.length);
		hair = Arrays.copyOf(Assets.hair, Assets.hair.length);
		glasses = Arrays.copyOf(Assets.glasses, Assets.glasses.length);
		shirts = Arrays.copyOf(Assets.shirts, Assets.shirts.length);
		
		
		skin = new Skin();
		atlas = new TextureAtlas(Gdx.files.internal("ui/ui-orange.atlas"));
		skin.addRegions(atlas);
		
		style = new TextButtonStyle();
		style.over = skin.getDrawable("knob_02");
		style.up = skin.getDrawable("knob_02");
		style.font = font;
		
		style2 = new TextButtonStyle();
		style2.over = skin.getDrawable("knob_04");
		style2.up = skin.getDrawable("knob_04");
		style2.font = font;
		
		nextFaceButton = new TextButton("", style);
		nextFaceButton.setSize(50, 50);
		nextFaceButton.setPosition(770, 230);
		stage.addActor(nextFaceButton);
		
		nextFaceButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentFace++;
	        	
	        	if(currentFace > faces.length - 1){
	        		currentFace = 0;
	        	}
	        }
	    });
		
		nextHairButton = new TextButton("", style);
		nextHairButton.setSize(50, 50);
		nextHairButton.setPosition(770, 165);
		stage.addActor(nextHairButton);
		
		nextHairButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentHair++;
	        	
	        	if(currentHair > hair.length - 1){
	        		currentHair = 0;
	        	}
	        }
	    });
		
		
		nextShirtButton = new TextButton("", style);
		nextShirtButton.setSize(50, 50);
		nextShirtButton.setPosition(770, 105);
		stage.addActor(nextShirtButton);
		
		nextShirtButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentShirt++;
	        	
	        	if(currentShirt > shirts.length - 1){
	        		currentShirt = 0;
	        	}
	        }
	    });
		
		
		nextGlassesButton = new TextButton("", style);
		nextGlassesButton.setSize(50, 50);
		nextGlassesButton.setPosition(770, 45);
		stage.addActor(nextGlassesButton);
		
		nextGlassesButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentGlasses++;
	        	
	        	if(currentGlasses > glasses.length - 1){
	        		currentGlasses = -1;
	        	}
	        }
	    });
		
		previousFaceButton = new TextButton("", style2);
		previousFaceButton.setSize(50, 50);
		previousFaceButton.setPosition(460, 230);
		stage.addActor(previousFaceButton);
		
		previousFaceButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentFace--;
	        	
	        	if(currentFace < 0){
	        		currentFace = faces.length - 1;
	        	}
	        }
	    });
		
		previousHairButton = new TextButton("", style2);
		previousHairButton.setSize(50, 50);
		previousHairButton.setPosition(460, 165);
		stage.addActor(previousHairButton);
		
		previousHairButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentHair--;
	        	
	        	if(currentHair < 0){
	        		currentHair = hair.length - 1;
	        	}
	        }
	    });
		
		
		previousShirtButton = new TextButton("", style2);
		previousShirtButton.setSize(50, 50);
		previousShirtButton.setPosition(460, 105);
		stage.addActor(previousShirtButton);
		
		previousShirtButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentShirt--;
	        	
	        	if(currentShirt < 0){
	        		currentShirt = shirts.length - 1;
	        	}
	        }
	    });
		
		
		previousGlassesButton = new TextButton("", style2);
		previousGlassesButton.setSize(50, 50);
		previousGlassesButton.setPosition(460, 45);
		stage.addActor(previousGlassesButton);
		
		previousGlassesButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	currentGlasses--;
	        	
	        	if(currentGlasses < -1){
	        		currentGlasses = glasses.length - 1;
	        	}
	        }
	    });
		
		
		LabelStyle style = new LabelStyle(font, Color.BLACK);
		
		Label faceLabel = new Label("Gesicht", style);
		stage.addActor(faceLabel);
		
		Label hairLabel = new Label("Haare", style);
		stage.addActor(hairLabel);
		
		Label shirtLabel = new Label("Shirt", style);
		stage.addActor(shirtLabel);
		
		Label glassesLabel = new Label("Brille", style);
		stage.addActor(glassesLabel);
		
		faceLabel.setPosition(573, 240);
		hairLabel.setPosition(590, 176);
		shirtLabel.setPosition(590, 116);
		glassesLabel.setPosition(583, 55);
		
		
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/gruenerhaken.png"))));
		confirmButton = new ImageButton(drawable);
		confirmButton.setSize(100, 100);
		confirmButton.setPosition(1120, 40);
		
		stage.addActor(confirmButton);
		
		confirmButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new PickCityScreen(game, currentFace, currentHair, currentShirt, currentGlasses, name, school));
			}
		});
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
			batch.draw(speechbubble, -30, 300, 700, 200);
			font.draw(batch, currentTextline  + "", 50, 465);
		}
		
		shirts[currentShirt].setBounds(606, 333, 85, 85);
		shirts[currentShirt].draw(batch);
		
		faces[currentFace].setBounds(600, 400, 100, 100);
		faces[currentFace].draw(batch);
		
		if(currentHair >= 13 && currentHair <= 24){
			if(currentHair >= 17 && currentHair <= 20){
				hair[currentHair].setBounds(600, 405, 100, 100);
			}
			
			else{
				hair[currentHair].setBounds(600, 405, 100, 100);
			}
			
			if(currentHair >= 21 && currentHair <= 24){
				hair[currentHair].setBounds(600, 415, 100, 100);
			}
		}
		else{
			hair[currentHair].setBounds(600, 430, 100, 100);
		}
		hair[currentHair].draw(batch);
		
		if(currentGlasses != -1){
			glasses[currentGlasses].setBounds(615, 430, 70, 30);
			glasses[currentGlasses].draw(batch);
		}
		
		
		
		
		batch.end();
		
		stage.act();
		stage.draw();
		
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
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
