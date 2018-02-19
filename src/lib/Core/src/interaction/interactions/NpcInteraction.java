package interaction.interactions;

import com.hexrealm.hexos.accessor.NpcAccessor;
import com.hexrealm.hexos.api.ContextMenu;
import com.hexrealm.hexos.api.Npcs;
import interaction.Interaction;
import interaction.InteractionType;
import interaction.util.Util;
import javafx.util.Pair;
import random.Condition;
import random.Random;
import random.RandomConstant;
import script.AbstractScript;

import java.util.function.Predicate;

/**
 * Created by Dorkinator on 2/5/2018.
 */
public abstract class NpcInteraction extends Interaction<NpcAccessor> implements InteractionType{

	public static final int[] NpcOptions = {
			ContextMenu.ACTION_NPC_FIRST_OPTION,
			ContextMenu.ACTION_NPC_SECOND_OPTION,
			ContextMenu.ACTION_NPC_THIRD_OPTION,
			ContextMenu.ACTION_NPC_FOURTH_OPTION,
			ContextMenu.ACTION_NPC_FIFTH_OPTION
	};

	private RandomConstant walkNearRate;
	private RandomConstant walkNearRange;
	private Condition interupt;

	public NpcInteraction(Predicate<NpcAccessor> predicate, String action, Condition interupt) {
		super(predicate, action);
		walkNearRate = new RandomConstant("walkNearRate", 1, 100, Random.nextInt(10*60*1000));
		walkNearRange = new RandomConstant("walkNearRange", 1, 6, Random.nextInt(10*60*1000));
		this.interupt = interupt;
	}

	@Override
	public void interact(){
		NpcAccessor t = getTarget(Npcs.matching(predicate));
		boolean interupted = false;
		if(Random.nextInt(200) < walkNearRate.get()){
			Util.walkNearLocatable(t, walkNearRange.get());
			interupted = AbstractScript.sleepUntil((Random.nextInt(100) < 94) ? Random.nextInt(10, 40) : Random.nextInt(0,10000), interupt);
		}
		if(!interupted) {
			interact(t);
		}
	}

	@Override
	public Interaction getInteraction() {
		return this;
	}

	@Override
	protected Pair<String[], int[]> getActions(NpcAccessor t) {
		return new Pair<>(t != null && t.getDefinition() != null ? t.getDefinition().getActions() : new String[]{}, NpcOptions);
	}
}
