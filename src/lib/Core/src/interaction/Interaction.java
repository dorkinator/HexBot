package interaction;

import com.hexrealm.hexos.Environment;
import com.hexrealm.hexos.api.Chrono;
import com.hexrealm.hexos.api.Client;
import com.hexrealm.hexos.api.Perspective;
import com.hexrealm.hexos.api.model.Interactable;
import com.hexrealm.hexos.api.model.Locatable;
import javafx.util.Pair;
import random.Random;
import script.AbstractScript;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Dorkinator on 2/3/2018.
 */
public abstract class Interaction <T extends Interactable & Locatable>{

	private String action;
	private int cachedActionId = Integer.MIN_VALUE;
	protected Predicate<T> predicate;
	private int actionHashCode = 0;
	private T lastInteracted;
	private long lastInteraction = System.currentTimeMillis();

	public Interaction(Predicate<T> predicate, String action){
		this.action = action;
		this.predicate = predicate;
	}

	public T getTarget(List<T> selection){
		if(selection.size() > 0 && selection.get(0) != null) {
			selection.sort(comparartor());
			return selection.get(0);
		} else {
			System.out.println("No objects matching predicate found");
			return null;
		}
	}

	protected void interact(T t){
		if(t != null){
			if(cachedActionId == Integer.MIN_VALUE || this.actionHashCode != getActionId(action, t)){
				cachedActionId = getActionId(action, t);
			}
			if(cachedActionId != Integer.MIN_VALUE) {
				Point p = Perspective.worldToViewort(t.getAbsoluteX(), t.getAbsoluteY(), 0);
				Rectangle canvas = new Rectangle(Environment.getClient().getCanvas().getBounds());
				if(p == null){
					System.out.println("p null");
				}
				if(canvas == null){
					System.out.println("canavas null");
				}
				if(canvas.contains(p)) {
					AbstractScript.queueMouseMove(p.x + Random.nextInt(0, 40) - 20, p.y + Random.nextInt(0, 40) - 20);
				}else{
					AbstractScript.queueMouseMove(Random.nextInt(canvas.width), Random.nextInt(canvas.height));
				}
				lastInteracted = t;
				lastInteraction = System.currentTimeMillis();
				Chrono.sleep(Random.nextInt(0, 30));
				if(!Client.isFocused()) {
					Client.setGainedFocus(true);
				}
				t.interact(cachedActionId);
			}
		} else {
			System.out.println("Null target");
		}
	}

	public abstract Comparator<T> comparartor();

	protected abstract Pair<String[], int[]> getActions(T t);

	private int getActionId(String action, T t){
		Pair<String[], int[]> actionPair = getActions(t);
		String[] options = actionPair.getKey();
		int[] actionIds = actionPair.getValue();
		this.actionHashCode = options.hashCode();
		for(int i = 0; i < actionPair.getKey().length; i++){
			if(options[i] != null && options[i].equalsIgnoreCase(action)){
				return actionIds[i];
			}
		}
		System.out.println("Can't find action: "+action);
		return Integer.MIN_VALUE;
	}
	public T getLastInteracted(){
		return lastInteracted;
	}

	public long getLastInteraction(){
		return lastInteraction;
	}

}
