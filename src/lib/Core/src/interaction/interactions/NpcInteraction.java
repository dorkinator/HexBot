package interaction.interactions;

import com.hexrealm.hexos.accessor.NpcAccessor;
import com.hexrealm.hexos.api.ContextMenu;
import com.hexrealm.hexos.api.Npcs;
import interaction.Interaction;
import javafx.util.Pair;

import java.util.function.Predicate;

/**
 * Created by Dorkinator on 2/5/2018.
 */
public abstract class NpcInteraction extends Interaction<NpcAccessor> {

	public static final int[] NpcOptions = {
			ContextMenu.ACTION_NPC_FIRST_OPTION,
			ContextMenu.ACTION_NPC_SECOND_OPTION,
			ContextMenu.ACTION_NPC_THIRD_OPTION,
			ContextMenu.ACTION_NPC_FOURTH_OPTION,
			ContextMenu.ACTION_NPC_FIFTH_OPTION
	};

	public NpcInteraction(Predicate<NpcAccessor> predicate, String action) {
		super(predicate, action);
	}

	public void interact(){
		interact(Npcs.matching(predicate));
	}

	@Override
	protected Pair<String[], int[]> getActions(NpcAccessor t) {
		return new Pair<>(t.getDefinition().getActions(), NpcOptions);
	}
}
