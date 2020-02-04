package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Ceiling;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTerrainTilemap;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTileSheet;
import com.watabou.noosa.Image;
import com.watabou.utils.RectF;

public class SPDLevelTest extends AbstractLevel {

	private Game game;

	public SPDLevelTest(Game _game) {
		game = _game;
	}

	@Override
	public void load() {
		Dungeon.init();
		Dungeon.depth = 1;

		Level sewer = new SewerLevel();
		sewer.create();

		Dungeon.level = sewer;
		DungeonTileSheet.setupVariance(Dungeon.level.map.length, Dungeon.seedCurDepth());
		DungeonTerrainTilemap tiles = new DungeonTerrainTilemap();

		game.mapData = new MapData(sewer.width(), sewer.height());

		// See Terrain.java
		StringBuilder mapStr = new StringBuilder();
		int idx = 0;
		for (int z=0 ; z<sewer.height() ; z++) {
			for (int x=0 ; x<sewer.width() ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = sewer.map[idx] == 4;
				//game.mapData.map[x][z].blocked = sewer.passable[idx];//map[idx] == 4;
				mapStr.append(sewer.map[idx] + ",");
				if (game.mapData.map[x][z].blocked) {
					Pixmap pixmap2 = this.getPixmap(tiles, x, z, idx);
					if (pixmap2 != null) {
						Wall wall = new Wall(pixmap2, x, z, false);
						game.ecs.addEntity(wall);
					} else if (sewer.map[idx] != 4) { // Ignore
						Wall wall = new Wall("monstermaze/wall.png", x, z, false);
						game.ecs.addEntity(wall);
					}
				} else {
					switch (sewer.map[idx]) {
					case Terrain.WATER: {
						Floor floor = new Floor("shatteredpixeldungeon/water0.png", x, z, 1, 1, false);
						game.ecs.addEntity(floor);
						break;
					}
					/*case Terrain.CHASM: 
						break;
					case Terrain.EMPTY: {
						Floor floor = new Floor("shatteredpixeldungeon/water3.png", x, z, 1, 1, false);
						game.ecs.addEntity(floor);
						break;
					}*/
					default:
						//Settings.p("Unhandled: " + sewer.map[idx]);

						Pixmap pixmap2 = this.getPixmap(tiles, x, z, idx);
						if (pixmap2 != null) {
							Floor floor = new Floor("test", pixmap2, x, z, 1, 1);
							game.ecs.addEntity(floor);
							Ceiling ceiling = new Ceiling(pixmap2, x, z, 1, 1, 1f);
							game.ecs.addEntity(ceiling);
						} else {
							Floor floor = new Floor("heart.png", "heart.png", x, z, 1, 1);
							game.ecs.addEntity(floor);
						}
						break;
					}
				}

				// Player start positions
				if (sewer.map[idx] == Terrain.ENTRANCE) {
					for (int i=0 ; i<this.startPositions.length ;i++) {
						this.startPositions[i] = new GridPoint2(x, z);
					}
				}

				idx++;
			}
			mapStr.append("\n");
		}

		for (Mob mob : sewer.mobs) {
			int x = mob.pos / sewer.width();
			int y = mob.pos % sewer.width();
			int map = sewer.map[mob.pos];
			// todo - create mob
		}

		//System.out.println(mapStr.toString());
	}


	private Pixmap getPixmap(DungeonTerrainTilemap tiles, int x, int z, int idx) {
		try {
			//Image img = tiles.image(x, z);
			Image img = tiles.tile(idx, Dungeon.level.map[idx]);
			if (img != null && img.texture != null && img.texture.bitmap != null) {
				Pixmap pixmap = img.texture.bitmap;
				if (pixmap != null) {
					RectF r = img.frame();
					Pixmap pixmap2 = new Pixmap(16, 16, pixmap.getFormat());
					int x1 = (int)(r.left * pixmap.getWidth());
					int y1 = (int)(r.top * pixmap.getHeight());
					int w1 = (int)(r.width() * pixmap.getWidth());
					int h1 = (int)(r.height() * pixmap.getHeight());

					pixmap2.drawPixmap(pixmap, 0, 0, x1, y1, w1, h1);
					return pixmap2;
				}
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
		return null;
	}

}
