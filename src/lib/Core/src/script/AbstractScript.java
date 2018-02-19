package script;

import com.hexrealm.hexos.api.Chrono;
import com.hexrealm.hexos.api.Mouse;
import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.RenderEvent;
import com.hexrealm.hexos.script.Script;
import random.Condition;
import random.Random;
import randomevent.RandomEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public abstract class AbstractScript extends Script {
	private ArrayList<RandomEvent> randomEvents = new ArrayList<>();

	public AbstractScript(){
		ScriptEventDispatcher.register(RenderEvent.class, this::onRepaint);
		ScriptProperties.properties = new HashMap<>();
	}

	private void onRepaint(RenderEvent e) {
		e.getGraphics().setColor(Color.WHITE);
		e.getGraphics().drawRect(Mouse.getMouseX(), Mouse.getMouseY(), 3,3);
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

	public static boolean sleepUntil(long timeout, Condition condition){
		long startTime = System.currentTimeMillis();
		while(!condition.validate()){
			Chrono.sleep(Random.nextInt(0, 25));
			if(startTime+timeout < System.currentTimeMillis()){
				return false;
			}
		}
		return true;
	}

}
