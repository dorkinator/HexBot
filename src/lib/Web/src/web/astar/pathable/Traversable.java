package web.astar.pathable;

import com.hexrealm.hexos.Environment;
import com.hexrealm.hexos.api.Chrono;
import com.hexrealm.hexos.api.Players;
import com.hexrealm.hexos.api.Walking;
import com.hexrealm.hexos.api.model.WorldTile;
import random.Random;
import script.AbstractScript;
import web.Web;
import web.astar.AStar;
import web.node.WebNode;
import web.node.WebRegion;
import web.util.Util;

import java.util.Set;

/**
 * Created by Dorkinator on 4/14/2018.
 */
public class Traversable {
	private boolean spreadTile = true;
	private	Path<WebNode> webNodePath;
	private int maxWalkDist = 18;
	private WorldTile start;
	private WorldTile end;

	public Traversable(WorldTile start, WorldTile end){
		this.start = start;
		this.end = end;
		WebRegion startRegion = Web.getWebRegion(start);
		WebRegion endRegion = Web.getWebRegion(end);
		if(startRegion == null || endRegion == null){
			throw new IllegalStateException("Missing region data");
		}else if(startRegion.getClosest(start) == null || endRegion.getClosest(end) == null){
			throw new IllegalStateException("null closest region tiles: "+Environment.getClient().getState());
		}
		webNodePath = AStar.findPath(startRegion.getClosest(start), endRegion.getClosest(end));
		if(webNodePath == null){
			throw new IllegalStateException("couldn't form path");
		}
	}

	public void traverse(){
		walkTo(getWalkToTile(Players.local().getWorldTile()));
	}

	public void traverse(WorldTile start){
		walkTo(getWalkToTile(start));
	}

	public WorldTile getWalkToTile(WorldTile start){
		Path<Tile> longPath = getLongestPath(start, end, webNodePath);
		if(longPath == null){
			if(Environment.getClient().getState() == 25){
				Chrono.sleep(100);
				System.out.println("Loading...");
				return getWalkToTile(start);
			}else {
				throw new IllegalStateException("Failed to traverse: " + Environment.getClient().getState());
			}
		}
		int maxWalkDist = Math.min(longPath.getPath().size()-1, this.maxWalkDist);
		int lowerBound = Math.max(0, maxWalkDist-5);
		int randomIndex = Random.nextInt(lowerBound, maxWalkDist);
		Tile randomTile = longPath.getPath().get(randomIndex);
		if(spreadTile) {
			Set<Tile> neighbors = randomTile.getValidNeighbors();
			neighbors.add(randomTile);
			return (Tile)neighbors.toArray()[Random.nextInt(neighbors.size()-1)];
		}else{
			return randomTile;
		}
	}


	public static Path<Tile> getLongestPath(WorldTile start, WorldTile end,  Path<WebNode> webNodePath) {
		int i;
		for(i = 0; i < Math.min(webNodePath.getPath().size(), 3); i++){
			if(!webNodePath.getPath().get(i).getTile().isLoaded()) {
				break;
			}
		}
		Path<Tile> path = null;
		for(i--;i > 2; i--){
			path = AStar.findPath(new Tile(start), new Tile(webNodePath.getPath().get(i).getTile()));
			if(path != null){
				return path;
			}
		}
		if(webNodePath.getPath().size() > i+1) {
			System.out.println("region edge path");
			path = getRegionEdgePath(webNodePath.getPath().get(i).getTile(), webNodePath.getPath().get(i + 1).getTile());
		}else{
			path = AStar.findPath(new Tile(start), new Tile(end));
		}
		return path;
	}

	public Path<WebNode> getWebNodePath() {
		return webNodePath;
	}

	public void walkTo(WorldTile tile){
		if(tile.getDistance(Players.local()) > 8){
			AbstractScript.queueMouseMove(Environment.getClient().getCanvas().getWidth() - Random.nextInt(217), Environment.getClient().getCanvas().getHeight() - Random.nextInt(169));
		}else{
			AbstractScript.queueMouseMove(Random.nextInt(Environment.getClient().getCanvas().getWidth()), Random.nextInt(Environment.getClient().getCanvas().getHeight()));
		}
		Walking.walkTo(tile.getRegionTile());
		if(Random.nextDouble()> 0.8) {
			Walking.walkTo(tile.getRegionTile());
		}
	}

	private static Path<Tile> getRegionEdgePath(WorldTile start, WorldTile end){
		Tile farthestLoadedTile = getFarthestLoadedTile(start, end);
		Path<Tile> out = null;
		while(!start.equals(end) && out == null){
			out = AStar.findPath(new Tile(start), new Tile(farthestLoadedTile));
			if(out == null || out.getPathCost() > 500){
				out = null;
				farthestLoadedTile = getFarthestLoadedTile(start, farthestLoadedTile);
			}
		}
		return out;
	}


	public static Tile getFarthestLoadedTile(WorldTile start, WorldTile end){
		int w = end.getX() - start.getX() ;
		int h = end.getY() - start.getY() ;
		double dist = start.getDistance(end);
		for(int i = (int)dist-1; i  > 0; i--){
			int dx = (int)((h/dist) * -i);
			int dy = (int)((w/dist) * -i);
			Tile tmp = new Tile(start.derive(dx, dy));
			if (tmp.isLoaded() && (Util.getWalkingFlags(tmp) & Util.WALKABLE) == 0) {
				return tmp;
			}
		}
		return new Tile(start);
	}
}
