package eu.minewars.client.gui;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class LabyModCapeUtils {

	public static boolean capeExists(UUID id) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL("http://capes.labymod.net/cape/" + id).openConnection();
			return conn.getResponseCode() == 200;
		} catch(Exception ex) {
			return false;
		}
	}
	
}
