package abstractnodes;

import com.hexrealm.hexos.api.Players;
import com.hexrealm.hexos.api.Walking;
import com.hexrealm.hexos.api.model.WorldTile;
import random.Condition;
import random.Random;
import random.RandomDelay;
import script.AbstractScript;
import script.nodescript.Node;
import web.astar.pathable.Traversable;

/**
 * Created by Dorkinator on 4/14/2018.
 */
public abstract class TraversePath extends Node {

	Traversable traverse = null;
	private int nextDest = 0;
	private WorldTile end;

	public TraversePath(WorldTile end) {
		super("Traversing Path");
		this.end = end;
	}

	public boolean shouldTraverse(){
		return Players.local().getPathQueueLength() == 0 || Walking.getDestination() == null || Walking.getDestination().getDistance() < nextDest;
	}

	@Override
	public int execute() {
		try {
			traverse = new Traversable(Players.local().getWorldTile(), end);
			traverse.traverse();
			if (AbstractScript.sleepUntil(Random.nextInt(100, 600), new Condition() {
				@Override
				public boolean validate() {
					return Walking.getDestination() != null;
				}
			})) {
				nextDest = (int) Walking.getDestination().getDistance();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return RandomDelay.NODE_TICK.get();
	}
}
