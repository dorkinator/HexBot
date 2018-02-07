package abstractnodes;

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
	Condition[] interupt;

	public InteractNode(String name, InteractionType interaction, long timeout, Condition... interupt) {
		super("InteractNode: "+name);
		this.interaction = interaction;
		this.timeout = timeout;
		this.interupt = interupt;
	}

	@Override
	public int execute() {
		interaction.interact();
		long stopTime = System.currentTimeMillis()+timeout;
		for(Condition i:interupt) {
			AbstractScript.sleepUntil(stopTime- System.currentTimeMillis(), i);
		}
		return RandomDelay.NODE_TICK.get();
	}
}
