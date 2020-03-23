package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.tag.TagableComponent;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Ceiling;
import com.scs.splitscreenfps.game.entities.GenericSquare;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.alientag.AlienTagEntityFactory;
import com.scs.splitscreenfps.game.systems.tag.TagSystem;
import com.scs.splitscreenfps.mapgen.MazeGen1;

import ssmith.lang.NumberFunctions;

public class AlienTagLevel extends AbstractLevel {

	private Texture slime;

	public AlienTagLevel(Game _game) {
		super(_game);

		slime = new Texture(Gdx.files.internal("tag/slime.jpg"));
	}


	@Override
	public void load() {
		loadMapFromMazegen(game);
	}


	@Override
	public void setupAvatars(AbstractEntity player, int playerIdx) {
		TagableComponent taggable = new TagableComponent(player, playerIdx);
		player.addComponent(taggable);
		addSkeletonForTagged(playerIdx, taggable);
	}


	private ModelInstance addSkeletonForTagged(int idx, TagableComponent taggable) {
		AssetManager am = game.assetManager;

		am.load("tag/models/Skeleton.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("tag/models/Skeleton.g3dj");

		ModelInstance instance = new ModelInstance(model);
		instance.transform.scl(.0015f);
		instance.transform.rotate(Vector3.Y, 90f); // Model is facing the wrong way
		HasModelComponent hasModel = new HasModelComponent("Skeleton", instance, -.3f, 90, 0.0016f);
		hasModel.dontDrawInViewId = idx;
		taggable.storedHasModel = hasModel;

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "SkeletonArmature|Skeleton_Running", "SkeletonArmature|Skeleton_Idle");
		anim.animationController = animation;
		taggable.storedAnimated = anim;

		return instance;
	}

	/*
	private ModelInstance addSkeletonComponents(int idx) {
		AssetManager am = game.assetManager;

		am.load("models/Skeleton.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/Skeleton.g3dj");

		ModelInstance instance = new ModelInstance(model);
		instance.transform.scl(.0013f);
		instance.transform.rotate(Vector3.Y, 90f); // Model is facing the wrong way
		HasModel hasModel = new HasModel(instance);
		hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);

		AnimatedForAvatarComponent avatarAnim = new AnimatedForAvatarComponent();
		avatarAnim.idle_anim = "SkeletonArmature|Skeleton_Idle";
		avatarAnim.walk_anim = "SkeletonArmature|Skeleton_Running";
		this.addComponent(avatarAnim);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, avatarAnim.idle_anim);
		anim.animationController = animation;
		this.addComponent(anim);

		return instance;
	}
	 */

	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 13 + game.players.length;
		if (Settings.SMALL_MAP) {
			this.map_width = 9;
		}
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		MazeGen1 maze = new MazeGen1(map_width, map_height, map_width/2);

		for (int z=-1 ; z<=map_height ; z++) {
			for (int x=-1 ; x<=map_width ; x++) {
				if (x == -1 || z == -1 || x == map_width || z == map_height) {
					Wall wall = new Wall(game.ecs, "tag/textures/spacewall2.png", x, 0, z, false);
					game.ecs.addEntity(wall);

					// underground floor
					Wall floor = new Wall(game.ecs, "tag/textures/floor3.jpg", x, -1, z, false);
					game.ecs.addEntity(floor);

					continue;
				}

				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = maze.map[x][z] == MazeGen1.WALL;
				if (game.mapData.map[x][z].blocked) {
					if (NumberFunctions.rnd(1,  3) == 1) {
						Ceiling ceiling = new Ceiling(game.ecs, "tag/textures/corridor.jpg", x, z, 1, 1, false, 1f);
						game.ecs.addEntity(ceiling);
						// Don't draw floor!
					} else {
						Wall wall = new Wall(game.ecs, "tag/textures/spacewall2.png", x, 0, z, false);
						game.ecs.addEntity(wall);

						// underground floor
						Wall floor = new Wall(game.ecs, "tag/textures/floor3.jpg", x, -1, z, false);
						game.ecs.addEntity(floor);
					}
				} else {
					//Floor floor = new Floor(game.ecs, "", "tag/textures/floor3.jpg", x, z, 1f, 1f);
					//game.ecs.addEntity(floor);

					// underground floor
					Wall floor = new Wall(game.ecs, "tag/textures/floor3.jpg", x, -1, z, false);
					game.ecs.addEntity(floor);

					int rnd = NumberFunctions.rnd(1, 5);
					if (rnd == 1) {
						GenericSquare sq = new GenericSquare(game.ecs, x, z, "tag/textures/damaged_floor2.png");
						game.ecs.addEntity(sq);
					} else if (rnd == 2) {
						//AbstractEntity door = game.entityFactory.createDoor(x, z, false);
						//game.ecs.addEntity(door);
					} else if (rnd == 3) {
						/*float offX = NumberFunctions.rndFloat(.2f, 0.4f);
						float offZ = NumberFunctions.rndFloat(.2f, 0.4f);
						AbstractEntity crate = AlienTagEntityFactory.createCrate(game.ecs, x+offX, z+offZ);
						if (game.isAreaEmpty(crate)) {
							game.ecs.addEntity(crate);
						}*/
					}

					Ceiling ceiling = new Ceiling(game.ecs, "tag/textures/corridor.jpg", x, z, 1, 1, false, 1f);
					game.ecs.addEntity(ceiling);
				}

			}
		}

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = game.mapData.getRandomFloorPos();
		}
	}


	@Override
	public void addSystems(BasicECS ecs) {
		TagSystem tagSystem = new TagSystem(ecs, game);
		ecs.addSystem(tagSystem);
	}


	@Override
	public void update() {
		game.ecs.processSystem(TagSystem.class);
	}


	@Override
	public void renderUI(SpriteBatch batch2d, int viewIndex) {
		// Draw slime
		TagSystem tagSystem = (TagSystem)game.ecs.getSystem(TagSystem.class);
		if (tagSystem != null) {
			if (tagSystem.currentIt.entityId == game.players[game.currentViewId].entityId) {
				if (tagSystem.lastTagTime + TagSystem.TAG_INTERVAL > System.currentTimeMillis()) {
					batch2d.setColor(1, 1, 1, 1);
				} else {
					batch2d.setColor(1, 1, 1, 0.3f);
				}
				batch2d.draw(slime, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batch2d.setColor(1, 1, 1, 1f);
			}
		}

		AbstractEntity playerAvatar = game.players[viewIndex];
		TagableComponent tc = (TagableComponent)playerAvatar.getComponent(TagableComponent.class);
		if (tc != null) {
			if (tc.timeLeftAsIt < 20) {
				game.font_med.setColor(1, 0, 0, 1);
			} else {
				game.font_med.setColor(0, 0, 0, 1);
			}
			game.font_med.draw(batch2d, "Time Left: " + (int)tc.timeLeftAsIt, 10, game.font_med.getLineHeight());
		}
	}

}
