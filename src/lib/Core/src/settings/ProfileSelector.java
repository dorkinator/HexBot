package settings;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dorkinator on 3/5/2018.
 */
public class ProfileSelector{

	public static File getProfile() {
		return (File) JOptionPane.showInputDialog(null, "Select profile", "Profile Selector", JOptionPane.PLAIN_MESSAGE, null, getContainedConfigs(new File(Settings.getConfigDirUri())).toArray(), "");
	}


	private static ArrayList<File> getContainedConfigs(final File folder){
		ArrayList<File> out = new ArrayList<>();
		for(File i:folder.listFiles()){
			if(i.isDirectory()){
				out.addAll(getContainedConfigs(i));
			}else{
				out.add(i);
			}
		}
		return out;
	}

	public static void main(String[] args) {
		new ProfileSelector();
	}
}
