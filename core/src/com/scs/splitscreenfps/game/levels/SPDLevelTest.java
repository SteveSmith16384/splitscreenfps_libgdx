package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
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
					Wall wall = new Wall("monstermaze/wall.png", x, z, false);
					game.ecs.addEntity(wall);
				} else {
					/*switch (sewer.map[idx]) {
					case Terrain.WATER: {
						Floor floor = new Floor("shatteredpixeldungeon/water0.png", x, z, 1, 1, false);
						game.ecs.addEntity(floor);
						break;
					}
					case Terrain.CHASM: 
						break;
					case Terrain.EMPTY: {
						Floor floor = new Floor("shatteredpixeldungeon/water3.png", x, z, 1, 1, false);
						game.ecs.addEntity(floor);
						break;
					}
					default:
						Settings.p("Unhandled: " + sewer.map[idx]);
					}
					}*/

					try {
						Image img = tiles.image(x, z);
						Pixmap pixmap = img.texture.bitmap;
						if (pixmap != null) {
							//RectF r = img.frame();
							Pixmap pixmap2 = new Pixmap(16, 16, pixmap.getFormat());
							pixmap2.drawPixmap(pixmap, 0, 0, 0, 0, 16, 16);
							Floor floor = new Floor("test", pixmap2, x, z, 1, 1);
							game.ecs.addEntity(floor);
							Settings.p("Created tex");
						}
					} catch (NullPointerException npe) {

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

}
