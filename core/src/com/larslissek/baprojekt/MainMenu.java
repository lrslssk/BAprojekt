package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class MainMenu implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	
	Stage stage;
	
	TextButtonStyle style;
	
	TextButton playButton;
	TextButton aboutButton;
	
	Skin skin;
	private TextureAtlas atlas;
	
	FreeTypeFontGenerator generator;
	Texture background;
	
	Music DWDIntro = Assets.DWDIntro;
	
	public MainMenu(MyGdxGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		
		batch = new SpriteBatch();
		
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		atlas = new TextureAtlas(Gdx.files.internal("ui/ui-orange.atlas"));
		skin.addRegions(atlas);
		
		style = new TextButtonStyle();
		style.over = skin.getDrawable("button_01");
		style.up = skin.getDrawable("button_03");
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 29;
		style.font = generator.generateFont(parameter);
		
		
		playButton = new TextButton("Spielen", style);
		playButton.setSize(300, 75);
		playButton.setPosition(MyGdxGame.V_WIDTH / 2 - playButton.getWidth() / 2, 120);
		stage.addActor(playButton);
		
		
		aboutButton = new TextButton("Über die App", style);
		aboutButton.setSize(300, 75);
		aboutButton.setPosition(MyGdxGame.V_WIDTH / 2 - aboutButton.getWidth() / 2, 20);
		stage.addActor(aboutButton);
		
		playButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	
	        	if(!IOController.doesProfileExist()){
	        		//TODO set Screen to profile creation
	        		DWDIntro.stop();
	        		game.setScreen(new ProfileCreationScreen(game));
	        	}
	        	
	        	else{
	        		//TODO set Screen to profile selection
	        		DWDIntro.stop();
	        		game.setScreen(new ProfileSelectionScreen(game));
	        	}
	        }
	    });
		

		aboutButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				DWDIntro.stop();
				game.setScreen(new AboutScreen(game));
			}
		});
		
		background = new Texture(Gdx.files.internal("DWDcover.jpg"));
		
		//TODO
		DWDIntro.play();
		DWDIntro.setLooping(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		
		stage.act();
		stage.draw();
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
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
