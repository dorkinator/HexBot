package web.node;

import com.hexrealm.hexos.api.Perspective;
import com.hexrealm.hexos.api.model.WorldTile;
import web.astar.AStar;
import web.astar.pathable.Path;
import web.astar.pathable.Tile;
import web.util.EDGE;
import web.util.Util;

import java.awt.*;
import java.util.Map;

/**
 * Created by Dorkinator on 4/18/2018.
 */
public class WebRegion {
	private WorldTile regionBase;
	private Map<WorldTile, WebNode> regionEntrances;

	public WebRegion(WorldTile regionBase, Map<WorldTile, WebNode> regionEntrances){
		this.regionBase = regionBase;
		this.regionEntrances = regionEntrances;
	}

	public WebNode getEntrance(WorldTile i){
		return regionEntrances.get(i);
	}

	public WorldTile getRegionBase(){
		return this.regionBase;
	}

	public Map<WorldTile, WebNode> getRegionEntrances(){
		return this.regionEntrances;
	}

	public WebNode getClosest(WorldTile tile){
		WebNode out = null;
		int best = Integer.MAX_VALUE;
		if(isLoaded() && tile.isLoaded()) {
			for (WebNode i: regionEntrances.values()) {
				if(i.getTile(regionBase) != null) {
					Path<Tile> path = AStar.findPath(new Tile(tile), new Tile(i.getTile(regionBase)));
					if (path != null && path.getPathCost() < best) {
						out = i;
						best = path.getPathCost();
					}
				}
			}
		}else{
			for (WebNode i: regionEntrances.values()) {
				int dist = (int) i.getTile(regionBase).getDistance(tile);
				if(dist < best){
					out = i;
					best = dist;
				}
			}
		}
		return out;
	}

	public boolean isLoaded(){
		WorldTile[] corners = {regionBase, regionBase.derive(Util.WEB_GRID_SIZE, 0), regionBase.derive(0, Util.WEB_GRID_SIZE), regionBase.derive(Util.WEB_GRID_SIZE, Util.WEB_GRID_SIZE)};
		for(WorldTile i:corners){
			if(!i.isLoaded()){
				return false;
			}
		}
		return true;
	}

	public void draw(Graphics g){
		WorldTile tmp = regionBase;
		for(int i = 0; i < 4; i++){
			EDGE[] edgeOrder = {EDGE.WEST, EDGE.NORTH, EDGE.EAST, EDGE.SOUTH};
			Point[] edgeDelta = {new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0)};
			for(int i2 = 0; i2 < Util.WEB_GRID_SIZE; i2++){
				drawTileEdge(g, tmp, edgeOrder[i]);
				if(i2 < Util.WEB_GRID_SIZE-1) {
					tmp = tmp.derive(edgeDelta[i].x, edgeDelta[i].y);
				}
			}
		}
		if(regionEntrances != null){
			for(WebNode i: regionEntrances.values()){
				i.draw(g);
			}
		}
	}


	@Override
	public String toString() {
		return regionBase.getX()+","+regionBase.getY()+","+regionBase.getPlane();
	}

	private static void drawTileEdge(Graphics g, WorldTile t, EDGE edge){
		int xoffset = edge == EDGE.EAST || edge == EDGE.SOUTH  ? 128 : 0;
		int yoffset = edge == EDGE.NORTH || edge == EDGE.EAST  ? 128 : 0;
		int endxOffset =  edge == EDGE.NORTH || edge == EDGE.EAST ? 128 : 0;
		int endyOffset = edge == EDGE.WEST || edge == EDGE.NORTH  ? 128 : 0;
		Point s = Perspective.worldToViewort(t.getRegionX() * 128 + xoffset, t.getRegionY() * 128 + yoffset, 0);
		Point e = Perspective.worldToViewort(t.getRegionX() * 128 + endxOffset, t.getRegionY() * 128 + endyOffset, 0);
		g.setColor(Color.WHITE);
		g.drawLine(s.x, s.y, e.x, e.y);
	}
}
