package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class NightWorldMap implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	
	Sprite backgroundDay;
	Sprite backgroundNight;
	
	//debug
	ShapeRenderer renderer;
	
	Rectangle homeRectangle;
	Rectangle schoolRectangle;
	Rectangle towerRectangle;
	Rectangle carnivalRectangle;
	
	float alphaDay = 1;
	float alphaNight = 0;
	
	boolean dayToNight = true;
	private float endScreenTimer;
	
	
	public NightWorldMap(MyGdxGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		
		batch = new SpriteBatch();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 29;
		
		backgroundDay = new Sprite(new Texture(Gdx.files.internal("worldmapv1.png")));
		backgroundNight = new Sprite(new Texture(Gdx.files.internal("worldmapnight.png")));
		
		homeRectangle = new Rectangle(170, 280, 170, 170);
		schoolRectangle = new Rectangle(505, 330, 290, 170);
		towerRectangle = new Rectangle(900, 330, 100, 190);
		carnivalRectangle = new Rectangle(890, 80, 380, 210);
		
		renderer = new ShapeRenderer();
		
		//Stop back key from quitting the game
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		renderer.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
		backgroundDay.draw(batch, alphaDay);
		backgroundNight.draw(batch, alphaNight);
		
		batch.end();
		
		//DEBUG
//		renderer.setAutoShapeType(true);
//		renderer.begin();
//		renderer.set(ShapeType.Line);
//		renderer.rect(homeRectangle.x, homeRectangle.y, homeRectangle.width, homeRectangle.height);
//		renderer.end();
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		
		if(dayToNight){
			if(alphaDay > 0f){
				alphaDay -= delta / 5;
			}
			
			else if(alphaNight + delta / 5 < 0.99f){
				alphaNight += delta / 5;
			}
			
			else{
				dayToNight = false;
			}
		}
		
		if(!dayToNight){
			if(alphaNight - delta / 5 > 0f){
				alphaNight -= delta / 5;
			}
			
			else if(alphaDay + delta / 5 < 0.99f){
				alphaDay += delta / 5;
			}
			
			else{
				endScreenTimer += delta;
			}
		}
		
		
		if(endScreenTimer >= 3){
			game.setScreen(new WorldmapScreen(game, 1));
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
