package tabs;

import com.hexrealm.hexos.api.Widgets;
import interaction.interactions.InterfaceInteraction;

/**
 * Created by Dorkinator on 3/30/2018.
 */
public enum TAB {
	COMBAT(10551347), STATS(10551348), QUEST(10551349), INVENTORY(10551350), EQUIPMENT(10551351), PRAYER(10551352), SPELLBOOK(10551353),
	CLANCHAT(10551331), FRIENDSLIST(10551332), INGORELIST(10551333), LOGOUT(10551334), SETTINGS(10551335), EMOTES(10551336), MUSIC(10551337);

	private static TAB lastTab = INVENTORY;
	private int hash = -1;

	private InterfaceInteraction interaction;

	TAB(int hash){
		this.hash = hash;
		interaction = new InterfaceInteraction(Widgets.get(hash), "", null);
	}

	public boolean isOpen(){
		return lastTab == this;
	}

	public boolean open(){
		if(isOpen()){
			return true;
		}else{
			lastTab = this;
			interaction.interact();
		}
		return isOpen();
	}

	public static TAB getOpenTab(){
		return lastTab;
	}
}
