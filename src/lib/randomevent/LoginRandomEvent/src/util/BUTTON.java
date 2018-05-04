package util;

		import com.hexrealm.hexos.api.Mouse;
		import random.Random;

		import java.awt.*;

/**
 * Created by Dorkinator on 2/2/2018.
 */
public enum BUTTON {
	EXISTING_USER(new Rectangle(396,272,130,30)),LOGIN(new Rectangle(236,305,130,30));

	Rectangle buttonArea;

	BUTTON(Rectangle buttonArea){
		this.buttonArea = buttonArea;
	}

	public void click(){
		Mouse.queueClick(Random.nextInt(buttonArea.x, buttonArea.x+buttonArea.width), Random.nextInt(buttonArea.y,buttonArea.y+buttonArea.height), Mouse.BUTTON_LEFT);
	}

}
