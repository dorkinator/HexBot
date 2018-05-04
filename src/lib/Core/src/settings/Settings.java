package settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import settings.GsonTools.ConflictStrategy;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Dorkinator on 3/4/2018.
 */
public class Settings<T extends Properties> {
	T properties;
	File profile;
	Class clazz;
	HashMap loadedObject = new HashMap();

	public Settings(Class clazz){
		this.clazz = clazz;
		try {
			properties = (T) clazz.newInstance();
		}catch (Exception e){
			e.printStackTrace();
		}
		File profile = ProfileSelector.getProfile();
		load(profile);
	}

	public Settings(String profile, Class clazz){
		this.profile = new File(getConfigDirUri()+"\\"+profile);
		this.clazz = clazz;
		try {
			properties = (T) clazz.newInstance();
		}catch (Exception e){
			e.printStackTrace();
		}
		load(this.profile);
	}

	public void load(File file){
		try(InputStream reader = new FileInputStream(file)) {

			Gson gson = new GsonBuilder().create();
			String input = inputStreamToString(reader);
			properties = (T) gson.fromJson(input, clazz);
			loadedObject = gson.fromJson(input, HashMap.class);
		}catch (FileNotFoundException fnf){
			save(file);
			load(file);
		}catch (Exception e){
			e.getStackTrace();
		}
	}


	public void save(File file){
		try {
			if(!file.exists()){
				file.getParentFile().mkdirs();
			}
			try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
				Gson gson = new GsonBuilder().create();
				JsonObject recent = gson.toJsonTree(properties).getAsJsonObject();
				JsonObject old = gson.toJsonTree(loadedObject).getAsJsonObject();
				GsonTools.extendJsonObject(old, ConflictStrategy.PREFER_SECOND_OBJ, recent);
				gson.toJson(old, writer);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public T get(){
		return properties;
	}

	public void set(T t){
		this.properties = t;
	}

	public static String getConfigDirUri(){
		return System.getProperty("user.home")+"\\HexOS\\accounts";
	}

	private String getPath(){
		return getConfigDirUri()+"\\"+profile+".json";
	}

	private static String inputStreamToString(InputStream inputStream) throws IOException {
		try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}

			return result.toString("UTF-8");
		}
	}

}
