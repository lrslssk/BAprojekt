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
	Texture heft;
	Texture auto;
	Texture etui;
	Texture wecker;
	
	Image bookImage;
	Image farbkastenImage;
	Image kuscheltierImage;
	Image heftImage;
	Image autoImage;
	Image etuiImage;
	Image weckerImage;
	
	Music dasbuch;
	Music daskuscheltier;
	Music derfarbmalkasten;
	Music dasheft;
	Music dasauto;
	Music dasetui;
	Music derwecker;
	
	Music backpack;
	Music wrong;
	private float bubbletimer;
	private String currentItemName = "";
	private Texture speechbubble;
	
	private int itemCount = 7;
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
		heft = new Texture(Gdx.files.internal("heft.png"));
		auto = new Texture(Gdx.files.internal("auto.png"));
		etui = new Texture(Gdx.files.internal("etui.png"));
		wecker = new Texture(Gdx.files.internal("wecker.png"));
		
		
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
		
		
		
		
		heftImage = new Image(heft);
		heftImage.setBounds(640, 170, 75, 75);
		stage.addActor(heftImage);
		
		heftImage.addListener(new DragListener() {
		    public void drag(InputEvent event, float x, float y, int pointer) {
		        heftImage.moveBy(x - heftImage.getWidth() / 2, y - heftImage.getHeight() / 2);
		    }
		    
		    public void dragStop (InputEvent event, float x, float y, int pointer) {
		    	if (Intersector.overlaps(new Rectangle(heftImage.getX(), heftImage.getY(), heftImage.getWidth(), heftImage.getHeight()), new Rectangle(150, 30, 170, 190))) {
		    		backpack.play();
		    		heftImage.remove();
		    		itemCount--;
		    	}
		    	
		    	else if (Intersector.overlaps(new Rectangle(heftImage.getX(), heftImage.getY(), heftImage.getWidth(), heftImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		wrong.play();
				}
			}
		});
		
		heftImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!dasheft.isPlaying())
            		dasheft.play();
            	
            	showNameBubble("Das Heft");
            	return true;
            }
        });
		
		
		
		
		autoImage = new Image(auto);
		autoImage.setBounds(540, 220, 66, 50);
		stage.addActor(autoImage);
		
		autoImage.addListener(new DragListener() {
		    public void drag(InputEvent event, float x, float y, int pointer) {
		        autoImage.moveBy(x - autoImage.getWidth() / 2, y - autoImage.getHeight() / 2);
		    }
		    
		    public void dragStop (InputEvent event, float x, float y, int pointer) {
		    	if (Intersector.overlaps(new Rectangle(autoImage.getX(), autoImage.getY(), autoImage.getWidth(), autoImage.getHeight()), new Rectangle(150, 30, 170, 190))) {
		    		wrong.play();
		    	}
		    	
		    	else if (Intersector.overlaps(new Rectangle(autoImage.getX(), autoImage.getY(), autoImage.getWidth(), autoImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		backpack.play();
		    		autoImage.remove();
		    		itemCount--;
				}
			}
		});
		
		autoImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!dasauto.isPlaying())
            		dasauto.play();
            	
            	showNameBubble("Das Auto");
            	return true;
            }
        });
		
		
		
		etuiImage = new Image(etui);
		etuiImage.setBounds(740, 50, 100, 75);
		stage.addActor(etuiImage);
		
		etuiImage.addListener(new DragListener() {
		    public void drag(InputEvent event, float x, float y, int pointer) {
		        etuiImage.moveBy(x - etuiImage.getWidth() / 2, y - etuiImage.getHeight() / 2);
		    }
		    
		    public void dragStop (InputEvent event, float x, float y, int pointer) {
		    	if (Intersector.overlaps(new Rectangle(etuiImage.getX(), etuiImage.getY(), etuiImage.getWidth(), etuiImage.getHeight()), new Rectangle(150, 30, 170, 190))) {
		    		backpack.play();
		    		etuiImage.remove();
		    		itemCount--;
		    	}
		    	
		    	else if (Intersector.overlaps(new Rectangle(etuiImage.getX(), etuiImage.getY(), etuiImage.getWidth(), etuiImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		wrong.play();
				}
			}
		});
		
		etuiImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!dasetui.isPlaying())
            		dasetui.play();
            	
            	showNameBubble("Das Etui");
            	return true;
            }
        });
		
		
		
		weckerImage = new Image(wecker);
		weckerImage.setBounds(440, 50, 75, 75);
		stage.addActor(weckerImage);
		
		weckerImage.addListener(new DragListener() {
		    public void drag(InputEvent event, float x, float y, int pointer) {
		        weckerImage.moveBy(x - weckerImage.getWidth() / 2, y - weckerImage.getHeight() / 2);
		    }
		    
		    public void dragStop (InputEvent event, float x, float y, int pointer) {
		    	if (Intersector.overlaps(new Rectangle(weckerImage.getX(), weckerImage.getY(), weckerImage.getWidth(), weckerImage.getHeight()), new Rectangle(150, 30, 170, 190))) {
		    		wrong.play();
		    	}
		    	
		    	else if (Intersector.overlaps(new Rectangle(weckerImage.getX(), weckerImage.getY(), weckerImage.getWidth(), weckerImage.getHeight()), new Rectangle(950, 30, 250, 250))) {
		    		backpack.play();
		    		weckerImage.remove();
		    		itemCount--;
				}
			}
		});
		
		weckerImage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(!derwecker.isPlaying())
            		derwecker.play();
            	
            	showNameBubble("Der Wecker");
            	return true;
            }
        });
		
		
		dasbuch = Gdx.audio.newMusic(Gdx.files.internal("sounds/dasbuch.ogg"));
		derfarbmalkasten = Gdx.audio.newMusic(Gdx.files.internal("sounds/derfarbmalkasten.ogg"));
		daskuscheltier = Gdx.audio.newMusic(Gdx.files.internal("sounds/daskuscheltier.ogg"));
		dasheft = Gdx.audio.newMusic(Gdx.files.internal("sounds/dasheft.ogg"));
		dasauto = Gdx.audio.newMusic(Gdx.files.internal("sounds/dasauto.ogg"));
		dasetui = Gdx.audio.newMusic(Gdx.files.internal("sounds/dasetui.ogg"));
		derwecker = Gdx.audio.newMusic(Gdx.files.internal("sounds/derwecker.ogg"));
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
