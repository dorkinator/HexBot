import banking.Bank;
import banking.Bank.InsufficientItemQuantityException;
import banking.Bank.ItemNotFoundException;
import com.hexrealm.hexos.api.Perspective;
import com.hexrealm.hexos.api.model.WorldTile;
import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.RenderEvent;
import script.AbstractScript;
import script.nodescript.Node;
import settings.Properties;

import java.awt.*;
import java.util.List;

/**
 * Created by Dorkinator on 1/31/2018.
 */
public class Main1 extends AbstractScript{
	private Node walk;
	private int walkState = 1;

	public Main1() {
		super(Properties.class);
	}

	@Override
	public int onTick() {
		try {
			Bank.withdraw("Leather chaps", 1);
		} catch (ItemNotFoundException | InsufficientItemQuantityException e) {
			e.printStackTrace();
		}
		return 1000;
	}

	@Override
	public boolean setup(List<String> list) {
		AbstractScript.getSettings().get();
		ScriptEventDispatcher.register(RenderEvent.class, this::onRepaint);
		return true;
	}

	private void onRepaint(RenderEvent e) {
		Graphics g = e.getGraphics();
	}

	private void drawTile(Graphics g, WorldTile tile){
		Point p = Perspective.worldToViewort(tile.getRegionX() * 128 + 64, tile.getRegionY() * 128 + 64, 70);
		int offset = (7 / 2) - 1;
		g.fillOval(p.x - offset, p.y - offset, 7, 7);
	}

}
