package web.node;

import com.hexrealm.hexos.api.model.WorldTile;
import web.Web;

/**
 * Created by Dorkinator on 4/18/2018.
 */
public class Connection {
	private CONNECTIONTYPE connectiontype = CONNECTIONTYPE.WALKING;

	private int cost;
	private WorldTile destination;
	public Connection(WorldTile destination, int cost, CONNECTIONTYPE connectiontype){
		this.destination = destination;
		this.connectiontype = connectiontype;
		this.cost = cost;
	}

	public WebNode getConnectedNode(){
		return Web.getWebRegion(destination).getEntrance(destination);
	}


	public CONNECTIONTYPE getConnectiontype() {
		return connectiontype;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return destination.getX()%100+","+destination.getY()%100+"("+cost+")";
	}

	public WorldTile getDestination() {
		return destination;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Connection){
			Connection i = (Connection)o;
			if(this.getDestination().equals(i.getDestination()) && this.getCost() == i.getCost()) {
				return true;
			}
		}
		return false;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
