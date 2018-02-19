import Util.GUI;
import com.hexrealm.hexos.script.Script;

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
	public boolean setup() {
		gui = new GUI();
		return true;
	}
}
