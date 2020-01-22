package com.scs.splitscreenfps.game;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.bitfire.postprocessing.PostProcessing;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Audio;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.entities.Ceiling;
import com.scs.splitscreenfps.game.entities.TextEntity;
import com.scs.splitscreenfps.game.levels.AbstractLevel;
import com.scs.splitscreenfps.game.levels.GameOverLevel;
import com.scs.splitscreenfps.game.levels.IntroLevel;
import com.scs.splitscreenfps.game.levels.PippoLevel;
import com.scs.splitscreenfps.game.player.Inventory;
import com.scs.splitscreenfps.game.player.Player;
import com.scs.splitscreenfps.game.renderable.GameShaderProvider;
import com.scs.splitscreenfps.game.systems.CollectionSystem;
import com.scs.splitscreenfps.game.systems.CycleThroughModelsSystem;
import com.scs.splitscreenfps.game.systems.CycleThruDecalsSystem;
import com.scs.splitscreenfps.game.systems.DrawDecalSystem;
import com.scs.splitscreenfps.game.systems.DrawModelSystem;
import com.scs.splitscreenfps.game.systems.DrawTextSystem;
import com.scs.splitscreenfps.game.systems.GotToExitSystem;
import com.scs.splitscreenfps.game.systems.MobAISystem;
import com.scs.splitscreenfps.game.systems.MovementSystem;
import com.scs.splitscreenfps.game.systems.RemoveAfterTimeSystem;
import com.scs.splitscreenfps.modules.IModule;

public class Game implements IModule {

	public static final float UNIT = 16f; // Square/box size

	public static final CollisionDetector collision = new CollisionDetector();
	public static final Art art = new Art();
	public static final Audio audio = new Audio();

	private SpriteBatch batch2d;
	private BitmapFont font_white, font_black;
	private ModelBatch batch;
	//private PerspectiveCamera camera;
	private ViewportData[] viewports;
	//private FrameBuffer frameBuffer = null;

	public Player player;
	public static World world;
	public Inventory inventory;
	public static BasicECS ecs;
	public ArrayList<ModelInstance> modelInstances;

	public static boolean levelComplete = false;
	public static boolean restartLevel = false;
	public Levels levels = new Levels();
	public static AbstractLevel gameLevel;

	public static int game_stage = -1;

	private PostProcessing post;

	public Game() {
		batch2d = new SpriteBatch();
		font_white = new BitmapFont(Gdx.files.internal("font/spectrum1white.fnt"));
		font_black = new BitmapFont(Gdx.files.internal("font/spectrum1black.fnt"));

		batch = new ModelBatch(new GameShaderProvider());

		viewports = new ViewportData[4];
		for (int i=0 ; i<viewports.length ; i++) {
			this.viewports[i] = new ViewportData(i);
		}
		/*
		camera = new PerspectiveCamera(65, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(10f, 0, 10f);
		camera.lookAt(11f, 0, 10f);
		camera.near = .5f;
		camera.far = 30f * Game.UNIT;
		camera.update();

		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		 */
		this.createECS();

		if (Settings.TEST_SPECIFIC_LEVEL == false) {
			this.gameLevel = new IntroLevel();
			gameLevel.load(this);
		} else {
			this.game_stage = 0;
			startGame();
		}

		if (Gdx.app.getType() != ApplicationType.WebGL) {
			//post = new PostProcessing();
		}

	}


	private void startGame() {
		inventory = new Inventory();
		player = new Player(this.viewports[0].camera, inventory, 1, 4);
		levelComplete = true; // So we load the first level 
	}


	private void createECS() {
		ecs = new BasicECS();
		ecs.addSystem(new DrawDecalSystem(ecs, this.viewports[0].camera)); // todo
		ecs.addSystem(new CycleThruDecalsSystem(ecs));
		ecs.addSystem(new CycleThroughModelsSystem(ecs));
		ecs.addSystem(new MobAISystem(ecs, player));
		ecs.addSystem(new MovementSystem(ecs, player));
		ecs.addSystem(new DrawModelSystem(ecs, batch));
		ecs.addSystem(new RemoveAfterTimeSystem(ecs));
		ecs.addSystem(new CollectionSystem(ecs));
		ecs.addSystem(new DrawTextSystem(ecs, batch2d, font_white));
		ecs.addSystem(new GotToExitSystem(ecs, player));

		world = new World();
	}


	public void update() {
		if (Settings.RELEASE_MODE == false) {
			// Cheat mode!
			if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
				this.levelComplete = true;
				/*if (Settings.DEBUG_LEVEL_JUMP) {
					Settings.p("X pressed");
				}*/
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (this.game_stage == -1) {
				this.game_stage = 0;
				this.startGame();
			}
		}

		if (levelComplete) {
			if (this.gameLevel instanceof GameOverLevel) {
				this.startGame();
			}
			levelComplete = false;
			levels.nextLevel();
			restartLevel = true;
			audio.stopMusic();
			audio.play("zxspectrumloadingnoise.ogg");
		}		
		if (restartLevel) {
			restartLevel = false;

			this.createECS();

			if (Settings.TEST_SPECIFIC_LEVEL == false) {
				if (player.getLives() >= 0) {
					gameLevel = levels.getLevel();
				} else {
					gameLevel = new GameOverLevel();
				}
			} else {
				//gameLevel = new GameOverLevel();
				//gameLevel = new OhMummyLevel(0);
				//gameLevel = new GulpmanLevel(0);
				//gameLevel = new MinedOutLevel(0);
				//gameLevel = new MonsterMazeLevel(0);
				//gameLevel = new ChaosLevel(0);
				//gameLevel = new AliensLevel(0);
				//gameLevel = new ChaosLevel(0);
				//gameLevel = new AndroidsLevel(0);
				gameLevel = new PippoLevel(0);
			}

			loadLevel();
			ecs.addEntity(new Ceiling("gamer1.jpg", -10, -10, 40, 40, false, Game.UNIT*8));
			ecs.addEntity(player);

			/*if (Settings.DEBUG_LEVEL_JUMP) {
				Settings.p("New level is " + gameLevel.getClass().getSimpleName());
			}*/

			if (gameLevel.GetName().length() > 0) {
				AbstractEntity text = new TextEntity("LOADING: " + gameLevel.GetName(), 30, 30, 4);
				ecs.addEntity(text);
			}

			if (gameLevel.getMusicFilename().length() > 0) {
				audio.startMusic(gameLevel.getMusicFilename());				
			}
		}

		if (player != null) {
			player.update();
		}
		//camera.update();
		for (int i=0 ; i<viewports.length ; i++) {
			this.viewports[i].camera.update();
		}

		this.ecs.getSystem(RemoveAfterTimeSystem.class).process();
		this.ecs.addAndRemoveEntities();
		if (this.game_stage == 0) {
			this.ecs.getSystem(MobAISystem.class).process();
		}
		this.ecs.getSystem(MovementSystem.class).process();
		this.ecs.getSystem(CollectionSystem.class).process();
		if (this.game_stage == 0) {
			this.ecs.getSystem(GotToExitSystem.class).process();
		}
		gameLevel.update(this, world);
	}


	public void render() {
		if (post != null) {
			post.update(Gdx.graphics.getDeltaTime());
		}

		for (int viewid=0 ; viewid<viewports.length ; viewid++) {
			ViewportData viewportData = this.viewports[viewid];
			//Gdx.gl.glViewport(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Gdx.gl.glViewport(viewportData.viewPos.x, viewportData.viewPos.y, viewportData.viewPos.width, viewportData.viewPos.height);

			//frameBuffer.begin();
			viewportData.frameBuffer.begin();

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

			this.gameLevel.setBackgroundColour();

			batch.begin(viewportData.camera);
			if (modelInstances != null) {
				for (int i = 0; i < modelInstances.size(); i++) {
					batch.render(modelInstances.get(i));
				}
			}

			if (ecs != null) {
				this.ecs.getSystem(DrawModelSystem.class).process();
			}
			batch.end();
			/*

			if (ecs != null) {
				this.ecs.getSystem(CycleThruDecalsSystem.class).process();
				//this.ecs.getSystem(DrawDecalSystem.class).process();
				this.ecs.getSystem(CycleThroughModelsSystem.class).process();
			}
			
			batch2d.begin();
			if (inventory != null) {
				inventory.render(batch2d, player);
			}
			if (player != null) {
				player.render(batch2d);
			}
			if (ecs != null) {
				this.ecs.getSystem(DrawTextSystem.class).process();
			}
			batch2d.end();
*/
			//frameBuffer.end();
			viewportData.frameBuffer.end();
/*
			if (post != null) {
				post.begin();
			}
*/
			//Draw buffer and FPS
			batch2d.begin();

			float c = 1.0f;
			batch2d.setColor(c,c,c,1);
			//batch2d.draw(frameBuffer.getColorBufferTexture(), 0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), - Gdx.graphics.getHeight());
			//batch2d.draw(viewportData.frameBuffer.getColorBufferTexture(), 0, viewportData.viewPos.height, viewportData.viewPos.width, -viewportData.viewPos.height);
			batch2d.draw(viewportData.frameBuffer.getColorBufferTexture(), viewportData.viewPos.x, viewportData.viewPos.y);

			/*
			if (player != null) {
				player.renderUI(batch2d, font_white);
			}
			gameLevel.renderUI(batch2d, font_white, font_black);
*/
			if (Settings.SHOW_FPS) {
				font_white.draw(batch2d, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 20);
			}

			batch2d.end();
			if (post != null) {
				post.end();
			}
			//break;
		}
	}


	private void loadLevel() {
		modelInstances = new ArrayList<ModelInstance>();
		gameLevel.load(this);

		if (gameLevel.getPlayerStartMapX() < 0 || gameLevel.getPlayerStartMapY() < 0) {
			throw new RuntimeException ("No player start position set");
		}
		PositionData posData = (PositionData)this.player.getComponent(PositionData.class);
		posData.position.set(gameLevel.getPlayerStartMapX()*Game.UNIT+(Game.UNIT/2), 0, gameLevel.getPlayerStartMapY()*Game.UNIT+(Game.UNIT/2)); // Start in middle of square

		player.update();
		for (int viewid=0 ; viewid<viewports.length ; viewid++) {
			ViewportData viewport = this.viewports[viewid];
			Camera camera = viewport.camera;
			camera.rotate(Vector3.Y, (float)Math.toDegrees(Math.atan2(camera.direction.z, camera.direction.x)));
			//this.viewports[0].camera.rotate(Vector3.Y, (float)Math.toDegrees(Math.atan2(camera.direction.z, camera.direction.x)));

			camera.update();
			//this.viewports[0].camera.update();
		}
	}


	@Override
	public void resize(int w, int h) {
	}


	public void destroy() {
		if (post != null) {
			post.dispose();
		}
		font_white.dispose(); 
		font_black.dispose();
		audio.dipose();
		batch.dispose();
		batch2d.dispose();
	}


	@Override
	public boolean isFinished() {
		return false; // Never finishes
	}


	@Override
	public void setFullScreen(boolean fullscreen) {
		batch2d.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // scs todo?
	}

}

