import Util.GUI;
import com.hexrealm.hexos.script.Script;

import java.util.List;

/**
 * Created by Dorkinator on 1/31/2018.
 */
public class Main3 extends Script {
	private GUI gui;
	@Override
	public int tick() {
		return 1000;
	}

	@Override
	public boolean setup(List<String> args) {
		gui = new GUI();
		return true;
	}
}
