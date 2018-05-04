import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.RenderEvent;
import com.hexrealm.hexos.script.Script;
import util.GUI;

import java.util.List;

/**
 * Created by Dorkinator on 1/31/2018.
 */
public class Main extends Script {
	private GUI gui;
	@Override
	public int tick() {
		return 1000;
	}

	@Override
	public boolean setup(List<String> args) {
		gui = new GUI();
		ScriptEventDispatcher.register(RenderEvent.class, gui::onRenderEvent);
		return true;
	}
}
