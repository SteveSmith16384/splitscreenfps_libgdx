package com.scs.splitscreenfps.game;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.data.MapSquare;

import ssmith.astar.IAStarMapInterface;
import ssmith.lang.NumberFunctions;
import ssmith.libgdx.GridPoint2Static;

public class MapData implements IAStarMapInterface {

	private static final MapSquare BLOCKED_WALL = new MapSquare(true);

	public MapSquare map[][];

	public MapData(int w, int h) {
		map = new MapSquare[w][h];
	}


	public MapSquare getMapSquareAt(int x, int y) {
		try {
			return map[x][y];
		} catch (ArrayIndexOutOfBoundsException ex) {
			return BLOCKED_WALL;
		}
	}


	public boolean rectangleFree(float center_x, float center_z, float width, float depth) {
		//Upper left
		float x = (center_x)-(width/2);// + 0.5f;
		float y = center_z-depth/2;// + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		//Down left
		x = center_x-width/2;// + 0.5f;
		y = center_z+depth/2;// + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		//Upper right
		x = center_x+width/2;// + 0.5f;
		y = center_z-depth/2;// + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		//Down right
		x = center_x+width/2;// + 0.5f;
		y = center_z+depth/2;// + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		return true;
	}


	public boolean rectangleFree_ORIG(float center_x, float center_z, float width, float depth) {
		//Upper left
		float x = (center_x)-(width/2) + 0.5f;
		float y = center_z-depth/2 + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		//Down left
		x = center_x-width/2 + 0.5f;
		y = center_z+depth/2 + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		//Upper right
		x = center_x+width/2 + 0.5f;
		y = center_z-depth/2 + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		//Down right
		x = center_x+width/2 + 0.5f;
		y = center_z+depth/2 + 0.5f;

		if (getMapSquareAt((int)(x), (int)(y)).blocked) {
			return false;
		}

		return true;
	}


	public boolean canSee(Vector3 startPos, Vector3 endPos) {
		Vector3 currentPos = new Vector3();
		currentPos.set(startPos);

		Vector3 dir = new Vector3();
		dir.set(endPos).sub(startPos);
		dir.y = 0;
		dir.nor();

		while(currentPos.dst2(endPos) > (0.5f * 0.5f)){
			currentPos.mulAdd(dir, 0.25f);

			if (getMapSquareAt(currentPos.x, currentPos.z).blocked) {
				return false;
			}
		}
		return true;
	}


	private MapSquare getMapSquareAt(float x, float y) {
		return getMapSquareAt((int)(x), (int)(y));
	}


	public MapSquare getMapSquareAt(Vector3 vec) {
		return getMapSquareAt((int)((vec.x)), (int)((vec.z)));
	}


	public GridPoint2Static getRandomFloorPos() {
		while (true) {
			int x = NumberFunctions.rnd(0,  this.getMapWidth()-1);
			int y = NumberFunctions.rnd(0,  this.getMapHeight()-1);
			if (map[x][y].blocked == false && map[x][y].spawn_point == false) {
				return new GridPoint2Static(x, y);
			}
		}
	}
	

	//--- A Star

	@Override
	public int getMapWidth() {
		return map.length;
	}


	@Override
	public int getMapHeight() {
		return map[0].length;
	}


	@Override
	public boolean isMapSquareTraversable(int x, int z) {
		return this.map[x][z].blocked == false && map[x][z].spawn_point == false;
	}


	@Override
	public float getMapSquareDifficulty(int x, int z) {
		return 1;
	}


}
