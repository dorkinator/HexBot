import com.hexrealm.hexos.api.Client;
import com.hexrealm.hexos.api.LoginScreen;
import com.hexrealm.hexos.event.impl.RenderEvent;
import random.RandomDelay;
import randomevent.RandomEvent;
import script.ScriptProperties;
import util.BUTTON;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public class LoginRandomEvent extends RandomEvent{

	public LoginRandomEvent(String user, String pass){
		ScriptProperties.properties.put("username", user);
		ScriptProperties.properties.put("password", pass);
	}

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

	public static void setUsername(String user){
		ScriptProperties.properties.put("username", user);
	}
	public static void setPass(String pass){
		ScriptProperties.properties.put("password", pass);
	}
	private static String getUser(){
		return (String) ScriptProperties.properties.get("username");
	}
	private static String getPass(){
		return (String) ScriptProperties.properties.get("password");
	}

}
