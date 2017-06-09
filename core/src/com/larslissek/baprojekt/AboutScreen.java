package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class AboutScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	
	Stage stage;
	
	TextButtonStyle style;
	
	TextButton backButton;
	
	Skin skin;
	private TextureAtlas atlas;
	
	FreeTypeFontGenerator generator;
	Texture background;
	
	BitmapFont font;
	
	String text = "Diese App wurde im Rahmen einer Abschlussarbeit am Lehrstuhl für Dienstleistungsinformatik entwickelt. \n Die Planung der App, sowie die Erarbeitung der wissenschaftlichen Basis"
			+ "geschah in Kooperation mit dem Lehrstuhl für Sprache und Kommunikation";
	
	
	Texture infofak;
	Texture SuK;
	
	public AboutScreen(MyGdxGame game) {
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
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended2.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 29;
		style.font = generator.generateFont(parameter);
		
		

		
		backButton = new TextButton("Zurück", style);
		backButton.setSize(300, 75);
		backButton.setPosition(MyGdxGame.V_WIDTH / 2 - backButton.getWidth() / 2, 20);
		stage.addActor(backButton);
		

		backButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new MainMenu(game));
			}
		});
		
		background = new Texture(Gdx.files.internal("DWDcover.jpg"));
		
		parameter.size = 31;
		font = generator.generateFont(parameter);
		font.setColor(Color.BLACK);
		
		infofak = new Texture(Gdx.files.internal("infofakultaet.PNG"));
		SuK = new Texture(Gdx.files.internal("SundK.PNG"));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		text = "Diese App wurde im Rahmen einer Abschlussarbeit\nam Lehrstuhl für Dienstleistungsinformatik entwickelt.\nDie Planung der App, sowie die Erarbeitung der\nwissenschaftlichen Basis,"
				+ " geschah in Kooperation\nmit dem Lehrstuhl für Sprache und Kommunikation.";
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
		batch.draw(infofak, 90, 560, 150, 100);
		batch.draw(SuK, 900, 550, 350, 120);
		
		font.draw(batch, text, 170, 400);
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

