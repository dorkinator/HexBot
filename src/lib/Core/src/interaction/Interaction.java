package interaction;

import com.hexrealm.hexos.api.model.Interactable;
import com.hexrealm.hexos.api.model.Locatable;
import javafx.util.Pair;

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

	public Interaction(Predicate<T> predicate, String action){
		this.action = action;
		this.predicate = predicate;
	}

	protected void interact(List<T> selection){
		System.out.println(selection.size());
		if(selection.size() > 0 && selection.get(0) != null) {
			selection.sort(comparartor());
			T t = selection.get(0);
			if(cachedActionId == Integer.MIN_VALUE || this.actionHashCode != getActionId(action, t)){
				cachedActionId = getActionId(action, t);
			}
			if(cachedActionId != Integer.MIN_VALUE) {
				t.interact(cachedActionId);
			}
		} else {
			System.out.println("No objects matching predicate found");
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

}
