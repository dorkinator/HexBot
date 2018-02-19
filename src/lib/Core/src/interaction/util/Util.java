package interaction.util;

import com.hexrealm.hexos.api.Walking;
import com.hexrealm.hexos.api.model.Locatable;
import com.hexrealm.hexos.api.model.RegionTile;
import random.Random;

/**
 * Created by Dorkinator on 2/15/2018.
 */
public class Util {
	public static void walkNearLocatable(Locatable t, int range){
		RegionTile rt = new RegionTile((Random.nextInt(range) + Random.nextInt(range)) / 2 - range + t.getRegionX(), (Random.nextInt(range) + Random.nextInt(range)) / 2 - range + t.getRegionY());
		System.out.println("walkTo: ["+rt.getX()+", "+rt.getY()+", "+range+"]");
		Walking.walkTo(rt);

	}
}
