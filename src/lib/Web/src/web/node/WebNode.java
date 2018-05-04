package web.node;

import com.hexrealm.hexos.api.Perspective;
import com.hexrealm.hexos.api.Players;
import com.hexrealm.hexos.api.model.WorldTile;
import web.Web;
import web.astar.Pathable;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dorkinator on 4/18/2018.
 */
public class WebNode implements Pathable<WebNode>{

	protected WorldTile t1, t2;

	private Map<Integer, Connection> connectionMap = new HashMap<>();

	private transient int gScore = Integer.MAX_VALUE;

	private transient int fScore = Integer.MAX_VALUE;
	private transient WebNode parent;
	public WebNode(WorldTile t1, WorldTile t2){
		this.t1 = t1;
		this.t2 = t2;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof WebNode){
			WebNode o2 = (WebNode)o;
			return this.t1.equals(o2.t1) && this.t2.equals(o2.t2);
		}
		return false;
	}

	public WorldTile getTile(WorldTile tile){
		WorldTile regionBase = Web.getWebRegionBase(tile);
		return regionBase.equals(Web.getWebRegionBase(t1)) ? t1 : regionBase.equals(Web.getWebRegionBase(t2)) ? t2 : null;
	}

	public Map<Integer, Connection> getConnectionMap() {
		return connectionMap;
	}

	public WorldTile getTile(){
		return t1;
	}

	public void addConnections(Map<Integer, Connection> connectionMap){
		this.connectionMap.putAll(connectionMap);
	}

	@Override
	public WebNode getParent() {

		return this.parent;
	}

	@Override
	public void setParent(WebNode p) {
		this.parent = p;
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
	public int distance(WebNode p) {
		if(p.connectionMap.containsKey(this.hashCode())){
			return p.connectionMap.get(this.hashCode()).getCost();
		}
		//return (int)(Math.hypot((t1.getX()+t2.getX()) - (p.t1.getX()+p.t2.getX()), (t1.getY()+t2.getY()) - (p.t1.getY()+p.t2.getY()))/2);//caused bad routes some times so it's gone
		return 100000;
	}

	@Override
	protected WebNode clone() throws CloneNotSupportedException {
		WebNode out =  new WebNode(t1, t2);
		out.addConnections(connectionMap);
		return out;
	}

	@Override
	public Set<WebNode> getValidNeighbors() {
		Set<WebNode> out = new HashSet<>();
		for(Connection i: connectionMap.values()){
			if(i.getConnectedNode() != null) {
				try {
					out.add(i.getConnectedNode().clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		return out;
	}

	@Override
	public int hashCode() {
		return t1.hashCode()+t2.hashCode();
	}

	@Override
	public String toString() {
		return t1.getX()%100+","+t1.getX()%100+":"+t2.getX()%100+","+t2.getY()%100;
	}


	public void draw(Graphics g) {
		WorldTile t = getTile(Players.local().getWorldTile());
		Point p = Perspective.worldToViewort(t.getRegionX() * 128 + 64, t.getRegionY() * 128 + 64, 0);
		int offset = (7/2) - 1;
		g.setColor(Color.PINK);
		g.fillOval(p.x - offset, p.y - offset, 7, 7);
		g.setColor(Color.cyan);
		for(Connection i:connectionMap.values()){
			Point p2 = Perspective.worldToViewort(i.getDestination().getRegionX() * 128 + 64, i.getDestination().getRegionY() * 128 + 64, 0);
			g.drawLine(p.x, p.y, p2.x, p2.y);
		}
	}

}
