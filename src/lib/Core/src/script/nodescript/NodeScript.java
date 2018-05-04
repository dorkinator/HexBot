package script.nodescript;

import random.RandomDelay;
import script.AbstractScript;
import settings.Properties;

import java.util.ArrayList;

/**
 * Created by Dorkinator on 2/2/2018.
 */
public abstract class NodeScript extends AbstractScript {
	private ArrayList<Node> jobs = new ArrayList<>();
	private String lastNode = "";
	private long lastNodeStartTime = 0;
	private long lastNodeEndTime = 0;

	public NodeScript(Class profile) {
		super(profile);
	}

	@Override
	public int onTick() {
		for(Node i:jobs){
			if(i.activate()){
				lastNodeStartTime = System.currentTimeMillis();
				lastNode = i.getName();
				System.out.println(i.getName());
				int sleepTime = i.execute();
				lastNodeEndTime = System.currentTimeMillis();
				return sleepTime;
			}
		}
		return RandomDelay.NODE_TICK.get();
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
