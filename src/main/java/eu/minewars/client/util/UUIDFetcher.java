package eu.minewars.client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class UUIDFetcher {

	public static UUID getUUID(AbstractClientPlayer p) {
		try {
			HttpsURLConnection conn = (HttpsURLConnection) new URL("https://api.minetools.eu/uuid/" + p.getNameClear())
					.openConnection(Minecraft.getMinecraft().getProxy());
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String text = "";
			String line;
			while ((line = reader.readLine()) != null) {
				text = text + line;
			}

			JsonObject json = (JsonObject) new JsonParser().parse(text);
			return UUID.fromString(json.get("id").getAsString().replaceFirst(
					"(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
					"$1-$2-$3-$4-$5"));
		} catch (Exception ex) {
			return EntityPlayer.getUUID(p.getGameProfile());
		}
	}

}
