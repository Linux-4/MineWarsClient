package eu.minewars.client.cape;

import java.net.HttpURLConnection;
import java.net.URL;

public class MineWarsCapeUtils {
	
	public static boolean capeExists(String name) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL("http://cape.minewars.eu/" + name + ".png").openConnection();
			return conn.getResponseCode() == 200;
		} catch(Exception ex) {
			return false;
		}
	}
	
}
