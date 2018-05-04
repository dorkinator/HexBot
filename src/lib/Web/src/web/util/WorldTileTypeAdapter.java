package web.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.hexrealm.hexos.api.model.WorldTile;

import java.io.IOException;

/**
 * Created by Dorkinator on 4/10/2018.
 */
public class WorldTileTypeAdapter extends TypeAdapter<WorldTile> {

	@Override
	public void write(JsonWriter w, WorldTile t) throws IOException {
		w.beginObject();
		w.name("x").value(t.getX());
		w.name("y").value(t.getY());
		w.name("z").value(t.getPlane());
		w.endObject();
	}

	@Override
	public WorldTile read(JsonReader r) throws IOException {
		int x = 0,y = 0,z = 0;
		r.beginObject();
		while(r.hasNext()){
			switch (r.nextName()) {
				case "x":
					x = r.nextInt();
					break;
				case "y":
					y = r.nextInt();
					break;
				case "z":
					z = r.nextInt();
					break;
			}
		}
		r.endObject();
		return new WorldTile(x,y,z);
	}
}
