package com.scs.splitscreenfps.mapgen;

import java.util.LinkedList;
import java.util.Random;

import ssmith.libgdx.GridPoint2Static;

public class MazeGen1 {

	public static final boolean WALL = false;
	public static final boolean PASSAGE = !WALL;

	private static final char PASSAGE_CHAR = '.';
	private static final char WALL_CHAR = 'W';

	public boolean map[][]; // wall == false!
	private int width;
	private int height;
	public GridPoint2Static start_pos; 
	public GridPoint2Static middle_pos; 
	public GridPoint2Static end_pos; 

	public static void main(String args[]) {
		//System.out.println(new Maze(21, 21));
		new MazeGen1(16, 16, 1);
	}


	public MazeGen1(final int _width, final int _height, int numWallsToRemove) {
		this.width = _width;
		this.height = _height;
		this.map = new boolean[width][height];

		final Random random = new Random();
		{
			final LinkedList<int[]> frontiers = new LinkedList<int[]>();
			int x = 1+random.nextInt(width-2);
			int y = 1+random.nextInt(height-2);
			frontiers.add(new int[]{x,y,x,y});

			while ( !frontiers.isEmpty()) {
				final int[] f = frontiers.remove(random.nextInt(frontiers.size()));
				x = f[2];
				y = f[3];
				if (map[x][y] == WALL) {
					map[f[0]][f[1]] = map[x][y] = PASSAGE;
					if ( x >= 3 && map[x-2][y] == WALL )
						frontiers.add( new int[]{x-1,y,x-2,y} );
					if ( y >= 3 && map[x][y-2] == WALL )
						frontiers.add( new int[]{x,y-1,x,y-2} );
					if ( x < width-3 && map[x+2][y] == WALL )
						frontiers.add( new int[]{x+1,y,x+2,y} );
					if ( y < height-3 && map[x][y+2] == WALL )
						frontiers.add( new int[]{x,y+1,x,y+2} );
				}
			}
		}

		{
			// Get start pos
			for (int z=0 ; z<height ; z++) {
				if (this.start_pos != null) {
					break;
				}
				for (int x=0 ; x<width ; x++) {
					if (this.start_pos == null && map[x][z] != WALL) {
						start_pos = new GridPoint2Static(x, z);
						break;
					}
				}
			}

			// Get middle pos
			for (int z=height/2 ; z<height ; z++) {
				if (this.middle_pos != null) {
					break;
				}
				for (int x=width/2 ; x<width ; x++) {
					if (this.middle_pos == null && map[x][z] != WALL) {
						middle_pos = new GridPoint2Static(x, z);
						break;
					}
				}
			}

			// Get end pos
			for (int z=height-1 ; z>=0 ; z--) {
				if (this.end_pos != null) {
					break;
				}
				for (int x=width-1 ; x>=0; x--) {
					if (this.end_pos == null && map[x][z] != WALL) {
						end_pos = new GridPoint2Static(x, z);
						break;
					}
				}
			}
		}

		// Remove walls AFTER we've created start positions, in case we create a single island area that the player is trapped in!
		while (numWallsToRemove > 0) {
			int mx = 1+random.nextInt(width-2);
			int my = 1+random.nextInt(height-2);
			if (map[mx][my] == WALL) {
				// Todo - check adjacent squaers are empty to avoid lone squares (which cause problems when selecting a dest for A*)
				map[mx][my] = PASSAGE;
				numWallsToRemove--;
			}
		}

		System.out.println(this.toString());
	}

	
	@Override
	public String toString() {
		final StringBuffer b = new StringBuffer();
		for ( int y = height-1; y >= 0; y-- ){ // Draw top-up
			for ( int x = 0; x < width; x++ ) {
				b.append( map[x][y] == WALL ? WALL_CHAR : PASSAGE_CHAR );
			}
			b.append('\n');
		}
		b.append('\n');
		return b.toString();
	}


}
