package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class BagPackGame implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Stage stage;
	
	
	FreeTypeFontGenerator generator;
	Texture background;
	Texture rucksack;
	Texture regal;
	
	Texture book;
	Texture farbkasten;
	Texture kuscheltier;
	
	Image bookImage;
	Image farbkastenImage;
	Image kuscheltierImage;
	
	Music dasbuch;
	Music daskuscheltier;
	Music derfarbmalkasten;
	
	Music backpack;
	
	public BagPackGame(MyGdxGame game) {
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
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 29;
		
		
		
		background = new Texture(Gdx.files.internal("testroom.jpg"));
		rucksack = new Texture(Gdx.files.internal("rucksack.png"));
		regal = new Texture(Gdx.files.internal("shelf.png"));
		
		book = new Texture(Gdx.files.internal("book.png"));
		farbkasten = new Texture(Gdx.files.internal("malkasten.png"));
		kuscheltier = new Texture(Gdx.files.internal("baer.png"));
		
		
		
		bookImage = new Image(book);
		bookImage.setBounds(410, 190, 75, 75);
		stage.addActor(bookImage);
		
		bookImage.addListener(new DragListener() {
			
		    public void drag(InputEvent event, float x, float y, int pointer) {
		        bookImage.moveBy(x - bookImage.getWidth() / 2, y - bookImage.getHeight() / 2);
		    }
		    
		    public void dragStop (InputEvent event, float x, float y, int pointer) {
		    	if (Intersector.overlaps(new Rectangle(bookImage.getX(), bookImage.getY(), bookImage.getWidth(), bookImage.getHeight()), new Rectangle(150, 30, 170, 190))) {
					backpack.play();
					bookImage.remove();
				}
		    	
		    	else if (Intersector.overlaps(new Rectangle(bookImage.getX(), bookImage.getY(), bookImage.getWidth(), bookImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
					System.out.println("Falsch!");
				}
			}
		    
		    
		});
		
		bookImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!dasbuch.isPlaying())
            	dasbuch.play();
            	return true;
            }
        });
		
		
		
		
		farbkastenImage = new Image(farbkasten);
		farbkastenImage.setBounds(540, 130, 75, 75);
		stage.addActor(farbkastenImage);
		
		farbkastenImage.addListener(new DragListener() {
		    public void drag(InputEvent event, float x, float y, int pointer) {
		        farbkastenImage.moveBy(x - farbkastenImage.getWidth() / 2, y - farbkastenImage.getHeight() / 2);
		    }
		    
		    public void dragStop (InputEvent event, float x, float y, int pointer) {
		    	if (Intersector.overlaps(new Rectangle(farbkastenImage.getX(), farbkastenImage.getY(), farbkastenImage.getWidth(), farbkastenImage.getHeight()), new Rectangle(150, 30, 170, 190))) {
		    		backpack.play();
		    		farbkastenImage.remove();
				}
		    	
		    	else if (Intersector.overlaps(new Rectangle(farbkastenImage.getX(), farbkastenImage.getY(), farbkastenImage.getWidth(), farbkastenImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
					System.out.println("Falsch!");
				}
			}
		});
		
		farbkastenImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!derfarbmalkasten.isPlaying())
            		derfarbmalkasten.play();
            	return true;
            }
        });
		
		kuscheltierImage = new Image(kuscheltier);
		kuscheltierImage.setBounds(640, 70, 75, 75);
		stage.addActor(kuscheltierImage);
		
		kuscheltierImage.addListener(new DragListener() {
		    public void drag(InputEvent event, float x, float y, int pointer) {
		        kuscheltierImage.moveBy(x - kuscheltierImage.getWidth() / 2, y - kuscheltierImage.getHeight() / 2);
		    }
		    
		    public void dragStop (InputEvent event, float x, float y, int pointer) {
		    	if (Intersector.overlaps(new Rectangle(kuscheltierImage.getX(), kuscheltierImage.getY(), kuscheltierImage.getWidth(), kuscheltierImage.getHeight()), new Rectangle(150, 30, 170, 190))) {
					System.out.println("Falsch!");
				}
		    	
		    	else if (Intersector.overlaps(new Rectangle(kuscheltierImage.getX(), kuscheltierImage.getY(), kuscheltierImage.getWidth(), kuscheltierImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		backpack.play();
		    		kuscheltierImage.remove();
				}
			}
		});
		
		kuscheltierImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!daskuscheltier.isPlaying())
            		daskuscheltier.play();
            	return true;
            }
        });
		
		dasbuch = Gdx.audio.newMusic(Gdx.files.internal("sounds/dasbuch.ogg"));
		derfarbmalkasten = Gdx.audio.newMusic(Gdx.files.internal("sounds/derfarbmalkasten.ogg"));
		daskuscheltier = Gdx.audio.newMusic(Gdx.files.internal("sounds/daskuscheltier.ogg"));
		backpack = Gdx.audio.newMusic(Gdx.files.internal("sounds/backpack.ogg"));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(rucksack, 150, 30, 170, 190);
		batch.draw(regal, 950, 30, 250, 250);
		
		//batch.draw(book, 410, 190, 75, 75);
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
