package com.scs.splitscreenfps.mapgen;

import java.awt.Point;
import java.util.ArrayList;

import ssmith.astar.IAStarMapInterface;
import ssmith.lang.NumberFunctions;

public class AbstractDungeon implements IAStarMapInterface {

	public SqType map[][];
	protected ArrayList<Point> doors = new ArrayList<Point>();

	public enum SqType {NOTHING, WALL, FLOOR, DOOR_EW, DOOR_NS, COMPUTER;}

	// This is called by the server to create the map
	public AbstractDungeon(int MAX) { 
		map = new SqType[MAX][MAX];
		this.clearMap();
	}


	protected void clearMap() {
		for (byte y=0 ; y<this.getMapHeight() ; y++) {
			for (byte x=0 ; x<this.getMapWidth() ; x++) {
				map[x][y] = SqType.NOTHING;
			}
		}
	}


	public SqType getSq(int x, int z) {
		//try {
		return map[x][z];
		/*} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
			DSRWebServer.p("Error: sq " + x + "," + z + " not found.");
			ex.printStackTrace();
			return null;
		}*/
	}


	protected void createRoomByCentre(int centre_x, int centre_y, int l, int d, short type, byte deploy_side) {
		this.createRoomByTopLeft((centre_x - (l/2)), (centre_y - (d/2)), l, d);
	}


	protected void createRoomByTopLeft(int x, int y, int l, int d) {
		for (int y2=y ; y2<=y+d ; y2++) {
			for (int x2=x ; x2<=x+l ; x2++) {
				map[x2][y2] = SqType.FLOOR;
			}
		}

	}


	protected boolean isThereARoomAt(int x, int y, int l, int d) {
		for (int y2=y-1 ; y2<=y+d+1 ; y2++) {
			for (int x2=x-1 ; x2<=x+l+1 ; x2++) {
				if (map[x2][y2] == SqType.FLOOR) {
					return true;
				}
			}
		}
		return false;
	}


	protected void addCorridorAndDoors(int sx, int sy, int ex, int ey, boolean extra_door) {
		int difx = NumberFunctions.sign(ex-sx);
		int dify = NumberFunctions.sign(ey-sy);

		// Across
		if (difx != 0) {
			boolean door_added = false;
			int x = sx;
			int y = sy;
			int len = Math.max(sx, ex) - Math.min(sx, ex);
			for (int c=0 ; c<len ; c++) {
				x += difx; 
				if (map[x][y] != SqType.FLOOR) {
					map[x][y] = SqType.FLOOR;
					if (!door_added) {
						if ((map[x][y+1] == SqType.NOTHING && map[x][y-1] == SqType.NOTHING) || (map[x][y+1] == SqType.WALL && map[x][y-1] == SqType.WALL)) {
							doors.add(new Point(x, y));
							door_added = true; 
						}
					}
				}
			}
		}
		// Down
		if (dify != 0) {
			boolean door_added = false;
			int x = ex;
			int y = sy;
			int len = Math.max(sy, ey) - Math.min(sy, ey);
			for (int c=0 ; c<len ; c++) {
				y += dify; 
				if (map[x][y] != SqType.FLOOR) {
					map[x][y] = SqType.FLOOR;
					if (!door_added) {
						if ((map[x+1][y] == SqType.NOTHING && map[x-1][y] == SqType.NOTHING) || (map[x+1][y] == SqType.WALL && map[x-1][y] == SqType.WALL)) {
							doors.add(new Point(x, y));
							door_added = true; 
						}
					}
				}
			}
		}

	}


	protected void addWalls() {
		for (byte y=1 ; y<this.getMapHeight()-1 ; y++) {
			for (byte x=1 ; x<this.getMapWidth()-1 ; x++) {
				if (map[x][y] == SqType.NOTHING) {
					if (map[x+1][y] == SqType.FLOOR || map[x-1][y] == SqType.FLOOR || map[x][y-1] == SqType.FLOOR || map[x][y+1] == SqType.FLOOR ||
							map[x+1][y+1] == SqType.FLOOR || map[x+1][y-1] == SqType.FLOOR || map[x-1][y-1] == SqType.FLOOR || map[x-1][y+1] == SqType.FLOOR) {
						map[x][y] = SqType.WALL;
					}					
				}
			}
		}

	}


	protected void addComputers(int amt) {
		while (amt > 0) {
			int x = NumberFunctions.rnd(2, this.getMapHeight()-3);
			int y = NumberFunctions.rnd(2, this.getMapWidth()-3);
			if (map[x][y] == SqType.FLOOR) {
				if (map[x+1][y] == SqType.FLOOR || map[x-1][y] == SqType.FLOOR || map[x][y-1] == SqType.FLOOR || map[x][y+1] == SqType.FLOOR ||
						map[x+1][y+1] == SqType.FLOOR || map[x+1][y-1] == SqType.FLOOR || map[x-1][y-1] == SqType.FLOOR || map[x-1][y+1] == SqType.FLOOR) {
					map[x][y] = SqType.COMPUTER;
					amt--;
				}					
			}
		}

	}


	public void showMap() {
		try {
			int w = map[0].length;
			int h = map.length;
			System.out.println("---------------------------------------------------");
			for(int z=0 ; z< h ; z++) {
				for(int x=0 ; x<w ; x++) {
					String s = " ";
					if (map[x][z] == SqType.DOOR_EW || map[x][z] == SqType.DOOR_NS) {
						s = "D";
					} else if (map[x][z] == SqType.WALL) {
						s = "W";
					} else if (map[x][z] == SqType.FLOOR) {
						s = ".";
					} else if (map[x][z] == SqType.COMPUTER) {
						s = "C";
					} else {
						s = ".";
					}
					System.out.print(s);
				}
				System.out.println("");
			}
			System.out.println("---------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	protected void addDoors() {
		for (int i=0 ; i<doors.size() ; i++) {
			Point p = doors.get(i);
			if (map[p.x+1][p.y] == SqType.NOTHING && map[p.x-1][p.y] == SqType.NOTHING) {
				map[p.x][p.y] = SqType.DOOR_EW;
			} else if (map[p.x][p.y+1] == SqType.NOTHING && map[p.x][p.y-1] == SqType.NOTHING) {
				map[p.x][p.y] = SqType.DOOR_NS;
			} else if (map[p.x+1][p.y] == SqType.WALL && map[p.x-1][p.y] == SqType.WALL) {
				map[p.x][p.y] = SqType.DOOR_EW;
			} else if (map[p.x][p.y+1] == SqType.WALL && map[p.x][p.y-1] == SqType.WALL) {
				map[p.x][p.y] = SqType.DOOR_NS;
			}
		}
	}

	//	A*

	public int getMapWidth() {
		return map.length;
	}

	public int getMapHeight() {
		return map[0].length;
	}

	public float getMapSquareDifficulty(int x, int z) {
		return 0;
	}

	public boolean isMapSquareTraversable(int x, int z) {
		SqType sq = this.getSq(x, z); 
		return sq == SqType.FLOOR;
	}

}
