package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class WorldmapScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	
	FreeTypeFontGenerator generator;
	
	Texture background;
	
	//debug
	ShapeRenderer renderer;
	
	Rectangle homeRectangle;
	Rectangle schoolRectangle;
	Rectangle towerRectangle;
	Rectangle carnivalRectangle;
	
	Sprite redArrow;
	
	int nextLocation;
	//0 == Spielerhaus
	//1 == Schule
	//2 == Turm
	//3 == Kirmesplatz
	
	float arrowYpos = -130f;
	boolean arrowMovingUp = true;
	
	public WorldmapScreen(MyGdxGame game, int nextLocation) {
		this.game = game;
		this.nextLocation = nextLocation;
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
		
		background = new Texture(Gdx.files.internal("worldmapv1.png"));
		
		homeRectangle = new Rectangle(170, 280, 170, 170);
		schoolRectangle = new Rectangle(505, 330, 290, 170);
		towerRectangle = new Rectangle(900, 330, 100, 190);
		carnivalRectangle = new Rectangle(890, 80, 380, 210);
		
		renderer = new ShapeRenderer();
		
		//Stop back key from quitting the game
		Gdx.input.setCatchBackKey(true);
		
		redArrow = new Sprite(new Texture(Gdx.files.internal("arrow.png")));
		redArrow.rotate(-90f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		renderer.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0);
		
		redArrow.setBounds(97, arrowYpos, 100, 70);
		redArrow.draw(batch);
		
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
		
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
			  game.setScreen(new MainMenu(game));
		}
		
		if(nextLocation == 0){
			if(arrowYpos <= -130f && !arrowMovingUp)
				arrowMovingUp = true;
				
			if(arrowYpos >= -100f && arrowMovingUp)
				arrowMovingUp = false;
			
			if(arrowMovingUp)
				arrowYpos += delta * 25;
			
			else
				arrowYpos -= delta * 25;
		}
		
		if(Gdx.input.isTouched()){
			
			float x = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
			float y = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y;
			
			if(homeRectangle.contains(x, y)){
				//TODO Play "success" sound and wait 2 Seconds
				game.setScreen(new PlayerHomeScreen(game));
			}
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
