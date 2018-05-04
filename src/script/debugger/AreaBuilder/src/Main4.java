import com.hexrealm.hexos.script.Script;
import util.GUI;

import java.util.List;

/**
 * Created by Dorkinator on 2/18/2018.
 */
public class Main4 extends Script {
	@Override
	public boolean setup(List<String> list) {
		GUI gui = new GUI();
		return true;
	}

	@Override
	public int tick() {
		return 10000;
	}
}
