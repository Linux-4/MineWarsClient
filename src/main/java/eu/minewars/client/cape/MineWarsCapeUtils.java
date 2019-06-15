package eu.minewars.client.cape;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class MineWarsCapeUtils {
	
	public static boolean capeExists(UUID id) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL("http://cape.minewars.eu/" + id + ".png").openConnection();
			return conn.getResponseCode() == 200;
		} catch(Exception ex) {
			return false;
		}
	}
	
	public static UUID getUUID(AbstractClientPlayer p) {
		return EntityPlayer.getUUID(p.getGameProfile());
	}
	
}
