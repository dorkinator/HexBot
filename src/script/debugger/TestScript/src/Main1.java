import com.hexrealm.hexos.accessor.NpcAccessor;
import com.hexrealm.hexos.accessor.PlayerAccessor;
import com.hexrealm.hexos.api.Players;
import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.RenderEvent;
import interaction.interactions.NpcInteraction;
import random.Random;
import script.AbstractScript;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by Dorkinator on 1/31/2018.
 */
public class Main1 extends AbstractScript {

	private NpcInteraction killChicken = new NpcInteraction(
			new Predicate<NpcAccessor>() {
				@Override
				public boolean test(NpcAccessor t) {
					return t.getName().equals("Chicken") && t.getInteractingEntityIndex() == -1 && !beingInteractedWith(t);
				}
			}, "Attack") {
		@Override
		public Comparator<NpcAccessor> comparartor() {
			return new Comparator<NpcAccessor>() {
				@Override
				public int compare(NpcAccessor o1, NpcAccessor o2) {
					return (int) (o1.getDistance() - o2.getDistance());
				}
			};
		}
	};

	@Override
	public int onTick() {
		if(Players.local().getInteractingEntityIndex() == -1){
			killChicken.interact();
			System.out.println("Killing chicken");
		}
		return Random.nextInt(100,200);
	}

	public boolean beingInteractedWith(NpcAccessor t){
		for(PlayerAccessor i: Players.all()){
			if(i.getInteractingEntityIndex() == t.getIndex()){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setup() {
		LoginRandomEvent loginbot = new LoginRandomEvent("", "");
		addRandomEvent(loginbot);
		ScriptEventDispatcher.register(RenderEvent.class, this::onRepaint);
		return true;
	}


	public void onRepaint(RenderEvent e){

	}
}
