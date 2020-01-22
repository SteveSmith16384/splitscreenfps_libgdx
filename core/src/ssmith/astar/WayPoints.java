package ssmith.astar;

import java.util.ArrayList;

import com.badlogic.gdx.math.GridPoint2;

import ssmith.lang.GeometryFuncs;
import ssmith.lang.NumberFunctions;

/**
 * @author stephen smith
 *
 */
public class WayPoints extends ArrayList<GridPoint2> {
	
	private static final long serialVersionUID = 1L;

	public WayPoints() {
		super();
	}
	
	public void insertRoute(int pos, WayPoints w) {
		this.addAll(pos, w);
	}
	
	public void truncate(int amt) {
	    while (this.size() > amt) {
	        this.remove(this.size()-1);
	    }
	}
	
	public void remove(int x, int y) {
		for (int i=0 ; i<this.size() ; i++) {
			GridPoint2 p = get(i);
			if (p.x == x && p.y == y) {
				this.remove(p);
			}
		}
	}

	public boolean contains(int x, int y) {
		for (int i=0 ; i<this.size() ; i++) {
			GridPoint2 p = get(i);
			if (p.x == x && p.y == y) {
				return true;
			}
		}
		return false;
	}

	public GridPoint2 getClosestPoint(int x, int y) {
		double closest_dist = Double.MAX_VALUE;
		int closest_point = -1;
		for(int p=0 ; p<this.size() ; p++) {
			GridPoint2 pnt = this.get(p);
			double dist = GeometryFuncs.distance(x, y, pnt.x, pnt.y);
			if (dist < closest_dist) {
				closest_dist = dist;
				closest_point = p;
			}
		}
		return this.get(closest_point);
	}

	public GridPoint2 getNextPoint() {
		if (this.hasAnotherPoint()) {
			return get(0);
		} else {
			return null;
		}
	}
	
	public GridPoint2 getLastPoint() {
		return (GridPoint2)get(size()-1);
	}
	
	public void removeCurrentPoint() {
		remove(0);
	}
	
	public boolean hasAnotherPoint() {
		return this.size() > 0;
	}
	
	public GridPoint2 getRandomPoint() {
		return this.getRandomPoint(false);
	}
	
	public GridPoint2 getRandomPoint(boolean remove) {
		if (this.hasAnotherPoint()) {
			int no = NumberFunctions.rnd(0, size()-1);
			GridPoint2 p = get(no); 
			if (remove) {
				this.remove(no);
			}
			return p;
		}
		return null;
	}
	
	public void add(int x, int y) {
		add(new GridPoint2(x, y));
	}
	

	public void clear() {
		removeAll(this);
	}
	
	public WayPoints copy() {
		WayPoints w = new WayPoints();
		for (int i=0 ; i<this.size() ; i++) {
			GridPoint2 p = get(i);
			w.add(p);
		}
		return w;
	}
	
}
