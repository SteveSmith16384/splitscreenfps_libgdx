package com.scs.splitscreenfps.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.BillBoardFPS_Main;
import com.scs.splitscreenfps.IModule;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfps.game.entities.TextEntity;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.levels.AbstractLevel;
import com.scs.splitscreenfps.game.levels.CarParkLevel;
import com.scs.splitscreenfps.game.levels.DeathChaseLevel;
import com.scs.splitscreenfps.game.levels.DungeonLevel;
import com.scs.splitscreenfps.game.levels.FTLLevel;
import com.scs.splitscreenfps.game.levels.FarmLevel;
import com.scs.splitscreenfps.game.levels.MonsterMazeLevel;
import com.scs.splitscreenfps.game.levels.TagLevel;
import com.scs.splitscreenfps.game.levels.TowerDefenceLevel;
import com.scs.splitscreenfps.game.systems.AnimationSystem;
import com.scs.splitscreenfps.game.systems.CollisionCheckSystem;
import com.scs.splitscreenfps.game.systems.CycleThroughModelsSystem;
import com.scs.splitscreenfps.game.systems.CycleThruDecalsSystem;
import com.scs.splitscreenfps.game.systems.DrawDecalSystem;
import com.scs.splitscreenfps.game.systems.DrawGuiSpritesSystem;
import com.scs.splitscreenfps.game.systems.DrawModelSystem;
import com.scs.splitscreenfps.game.systems.DrawTextIn3DSpaceSystem;
import com.scs.splitscreenfps.game.systems.DrawTextSystem;
import com.scs.splitscreenfps.game.systems.MoveAStarSystem;
import com.scs.splitscreenfps.game.systems.MovementSystem;
import com.scs.splitscreenfps.game.systems.PickupDropSystem;
import com.scs.splitscreenfps.game.systems.PlayerInputSystem;
import com.scs.splitscreenfps.game.systems.RemoveEntityAfterTimeSystem;
import com.scs.splitscreenfps.game.systems.RespawnSystem;
import com.scs.splitscreenfps.game.systems.VehicleMovementSystem;

public class Game implements IModule {

	private BillBoardFPS_Main main;
	private SpriteBatch batch2d;
	public BitmapFont font_small, font_med, font_large;
	public final ViewportData[] viewports;

	public AbstractPlayersAvatar[] players;
	public List<IInputMethod> inputs;
	public MapData mapData;
	public BasicECS ecs;
	private AbstractLevel currentLevel;

	private int game_stage;
	private long restartTime;
	private List<AbstractEntity> losers = new ArrayList<AbstractEntity>();

	// Specific systems 
	private DrawModelSystem drawModelSystem;
	public RespawnSystem respawnSystem;
	
	public int currentViewId;
	public AssetManager assetManager = new AssetManager();

	public Game(BillBoardFPS_Main _main, List<IInputMethod> _inputs) {
		main = _main;
		inputs = _inputs;
		
		BillBoardFPS_Main.audio.startMusic("shared/Heroic Demise (New).mp3");

		game_stage = 0;
		batch2d = new SpriteBatch();
		this.createECS();

		viewports = new ViewportData[4];
		players = new AbstractPlayersAvatar[inputs.size()];
		for (int i=0 ; i<players.length ; i++) {
			this.viewports[i] = new ViewportData(false, i);
			//IInputMethod input = inputs.get(i);
			//players[i] = new PlayersAvatar(this, i, this.viewports[i], input);
			//ecs.addEntity(players[i]);
		}

		switch (Settings.CURRENT_MODE) {
		case Settings.MODE_TAG:
			currentLevel = new TagLevel(this);
			break;
		case Settings.MODE_MM:
			currentLevel = new MonsterMazeLevel(this);
			break;
		case Settings.MODE_DUNGEON:
			currentLevel = new DungeonLevel(this);
			break;
		case Settings.MODE_FARM:
			currentLevel = new FarmLevel(this);
			break;
		case Settings.MODE_FTL:
			currentLevel = new FTLLevel(this);
			break;
		case Settings.MODE_CAR_PARK:
			currentLevel = new CarParkLevel(this);
			break;
		case Settings.MODE_DEATHCHASE:
			currentLevel = new DeathChaseLevel(this);
			break;
		case Settings.MODE_TOWER_DEFENCE:
			currentLevel = new TowerDefenceLevel(this);
			break;
		default:
			throw new RuntimeException("Unknown mode: " + Settings.CURRENT_MODE);
		}

		currentLevel.loadAvatars();
		loadLevel();
		this.loadAssetsForRescale(); // Need this to load font

		this.currentLevel.addSystems(ecs);

		for (int i=0 ; i<players.length ; i++) {
			this.currentLevel.setupAvatars(this.players[i], i);
		}

	}


	private void loadAssetsForRescale() {
		this.currentLevel.loadAssets();
		DrawGuiSpritesSystem sys = (DrawGuiSpritesSystem)this.ecs.getSystem(DrawGuiSpritesSystem.class);
		sys.rescaleSprites();
	}


	public void resizeViewports(boolean full_screen) {
		for (int i=0 ; i<players.length ; i++) {
			this.viewports[i].resize(full_screen, i);
		}
	}


	private void createECS() {
		ecs = new BasicECS();
		ecs.addSystem(new PlayerInputSystem(this));
		ecs.addSystem(new CollisionCheckSystem(ecs));
		ecs.addSystem(new DrawDecalSystem(this, ecs));
		ecs.addSystem(new CycleThruDecalsSystem(ecs));
		ecs.addSystem(new CycleThroughModelsSystem(ecs));
		ecs.addSystem(new MovementSystem(this, ecs));
		ecs.addSystem(new RemoveEntityAfterTimeSystem(ecs));
		ecs.addSystem(new DrawTextSystem(ecs, this, batch2d));
		ecs.addSystem(new AnimationSystem(ecs));
		ecs.addSystem(new DrawGuiSpritesSystem(ecs, this, this.batch2d));
		ecs.addSystem(new MoveAStarSystem(ecs, this));
		this.drawModelSystem = new DrawModelSystem(this, ecs); 
		ecs.addSystem(this.drawModelSystem);
		ecs.addSystem(new PickupDropSystem(ecs, this));
		ecs.addSystem(new DrawTextIn3DSpaceSystem(ecs, this, batch2d));
		ecs.addSystem(new VehicleMovementSystem(ecs));
		respawnSystem = new RespawnSystem(ecs); 
		ecs.addSystem(respawnSystem);
	}


	private void loadLevel() {
		currentLevel.load();

		// Set start position of players
		for (int idx=0 ; idx<players.length  ; idx++) {
			PositionComponent posData = (PositionComponent)this.players[idx].getComponent(PositionComponent.class);
			posData.position.set(currentLevel.getPlayerStartMap(idx).x + 0.5f, Settings.PLAYER_HEIGHT/2, currentLevel.getPlayerStartMap(idx).y + 0.5f); // Start in middle of square
			players[idx].update();
			
			// Look down the z-axis
			this.viewports[idx].camera.direction.x = 0;
			this.viewports[idx].camera.direction.z = 1;
			this.viewports[idx].camera.update();
		}
	}


	@Override
	public void render() {
		if (this.game_stage == 1) {
			if (this.restartTime < System.currentTimeMillis()) {
				this.main.next_module = new Game(main, this.inputs);
				return;
			}
		}

		this.ecs.events.clear();
		this.ecs.getSystem(RemoveEntityAfterTimeSystem.class).process();
		this.respawnSystem.process();
		this.ecs.addAndRemoveEntities();
		this.ecs.getSystem(PlayerInputSystem.class).process();
		this.ecs.getSystem(MoveAStarSystem.class).process();
		this.ecs.processSystem(VehicleMovementSystem.class);
		this.ecs.getSystem(MovementSystem.class).process();
		this.ecs.getSystem(AnimationSystem.class).process();
		this.ecs.getSystem(PickupDropSystem.class).process();
		
		currentLevel.update();

		for (currentViewId=0 ; currentViewId<players.length ; currentViewId++) {
			ViewportData viewportData = this.viewports[currentViewId];

			if (viewportData.post != null) {
				viewportData.post.update(Gdx.graphics.getDeltaTime());
			}

			Gdx.gl.glViewport(viewportData.viewPos.x, viewportData.viewPos.y, viewportData.viewPos.width, viewportData.viewPos.height);

			viewportData.frameBuffer.begin();

			this.currentLevel.setBackgroundColour();
			//Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
			//Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

			if (viewportData.post != null) {
				viewportData.post.begin();
			}

			this.ecs.getSystem(CycleThroughModelsSystem.class).process();
			this.drawModelSystem.process(viewportData.camera);
			this.ecs.getSystem(CycleThruDecalsSystem.class).process();
			this.ecs.getSystem(DrawDecalSystem.class).process();

			batch2d.begin();
			this.ecs.getSystem(DrawTextIn3DSpaceSystem.class).process();
			this.ecs.getSystem(DrawTextSystem.class).process();
			this.ecs.getSystem(DrawGuiSpritesSystem.class).process();

			//font_white.draw(batch2d, "Screen " + this.currentViewId, 10, 250);
			/*if (this.game_stage == 1) {
				if (this.losers.contains(this.players[this.currentViewId])) {
					font.setColor(0, 1, 0, 1);
					font.draw(batch2d, "YOU HAVE LOST!", 10, Gdx.graphics.getBackBufferHeight()/2);
				} else {
					font.setColor(0, 1, 1, 1);
					font.draw(batch2d, "YOU HAVE WON!", 10, Gdx.graphics.getBackBufferHeight()/2);
				}
			}*/

			currentLevel.renderUI(batch2d, currentViewId);

			/*if (players[currentViewId] != null) {
				players[currentViewId].renderUI(batch2d, font);
			}*/

			/*if (Settings.TEST_SCREEN_COORDS) {
				font.draw(batch2d, "TL", 20, 20);
				font.draw(batch2d, "50", 50, 50);
				font.draw(batch2d, "150", 150, 150);
				font.draw(batch2d, "TR", Gdx.graphics.getBackBufferWidth()-20, 20);
				font.draw(batch2d, "BL", 10, Gdx.graphics.getBackBufferHeight()-20);
				font.draw(batch2d, "BR", Gdx.graphics.getBackBufferWidth()-20, Gdx.graphics.getBackBufferHeight()-20);
			}*/

			batch2d.end();

			if (viewportData.post != null) {
				viewportData.post.end();
			}

			viewportData.frameBuffer.end();

			//Draw buffer and FPS
			batch2d.begin();
			batch2d.draw(viewportData.frameBuffer.getColorBufferTexture(), viewportData.viewPos.x, viewportData.viewPos.y+viewportData.viewPos.height, viewportData.viewPos.width, -viewportData.viewPos.height);
			if (Settings.SHOW_FPS) {
				if (font_small != null) {
					font_small.draw(batch2d, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, font_small.getLineHeight()*2);
				}
			}

			batch2d.end();

		}
	}


	@Override
	public void resize(int w, int h) {
		//float scale = w / prev_width;
		this.loadAssetsForRescale();
		//this.resizeViewports(false);
		//this.prev_width = w;
	}


	@Override
	public void dispose() {
		for (currentViewId=0 ; currentViewId<players.length ; currentViewId++) {
			ViewportData viewportData = this.viewports[currentViewId];
			viewportData.dispose();
		}
		font_small.dispose(); 
		font_med.dispose(); 
		font_large.dispose(); 
		batch2d.dispose();
	}


	@Override
	public void setFullScreen(boolean fullscreen) {
		batch2d.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.resizeViewports(true);
	}


	public void playerHasLost(AbstractEntity avatar) {
		if (this.game_stage != 0) {
			return;
		}

		this.losers.clear();
		this.losers.add(avatar);
		loadWinLoseText();
	}


	public void playerHasWon(AbstractEntity winner) {
		if (this.game_stage != 0) {
			return;
		}

		this.losers.clear();
		for(AbstractEntity player : this.players) {
			if (player != winner) {
				this.losers.add(player);
			}
		}
		loadWinLoseText();
	}


	private void loadWinLoseText() {
		for (int i=0 ; i<this.players.length ; i++) {
			if (this.losers.contains(this.players[i])) {
				TextEntity te = new TextEntity(ecs, "YOU HAVE LOST!", Gdx.graphics.getBackBufferHeight()/2, 5, new Color(0, 0, 1, 1), i, 2);
				ecs.addEntity(te);
			} else {
				TextEntity te = new TextEntity(ecs, "YOU HAVE WON!", Gdx.graphics.getBackBufferHeight()/2, 5, new Color(0, 1, 0, 1), i, 1);
				ecs.addEntity(te);
			}
		}
		this.game_stage = 1;
		this.restartTime = System.currentTimeMillis() + 5000;
	}

}

