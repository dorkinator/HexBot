package web.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hexrealm.hexos.Environment;
import com.hexrealm.hexos.api.model.WorldTile;
import web.Web;
import web.node.WebRegion;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Dorkinator on 4/5/2018.
 */
public class Util {
	public static final int WEB_GRID_SIZE = 16;

	public static final int W_N = 2;
	public static final int W_S = 32;
	public static final int W_W = 128;
	public static final int W_E = 8;
	public static final int W_SW = 64;
	public static final int W_SE = 16;
	public static final int W_NW = 1;
	public static final int W_NE = 4;
	public static final int W_NSEW = W_N | W_S | W_E | W_W;
	public static final int W_NWNESWSE = W_NW | W_NE | W_SW | W_SE;
	public static final int BLOCKED4 = 262144;
	public static final int BLOCKED3 = 131328;
	public static final int BLOCKED2 = 2097152;
	public static final int BLOCKED = 256;
	public static final int WATER = 19398912;
	public static final int WALKABLE = WATER | BLOCKED | BLOCKED2 | BLOCKED4  | BLOCKED3;

	public static int getWalkingFlags(WorldTile t){
		if(t.isLoaded()){
			return Environment.getClient().getCollisionMaps()[t.getPlane()].getFlags()[t.getRegionX()][t.getRegionY()];
		}
		return -1;
	}


	public static void writeWeb(){
		try {
			if(!new File(getWebUri()).exists()){
				new File(getWebUri()).getParentFile().mkdirs();
			}
			try (Writer writer = new OutputStreamWriter(new FileOutputStream(getWebUri()))) {
				Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().enableComplexMapKeySerialization().registerTypeAdapter(WorldTile.class, new WorldTileTypeAdapter()).create();
				Type WorldTileWebRegionMap = new TypeToken<HashMap<WorldTile, WebRegion>>(){}.getType();
				gson.toJson(Web.webRegions, WorldTileWebRegionMap, writer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void loadWeb(){
		try(InputStreamReader reader = new InputStreamReader(new FileInputStream(getWebUri()))) {
			Gson gson = new GsonBuilder().disableHtmlEscaping().enableComplexMapKeySerialization().registerTypeAdapter(WorldTile.class, new WorldTileTypeAdapter()).create();
			Type WorldTileWebRegionMap = new TypeToken<HashMap<WorldTile, WebRegion>>(){}.getType();
			Web.webRegions = gson.fromJson(reader, WorldTileWebRegionMap);
		}catch (FileNotFoundException fnf){
			//getWeb
		}catch (Exception e){
			e.getStackTrace();
		}
	}

	public static String getWebUri(){
		return System.getProperty("user.home")+"\\HexOS\\web\\web.json";
	}
}
