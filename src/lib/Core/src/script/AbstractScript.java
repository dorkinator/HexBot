package script;

import com.hexrealm.hexos.script.Script;
import random.Condition;
import random.Random;
import randomevent.RandomEvent;

import java.util.ArrayList;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public abstract class AbstractScript extends Script {
	private ArrayList<RandomEvent> randomEvents = new ArrayList<>();

	@Override
	public void cleanup() {
		super.cleanup();
	}

	@Override
	public int tick() {

		for(RandomEvent i:randomEvents){
			i.evaluate();
		}
		return onTick();
	}

	public abstract int onTick();

	public void addRandomEvent(RandomEvent event){
		randomEvents.add(event);
	}

	public boolean removeRandomEvent(RandomEvent event){
		return randomEvents.remove(event);
	}

	public ArrayList<RandomEvent> getRandomEvents(){
		return randomEvents;
	}

	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean sleepUntil(long timeout, Condition condition){
		long startTime = System.currentTimeMillis();
		while(!condition.validate()){
			sleep(Random.nextInt(0,25));
			if(startTime+timeout > System.currentTimeMillis()){
				return false;
			}
		}
		return true;
	}

}
