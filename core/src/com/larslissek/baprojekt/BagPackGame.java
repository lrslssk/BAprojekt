package com.larslissek.baprojekt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	BitmapFont font;
	
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
	Music wrong;
	private float bubbletimer;
	private String currentItemName = "";
	private Texture speechbubble;
	
	private int itemCount = 3;
	private float endGameTimer = 0;
	
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
		parameter.color = Color.BLACK;
		font = generator.generateFont(parameter);
		
		
		background = new Texture(Gdx.files.internal("testroom.jpg"));
		rucksack = new Texture(Gdx.files.internal("rucksack.png"));
		regal = new Texture(Gdx.files.internal("shelf.png"));
		
		book = new Texture(Gdx.files.internal("book.png"));
		farbkasten = new Texture(Gdx.files.internal("malkasten.png"));
		kuscheltier = new Texture(Gdx.files.internal("baer.png"));
		
		
		speechbubble = new Texture(Gdx.files.internal("speechbubble.png"));
		
		
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
					itemCount--;
				}
		    	
		    	else if (Intersector.overlaps(new Rectangle(bookImage.getX(), bookImage.getY(), bookImage.getWidth(), bookImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		wrong.play();
		    	}
			}
		    
		    
		});
		
		bookImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!dasbuch.isPlaying())
            	dasbuch.play();
            	
            	showNameBubble("Das Buch");
            	
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
		    		itemCount--;
				}
		    	
		    	else if (Intersector.overlaps(new Rectangle(farbkastenImage.getX(), farbkastenImage.getY(), farbkastenImage.getWidth(), farbkastenImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		wrong.play();
		    	}
			}
		});
		
		farbkastenImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!derfarbmalkasten.isPlaying())
            		derfarbmalkasten.play();
            	
            	showNameBubble("Der Farbmalkasten");
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
		    		wrong.play();
		    	}
		    	
		    	else if (Intersector.overlaps(new Rectangle(kuscheltierImage.getX(), kuscheltierImage.getY(), kuscheltierImage.getWidth(), kuscheltierImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		backpack.play();
		    		kuscheltierImage.remove();
		    		itemCount--;
				}
			}
		});
		
		kuscheltierImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!daskuscheltier.isPlaying())
            		daskuscheltier.play();
            	
            	showNameBubble("Das Kuscheltier");
            	return true;
            }
        });
		
		dasbuch = Gdx.audio.newMusic(Gdx.files.internal("sounds/dasbuch.ogg"));
		derfarbmalkasten = Gdx.audio.newMusic(Gdx.files.internal("sounds/derfarbmalkasten.ogg"));
		daskuscheltier = Gdx.audio.newMusic(Gdx.files.internal("sounds/daskuscheltier.ogg"));
		backpack = Gdx.audio.newMusic(Gdx.files.internal("sounds/backpack.ogg"));
		wrong = Gdx.audio.newMusic(Gdx.files.internal("sounds/wrong.ogg"));
	}

	protected void showNameBubble(String string) {
		bubbletimer = 3f;
		currentItemName = string;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		//test
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(rucksack, 150, 30, 170, 190);
		batch.draw(regal, 950, 30, 250, 250);
		
		//batch.draw(book, 410, 190, 75, 75);
		
		
		if(bubbletimer > 0){
			batch.draw(speechbubble, 450, 600, 400, 100);
			font.draw(batch, currentItemName + "", 495, 675);
			bubbletimer -= delta;
		}
		
		batch.end();
		
		stage.act();
		stage.draw();
		
		Gdx.graphics.setTitle("BAprojekt | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		if(itemCount == 0){
			endGameTimer += delta;
		}
		
		if(endGameTimer >= 3){
			game.setScreen(new MainMenu(game));
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
