package web.astar.pathable;

import com.hexrealm.hexos.accessor.GameObjectAccessor;
import com.hexrealm.hexos.accessor.GameObjectDefinitionAccessor;
import com.hexrealm.hexos.api.GameObjects;
import com.hexrealm.hexos.api.model.WorldTile;
import web.astar.Pathable;
import web.util.Util;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dorkinator on 4/9/2018.
 */
public class Tile extends WorldTile implements Pathable<Tile> {

	private Pathable parent;
	private int gScore = Integer.MAX_VALUE;
	private int fScore = Integer.MAX_VALUE;

	private static Point[] neighborOffsets = {
			new Point(0, 1),
			new Point(-1, 0),
			new Point(1, 0),
			new Point(0, -1),
			new Point(-1, 1),
			new Point(1,1),
			new Point(-1, -1),
			new Point(1, -1)
	};

	private static int[] blockedFlags = {
			Util.W_N,
			Util.W_W,
			Util.W_E,
			Util.W_S,
			Util.W_N | Util.W_W | Util.W_NW,
			Util.W_E | Util.W_N | Util.W_NE,
			Util.W_S | Util.W_W | Util.W_SW,
			Util.W_S | Util.W_E | Util.W_SE
	};

	public Tile(int worldX, int worldY, int plane) {
		super(worldX, worldY, plane);
	}

	public Tile(WorldTile t){
		super(t.getX(), t.getY(), t.getPlane());
	}

	@Override
	public Pathable getParent() {
		return parent;
	}

	@Override
	public void setParent(Tile parent) {
		this.parent = parent;
	}

	@Override
	public int getGScore() {
		return this.gScore;
	}

	@Override
	public int getFScrore() {
		return this.fScore;
	}

	@Override
	public void setGScore(int score) {
		this.gScore = score;
	}

	@Override
	public void setFScore(int score) {
		this.fScore = score;
	}

	@Override
	public int distance(Tile p) {
		if(Math.abs(p.getX() - getX()) + Math.abs(p.getY() - getY()) == 2){
			return 11;
		}
		return 10;
	}


	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public Set<Tile> getValidNeighbors() {
		Set<Tile> out = new HashSet<>();
		for(int i = 0; i < neighborOffsets.length; i++) {
			WorldTile neighbor = this.derive(neighborOffsets[i].x, neighborOffsets[i].y);
			//if ((Web.getWebRegionBase(neighbor).getDistance(Web.getWebRegionBase(this)) == 0)){//used for region building
				if (neighbor.isLoaded() && canReach(neighbor, i)) {
					out.add(new Tile(neighbor));
				}
			//}
		}
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Tile){
			Tile t = (Tile) obj;
			return t.getX() == getX() && t.getY() == getY() && t.getPlane() == getPlane();
		}
		return false;
	}

	public static boolean canReach(WorldTile t1, WorldTile t2){
		if((Util.getWalkingFlags(t1) & Util.WALKABLE) == 0 && (Util.getWalkingFlags(t2) & Util.WALKABLE) == 0) {
			Point p = new Point(t2.getX() - t1.getX(), t2.getY() - t1.getY());
			int i = getI(p);
			if (Math.abs(p.x) + Math.abs(p.y) == 2) {
				if ((Util.getWalkingFlags(t1) & (Util.WALKABLE | blockedFlags[i])) == 0) {
					if((Util.getWalkingFlags(t2) & (Util.W_NSEW & ~blockedFlags[i])) == 0) {
						return true;
					}
				}
			} else {
				return (Util.getWalkingFlags(t1) & (Util.WALKABLE | blockedFlags[i])) == 0;
			}
		}
		return false;
	}

	private boolean canReach(WorldTile t, int i){
		if((Util.getWalkingFlags(t) & Util.WALKABLE) == 0) {
			if (Math.abs(neighborOffsets[i].x) + Math.abs(neighborOffsets[i].y) == 2) {
				if ((Util.getWalkingFlags(this) & (Util.WALKABLE | blockedFlags[i])) == 0) {
					if((Util.getWalkingFlags(t) & (Util.W_NSEW & ~blockedFlags[i])) == 0) {
						return true;
					}
				}
			} else {
				if((Util.getWalkingFlags(this) & (Util.WALKABLE | blockedFlags[i])) == 0){
					return true;
				}else{
					Collection<GameObjectAccessor> tmp;
					if((tmp = GameObjects.at(this.getRegionX(), this.getRegionY())).size() > 0){
						for(GameObjectAccessor i2:tmp){
							try {
								if (objectPassable(i2.getDefinition())) {
									return true;
								}
							}catch (Exception e){}
						}
					}
				}
			}
		}
		return false;
	}

	private static final HashSet<String> doorNames = new HashSet<String>(){{
		add("Door");
		add("Dork");
		add("Large door");
		add("Prison door");
		add("Gate");
		add("Curtain");
	}};

	private static boolean objectPassable(GameObjectDefinitionAccessor i){
		if(doorNames.contains(i.getName())) {
			return true;
		}
		return false;
	}

	private static int getI(Point p){
		for(int i = 0; i < neighborOffsets.length; i++){
			if(p.x == neighborOffsets[i].x && p.y == neighborOffsets[i].y) {
				return i;
			}
		}
		return -1;
	}
}
