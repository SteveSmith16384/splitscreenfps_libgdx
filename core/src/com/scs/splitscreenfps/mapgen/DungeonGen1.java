package com.scs.splitscreenfps.mapgen;

import java.util.ArrayList;

import com.badlogic.gdx.math.GridPoint2;

import ssmith.lang.NumberFunctions;

public class DungeonGen1 extends AbstractDungeon {

	public ArrayList<GridPoint2> centres = new ArrayList<GridPoint2>();

	private int MAX_ROOM_SIZE;
	
	public static void main(String args[])  {
		new DungeonGen1(30, 10, 5, 1);
	}

	public DungeonGen1(int size, int rooms, int _MAX_ROOM_SIZE, int cpus) {
		super(size);

		MAX_ROOM_SIZE = _MAX_ROOM_SIZE;
		
		int attempts = 0;
		while (true) {
			//Interval int_check = new Interval(10 * 1000, false); // Give us time to create the map
			attempts++;
			if (attempts > 100) {
				throw new RuntimeException("Map generation timed out.");
			}

			super.clearMap();
			centres.clear();

			for (int r=1 ; r<=rooms ; r++) {
				int x = NumberFunctions.rnd(2, size-MAX_ROOM_SIZE-3);
				int y = NumberFunctions.rnd(2, size-MAX_ROOM_SIZE-3);
				int w = NumberFunctions.rnd(2, MAX_ROOM_SIZE);
				int h = NumberFunctions.rnd(2, MAX_ROOM_SIZE);

				// Loop until we find an empty area
				while (super.isThereARoomAt(x, y, w, h) == true) {
					x = NumberFunctions.rnd(2, size-MAX_ROOM_SIZE-3);
					y = NumberFunctions.rnd(2, size-MAX_ROOM_SIZE-3);
					w = NumberFunctions.rnd(2, MAX_ROOM_SIZE);
					h = NumberFunctions.rnd(2, MAX_ROOM_SIZE);

					// Check if we've run out of time
					/*if (int_check.hitInterval()) {
						this.showMap();
						continue restart;
					}*/
				}
				this.createRoomByTopLeft(x, y, w, h);
				// Store the rooms for checking later
				centres.add(new GridPoint2(x+(w/2), y+(h/2)));
			}

			// Connect rooms
			doors.clear();
			for (int i=0 ; i<centres.size() ; i++) {
				GridPoint2 start = centres.get(i);
				GridPoint2 end = centres.get(NumberFunctions.rnd(0, centres.size()-1));
				while (start == end) {
					end = centres.get(NumberFunctions.rnd(0, centres.size()-1));
				}
				//super.showMap();
				this.addCorridorAndDoors(start.x, start.y, end.x, end.y, false);
				//super.showMap();
			}

			// Check deployment rooms are connected
			boolean success = true;
			/*for (int s=0 ; s<num_players ; s++) {
				if (areRoomsConnected(centres, s, s+1) == false) {
					super.showMap();
					success = false;
					break;
				}
			}*/

			if (success) {
				addDoors();
				addComputers(cpus);
				addWalls();
				//super.showMap();
				break; // out of loop
			}
			//DSRWebServer.p("Recreating map.");

		}

	}
/*

	private Point getRoomLoc(int side, int size) {
		switch (side) {
		case 1:
			return new Point(1, 1);
		case 2:
			return new Point(size-MAX_ROOM_SIZE-2, size-MAX_ROOM_SIZE-2);
		case 3:
			return new Point(1, size-MAX_ROOM_SIZE-2);
		case 4:
			return new Point(size-MAX_ROOM_SIZE-2, 1);
		default:
			throw new RuntimeException("Unknown side: " + side);
		}
	}


	private boolean areRoomsConnected(ArrayList<Point> centres, int s, int e) {
		AStar astar = new AStar(this);
		Point start = centres.get(s);
		Point end = centres.get(e);
		astar.findPath(start.x, start.y, end.x, end.y, false);
		if (astar.wasSuccessful()) {
			return true;
		} else {
			return false;
		}

	}
*/
	/*
	private void addComputers(int cpus) {
		for (int i=0 ; i<cpus ; i++) {
			int x, y;
			while (true) {
				x = Functions.rnd(1, super.getMapWidth()-2);
				y = Functions.rnd(1, super.getMapHeight()-2);
				if (super.map[x][y].major_type == MapDataTable.MT_FLOOR) {
					// Check its next to a wall and not near a corridor
					ServerMapSquare sms[] = new ServerMapSquare[4];
					sms[0] = super.map[x-1][y];
					sms[1] = super.map[x+1][y];
					sms[2] = super.map[x][y-1];
					sms[3] = super.map[x][y+1];
					int wall_count = 0;
					for (int s=0 ; s<sms.length ; s++) {
						if (sms[s].major_type == MapDataTable.MT_FLOOR) {
							wall_count++;
						}
					}
					if (wall_count == 3) {
						break;
					}
				}
			}
			super.map[x][y].major_type = MapDataTable.MT_COMPUTER;
			super.map[x][y].deploy_sq_side = 0;
		}
	}
	 */

}
