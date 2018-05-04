import com.hexrealm.hexos.api.Client;
import com.hexrealm.hexos.api.LoginScreen;
import com.hexrealm.hexos.event.impl.RenderEvent;
import random.RandomDelay;
import randomevent.RandomEvent;
import script.AbstractScript;
import util.BUTTON;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public class LoginRandomEvent extends RandomEvent{

	@Override
	public boolean activate() {
		return Client.getState() == Client.STATE_LOGIN_SCREEN;
	}

	@Override
	public int onTick() {
		if(Client.getState() == Client.STATE_INGAME){
			return -1;
		}else{
			switch (LoginScreen.getState()){
				case LoginScreen.STATE_DEFAULT:
					BUTTON.EXISTING_USER.click();
					break;
				case LoginScreen.STATE_INPUT_AUTHENTICATOR:
					System.out.println("Authenticantor not supported");
					break;
				case LoginScreen.STATE_INPUT_CREDENTIALS:
					LoginScreen.setUsername(getUser());
					LoginScreen.setPassword(getPass());
					BUTTON.LOGIN.click();
					break;
				case LoginScreen.STATE_INVALID_CREDENTIALS:

					System.out.println("Bad credentials: ");
					break;
				default:
					System.out.println("Bad login screen state: "+LoginScreen.getState());

					break;

			}
		}
		return RandomDelay.SHORT.get();
	}

	@Override
	public void draw(RenderEvent e) {

	}

	private static String getUser(){
		return AbstractScript.getSettings().get().loginProperties.user;
	}
	private static String getPass(){
		return AbstractScript.getSettings().get().loginProperties.pass;
	}

}
