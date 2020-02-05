package com.scs.splitscreenfps.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Audio;
import com.scs.splitscreenfps.IModule;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.entities.EntityFactory;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.MouseAndKeyboardInputMethod;
import com.scs.splitscreenfps.game.input.NoInputMethod;
import com.scs.splitscreenfps.game.levels.AbstractLevel;
import com.scs.splitscreenfps.game.levels.OpenRoomLevel;
import com.scs.splitscreenfps.game.player.PlayersAvatar;
import com.scs.splitscreenfps.game.systems.AnimationSystem;
import com.scs.splitscreenfps.game.systems.CollectionSystem;
import com.scs.splitscreenfps.game.systems.CollisionCheckSystem;
import com.scs.splitscreenfps.game.systems.CycleThroughModelsSystem;
import com.scs.splitscreenfps.game.systems.CycleThruDecalsSystem;
import com.scs.splitscreenfps.game.systems.DrawDecalSystem;
import com.scs.splitscreenfps.game.systems.DrawGuiSpritesSystem;
import com.scs.splitscreenfps.game.systems.DrawModelSystem;
import com.scs.splitscreenfps.game.systems.DrawTextSystem;
import com.scs.splitscreenfps.game.systems.MobAISystem;
import com.scs.splitscreenfps.game.systems.MovementSystem;
import com.scs.splitscreenfps.game.systems.PickupSystem;
import com.scs.splitscreenfps.game.systems.PlayerInputSystem;
import com.scs.splitscreenfps.game.systems.RemoveAfterTimeSystem;

public class Game implements IModule {

	public static final Graphics art = new Graphics();
	public static final Audio audio = new Audio();

	private SpriteBatch batch2d;
	private final BitmapFont font_white, font_black;
	public final ViewportData[] viewports;

	public PlayersAvatar[] players;
	public MapData mapData;
	public BasicECS ecs;
	public EntityFactory entityFactory = new EntityFactory();
	private AbstractLevel gameLevel;
	private DrawModelSystem drawModelSystem;

	public int currentViewId;

	public Game() {
		batch2d = new SpriteBatch();
		font_white = new BitmapFont(Gdx.files.internal("font/spectrum1white.fnt"));
		font_black = new BitmapFont(Gdx.files.internal("font/spectrum1black.fnt"));

		viewports = new ViewportData[4];
		players = new PlayersAvatar[4];
		for (int i=0 ; i<viewports.length ; i++) {
			this.viewports[i] = new ViewportData(false, i);
			IInputMethod input = i==0 ? new MouseAndKeyboardInputMethod() : new NoInputMethod();
			players[i] = new PlayersAvatar(i, this.viewports[i], input);
		}

		this.createECS();

		gameLevel = new OpenRoomLevel(this); //LoadMapDynamicallyLevel(this);//SPDLevelTest(this);//CleanTheLitterLevel(this);//MonsterMazeLevel(this);
		loadLevel();

		for (int i=0 ; i<4 ; i++) {
			ecs.addEntity(players[i]);
		}

	}


	public void resizeViewports(boolean full_screen) {
		for (int i=0 ; i<viewports.length ; i++) {
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
		ecs.addSystem(new MobAISystem(this, ecs));
		ecs.addSystem(new MovementSystem(this, ecs));
		ecs.addSystem(new RemoveAfterTimeSystem(ecs));
		ecs.addSystem(new DrawTextSystem(ecs, batch2d, font_white));
		ecs.addSystem(new CollectionSystem(ecs));
		ecs.addSystem(new AnimationSystem(ecs));
		ecs.addSystem(new PickupSystem(ecs, this));
		ecs.addSystem(new DrawGuiSpritesSystem(ecs, this.batch2d));

		this.drawModelSystem = new DrawModelSystem(this, ecs); 
		ecs.addSystem(this.drawModelSystem);
	}


	private void loadLevel() {
		gameLevel.load();

		// Set start position of players
		for (int idx=0 ; idx<4 ; idx++) {
			PositionData posData = (PositionData)this.players[idx].getComponent(PositionData.class);
			posData.position.set(gameLevel.getPlayerStartMap(idx).x + 0.5f, Settings.PLAYER_HEIGHT/2, gameLevel.getPlayerStartMap(idx).y + 0.5f); // Start in middle of square
			players[idx].update();

			// Move model if it has one
			HasModel hasModel = (HasModel)this.players[idx].getComponent(HasModel.class);
			if (hasModel != null) {
				hasModel.model.transform.setTranslation(posData.position);
			}

			ViewportData viewport = this.viewports[idx];
			Camera camera = viewport.camera;
			camera.position.set(posData.position);
			camera.position.y += Settings.CAMERA_HEIGHT_OFFSET;
			camera.rotate(Vector3.Y, (float)Math.toDegrees(Math.atan2(camera.direction.z, camera.direction.x)));
			//this.viewports[0].camera.rotate(Vector3.Y, (float)Math.toDegrees(Math.atan2(camera.direction.z, camera.direction.x)));

			camera.update();
		}
	}


	@Override
	public void render() {
		this.ecs.getSystem(RemoveAfterTimeSystem.class).process();
		this.ecs.addAndRemoveEntities();
		this.ecs.getSystem(PlayerInputSystem.class).process();
		this.ecs.getSystem(MobAISystem.class).process();
		this.ecs.getSystem(MovementSystem.class).process();
		this.ecs.getSystem(CollectionSystem.class).process();
		this.ecs.getSystem(AnimationSystem.class).process();
		this.ecs.getSystem(PickupSystem.class).process();

		gameLevel.update(this, mapData);

		for (currentViewId=0 ; currentViewId<viewports.length ; currentViewId++) {
			ViewportData viewportData = this.viewports[currentViewId];
			if (viewportData.post != null) {
				viewportData.post.update(Gdx.graphics.getDeltaTime());
			}

			Gdx.gl.glViewport(viewportData.viewPos.x, viewportData.viewPos.y, viewportData.viewPos.width, viewportData.viewPos.height);

			viewportData.frameBuffer.begin();

			//Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			this.gameLevel.setBackgroundColour();

			//this.drawModelSystem.process(true, viewportData.camera); Doesn't work.
			this.drawModelSystem.process(false, viewportData.camera);

			this.ecs.getSystem(CycleThruDecalsSystem.class).process();
			this.ecs.getSystem(DrawDecalSystem.class).process();
			this.ecs.getSystem(CycleThroughModelsSystem.class).process();

			batch2d.begin();
			this.ecs.getSystem(DrawTextSystem.class).process();
			this.ecs.getSystem(DrawGuiSpritesSystem.class).process();
			batch2d.end();

			viewportData.frameBuffer.end();

			if (viewportData.post != null) {
				viewportData.post.begin();
			}

			//Draw buffer and FPS
			batch2d.begin();

			float c = 1.0f;
			batch2d.setColor(c,c,c,1);
			batch2d.draw(viewportData.frameBuffer.getColorBufferTexture(), viewportData.viewPos.x, viewportData.viewPos.y+viewportData.viewPos.height, viewportData.viewPos.width, -viewportData.viewPos.height);

			if (players[currentViewId] != null) {
				players[currentViewId].renderUI(batch2d, font_white);
			}

			gameLevel.renderUI(batch2d, font_white, font_black);

			if (Settings.SHOW_FPS) {
				font_white.draw(batch2d, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 20);
			}

			batch2d.end();
			if (viewportData.post != null) {
				viewportData.post.end();
			}
		}
	}


	@Override
	public void resize(int w, int h) {
		//this.resizeViewports(false);
	}


	@Override
	public void dispose() {
		for (currentViewId=0 ; currentViewId<viewports.length ; currentViewId++) {
			ViewportData viewportData = this.viewports[currentViewId];
			viewportData.dispose();
		}
		/*if (post != null) {
			post.dispose();
		}*/
		font_white.dispose(); 
		font_black.dispose();
		audio.dipose();
		//todo batch.dispose();
		batch2d.dispose();
	}


	@Override
	public void setFullScreen(boolean fullscreen) {
		batch2d.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.resizeViewports(true);
	}

}

