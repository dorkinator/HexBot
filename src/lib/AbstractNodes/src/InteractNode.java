import interaction.InteractionType;
import random.Condition;
import random.RandomDelay;
import script.AbstractScript;
import script.nodescript.Node;

/**
 * Created by Dorkinator on 2/6/2018.
 */
public abstract class InteractNode extends Node {
	InteractionType interaction;
	long timeout;
	Condition interupt;

	public InteractNode(String name, InteractionType interaction, Condition interupt, long timeout) {
		super("InteractNode: "+name);
		this.interaction = interaction;
		this.timeout = timeout;
		this.interupt = interupt;
	}

	@Override
	public int execute() {
		interaction.interact();
		AbstractScript.sleepUntil(timeout, interupt);
		return RandomDelay.NODE_TICK.get();
	}
}
