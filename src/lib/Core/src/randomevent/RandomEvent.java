package randomevent;

import java.awt.*;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public abstract class RandomEvent {
	private boolean enabled = true;



	public abstract boolean activate();
	public abstract int onTick();
	public abstract void draw(Graphics g);

	public void evaluate(){
		if(activate()){
			System.out.println("Random event started.");
			int tickDelay;
			while((tickDelay = onTick()) > 0){
				try {
					Thread.sleep(tickDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
