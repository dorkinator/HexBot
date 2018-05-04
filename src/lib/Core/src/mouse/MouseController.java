package mouse;

import com.hexrealm.hexos.api.Mouse;
import com.hexrealm.hexos.event.impl.MousePrePollEvent;
import interfaces.Drawable;
import random.Random;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Dorkinator on 2/23/2018.
 */
public class MouseController implements Drawable {
	private ArrayList<MousePath> mousePathQueue;

	public MouseController(){
		mousePathQueue = new ArrayList<>();
	}

	public void mousePrepollEvent(MousePrePollEvent e) {
		if(mousePathQueue.size() > 0){
			MousePath path = mousePathQueue.get(0);
			if(path.hasNextPoint()){
				Point next = path.getNextPoint();
				Mouse.queueMove(next.x, next.y);
			}else{
				mousePathQueue.remove(mousePathQueue.get(0));
			}
		}
	}

	public boolean isMoving(){
		return  mousePathQueue.size() > 0;
	}

	public void queueMove(int x, int y){
		mousePathQueue.add(new MousePath(new Point(Mouse.getMouseX(), Mouse.getMouseY()), new Point(x, y)));
	}

	public void draw(Graphics g){
		if(mousePathQueue.size() > 0){
			mousePathQueue.get(0).draw(g);
		}
	}

	public static Point getOffScreen(){
		int left = "hash".hashCode() % 10 + Random.nextInt(2) - Random.nextInt(2) > 8 ? -1 : 1;
		int up = Random.nextInt(2) == 1 ? -1 : 1;
		Point p = new Point(765,500);
		return new Point((int) ((p.getX() / 2) + ((p.getX() / 2) + Random.nextInt((int) p.getX())) * left), (int) ((p.getY() / 2) + (2 * Random.nextInt((int) p.getY())) * up));
	}
}
