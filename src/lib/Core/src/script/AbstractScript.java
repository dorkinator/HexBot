package script;

import com.hexrealm.hexos.api.Chrono;
import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.MousePrePollEvent;
import com.hexrealm.hexos.event.impl.RenderEvent;
import com.hexrealm.hexos.script.Script;
import mouse.MouseController;
import random.Condition;
import random.Random;
import randomevent.RandomEvent;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public abstract class AbstractScript extends Script {
	private ArrayList<RandomEvent> randomEvents = new ArrayList<>();
	private static MouseController mouseController = new MouseController();
	private static Settings settings;

	public AbstractScript(){

		ScriptEventDispatcher.register(RenderEvent.class, this::onRepaint);
		ScriptEventDispatcher.register(MousePrePollEvent.class, this::mousePrepollEvent);
	}

	public AbstractScript(Class profile){
		settings = new Settings(profile);
		ScriptEventDispatcher.register(RenderEvent.class, this::onRepaint);
		ScriptEventDispatcher.register(MousePrePollEvent.class, this::mousePrepollEvent);
	}

	public void mousePrepollEvent(MousePrePollEvent e){
		mouseController.mousePrepollEvent(e);
	}

	private void onRepaint(RenderEvent e) {
		mouseController.draw(e.getGraphics());
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


	public static void queueMouseMove(int x, int y, boolean blocking){
		mouseController.queueMove(x, y);
		sleepUntil(5000, new Condition() {
			@Override
			public boolean validate() {
				return !mouseController.isMoving();
			}
		});
	}

	public static void queueMouseMove(int x, int y){
		queueMouseMove(x, y, true);
	}

	public static void queueMouseMove(Point p){
		mouseController.queueMove(p.x, p.y);
	}

	public static Settings getSettings(){
		return settings;
	}

}
