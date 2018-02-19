import com.hexrealm.hexos.accessor.PlayerAccessor;
import com.hexrealm.hexos.api.Players;
import com.hexrealm.hexos.api.Walking;
import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.RenderEvent;
import random.Random;
import script.AbstractScript;

/**
 * Created by Dorkinator on 1/31/2018.
 */
public class Main1 extends AbstractScript {


	@Override
	public boolean setup() {
		LoginRandomEvent loginbot = new LoginRandomEvent("", "");
		addRandomEvent(loginbot);
		ScriptEventDispatcher.register(RenderEvent.class, this::onRepaint);
		return true;
	}


	public void onRepaint(RenderEvent e){

	}

	@Override
	public int onTick() {
		int range = 10;
		PlayerAccessor t = Players.local();
		Walking.walkTo((Random.nextInt(range) + Random.nextInt(range)) / 2 - range + t.getRegionX(), (Random.nextInt(range) + Random.nextInt(range)) / 2 - range + t.getRegionY());
		return 10000;
	}
}
