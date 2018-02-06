package script.nodescript;

import script.AbstractScript;

import java.util.ArrayList;

/**
 * Created by Dorkinator on 2/2/2018.
 */
public abstract class NodeScript extends AbstractScript {
	private ArrayList<Node> jobs = new ArrayList<>();

	@Override
	public int onTick() {
		return 0;
	}

	public void addNodes(Node... nodes){
		for(Node i:nodes){
			jobs.add(i);
		}
	}

	public void removeNodes(Node... nodes){
		for(Node i:nodes){
			jobs.remove(i);
		}
	}
}
