package web;

import com.hexrealm.hexos.api.Players;
import com.hexrealm.hexos.api.model.WorldTile;
import web.node.WebRegion;
import web.util.Util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dorkinator on 4/18/2018.
 */
public class Web {
	public static Map<WorldTile, WebRegion> webRegions = new HashMap<>();

	public static void draw(Graphics g) {
		try {
			WorldTile center = Players.local().getWorldTile();
			for (WebRegion i : webRegions.values()) {
				if (getWebRegionBase(center).getDistance(i.getRegionBase()) == 0) {
					g.setColor(new Color(255,0,0,100));
				} else {
					g.setColor(new Color(255,255,255,35));
				}

				g.fillRect(400 + i.getRegionBase().getX() - center.getX(), 400 + i.getRegionBase().getY() - center.getY(), 16, 16);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	public static WebRegion getWebRegion(WorldTile worldTile) {
		return webRegions.get(getWebRegionBase(worldTile));
	}

	public static WorldTile getWebRegionBase(WorldTile tile){
		return tile.derive(-tile.getX() % Util.WEB_GRID_SIZE, -tile.getY() % Util.WEB_GRID_SIZE);
	}
}
