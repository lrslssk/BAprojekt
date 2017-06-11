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

public class ProfileSelectionScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	Texture background;
	Texture greyProfileBackground;
	
	ImageButton profile1Button;
	
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
	
	String profile1Name = "";
	String profile1Score = "";
	
	ImageButton deleteProfileButton;
	
	
	Sprite face;
	Sprite hair;
	Sprite glasses;
	Sprite shirt;
	
	public ProfileSelectionScreen(MyGdxGame game) {
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
		greyProfileBackground = new Texture(Gdx.files.internal("emptyprofile.png"));
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		drawable = new TextureRegionDrawable(new TextureRegion(greyProfileBackground));
		profile1Button = new ImageButton(drawable);
		profile1Button.setSize(400, 300);
		profile1Button.setPosition(MyGdxGame.V_WIDTH / 2 - profile1Button.getWidth() / 2, MyGdxGame.V_HEIGHT / 2 - profile1Button.getHeight() / 2 - 35);
		
		stage.addActor(profile1Button);
		
		profile1Button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//game.setScreen(new SelectAvatarScreen(game));
				System.out.println("button pressed");
			}
		});
		
		blondesMaedchen = new Texture(Gdx.files.internal("blondesmaedchen.png"));
		maedchen = new Sprite(blondesMaedchen);
		maedchen.setPosition(100, 20);
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		//TODO Record appropriate with Larry
		//sound = Assets.profileCreationSound;
		
		profile1Button.setDisabled(true);
		profile1Button.setVisible(false);
		
		
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/redcrossbutton.png"))));
		deleteProfileButton = new ImageButton(drawable);
		deleteProfileButton.setSize(75, 75);
		deleteProfileButton.setPosition(900, 316);
		
		stage.addActor(deleteProfileButton);
		
		deleteProfileButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				IOController.deleteProfile();
				game.setScreen(new MainMenu(game));
			}
		});
		
		deleteProfileButton.setDisabled(true);
		deleteProfileButton.setVisible(false);
		
		face = Assets.faces[IOController.getAvatar()[0]];
		hair = Assets.hair[IOController.getAvatar()[1]];
		shirt = Assets.shirts[IOController.getAvatar()[2]];
		
		if(IOController.getAvatar()[3] != -1)
		glasses = Assets.glasses[IOController.getAvatar()[3]];
		
		
		face.setBounds(490, 320, face.getWidth(), face.getHeight());
		
		if(IOController.getAvatar()[1] >= 13 && IOController.getAvatar()[1] <= 16)
			hair.setBounds(488, 320, hair.getWidth(), hair.getHeight());
		
		else if(IOController.getAvatar()[1] >= 17 && IOController.getAvatar()[1] <= 20)
			hair.setBounds(475, 310, hair.getWidth(), hair.getHeight());
		
		else if(IOController.getAvatar()[1] >= 21 && IOController.getAvatar()[1] <= 25)
			hair.setBounds(490, 330, hair.getWidth(), hair.getHeight());
		
		else
			hair.setBounds(495, 360, hair.getWidth(), hair.getHeight());
		
		if(glasses != null)
		glasses.setBounds(500, 350, glasses.getWidth(), glasses.getHeight());
		
		shirt.setBounds(517, 272, 60, 60);
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
		
		
		batch.end();
		
		stage.act();
		stage.draw();

		
		batch.begin();
		font.draw(batch, profile1Name, 680, 420);
		
		if(!profile1Name.equals(""))
		font.draw(batch, "Level:", 680, 360);
		
		font.draw(batch, profile1Score, 720, 320);
		
		if(deleteProfileButton.isVisible()){
			
			
			face.draw(batch);
			hair.draw(batch);
			
			if(glasses != null)
			glasses.draw(batch);
			
			shirt.draw(batch);
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
			currentTextline = "Schön, dass du zurück bist!\nBitte wähle dein Profil.";
		}
		
		
		if(startTimer >= 12){
			showBubble = false;
		}
		
		if(startTimer >= 13){
			profile1Button.setDisabled(false);
			profile1Button.setVisible(true);
			
			deleteProfileButton.setDisabled(false);
			deleteProfileButton.setVisible(true);
			
			profile1Name = IOController.getProfile1Name();
			profile1Score = IOController.getProfileScore() + "";
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
