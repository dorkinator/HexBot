package randomevent;

import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.RenderEvent;
import com.hexrealm.hexos.script.ScriptController;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public abstract class RandomEvent {
	private boolean enabled = true;



	public abstract boolean activate();
	public abstract int onTick();
	public abstract void draw(RenderEvent e);

	public void evaluate(){
		if(activate()){
			ScriptEventDispatcher.register(RenderEvent.class, this::draw);
			System.out.println("Random event started.");
			int tickDelay;
			while((tickDelay = onTick()) > 0 && ScriptController.isRunning()){
				try {
					Thread.sleep(tickDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ScriptEventDispatcher.unregister(RenderEvent.class, this::draw);
			System.out.println("Random event ended.");
		}
	}

	public void setEnabled(boolean flag){
		enabled = flag;
	}

	public boolean isEnabled(){
		return enabled;
	}
}
