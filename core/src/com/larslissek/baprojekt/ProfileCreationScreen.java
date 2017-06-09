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

public class ProfileCreationScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	Texture background;
	Texture friendbook;
	
	ImageButton friendbookButton;
	
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
	
	public ProfileCreationScreen(MyGdxGame game) {
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
		friendbook = new Texture(Gdx.files.internal("freundebuch.png"));
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		drawable = new TextureRegionDrawable(new TextureRegion(friendbook));
		friendbookButton = new ImageButton(drawable);
		friendbookButton.setSize(400, 250);
		friendbookButton.setPosition(MyGdxGame.V_WIDTH / 2 - friendbookButton.getWidth() / 2, 120);
		
		stage.addActor(friendbookButton);
		
		friendbookButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//game.setScreen(new SelectAvatarScreen(game));
				System.out.println("button pressed");
			}
		});
		
		blondesMaedchen = new Texture(Gdx.files.internal("blondesmaedchen.png"));
		maedchen = new Sprite(blondesMaedchen);
		maedchen.setPosition(100, 20);
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		sound = Assets.profileCreationSound;
		
		friendbookButton.setDisabled(true);
		friendbookButton.setVisible(false);
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
		
		
		friendbookButton.setSize(300, 200);
		friendbookButton.setPosition(MyGdxGame.V_WIDTH / 2 - friendbookButton.getWidth() / 2, 120);
		
		if(showBubble){
			batch.draw(speechbubble, -30, 300, 700, 200);
			font.draw(batch, currentTextline  + "", 50, 465);
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
			sound.play();
			showBubble = true;
			soundStarted = true;
			currentTextline = "Hi, dich sehe ich hier\nzum ersten Mal.";
		}
		
		
		
		if(startTimer >= 21){
			currentTextline = "Berühre mein Freundebuch,\num einen Eintrag vorzunehmen!";
		}
		
		else if(startTimer >= 17.5){
			currentTextline = "Erzähl mir doch ein\nwenig von dir!";
		}
		
		else if(startTimer >= 14){
			currentTextline = "Ich würde dich gerne\nkennenlernen!";
		}
		
		else if(startTimer >= 11){
			currentTextline = "Mein Name ist Ella.";
		}
		
		
		if(startTimer >= 25.5){
			friendbookButton.setDisabled(false);
			friendbookButton.setVisible(true);
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
