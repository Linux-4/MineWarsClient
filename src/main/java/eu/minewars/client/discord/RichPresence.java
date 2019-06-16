package eu.minewars.client.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import eu.minewars.client.MineWarsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class RichPresence extends Thread {

	private final DiscordRPC lib = DiscordRPC.INSTANCE;
	private final String applicationId = "589785332329742337";
	private final DiscordRichPresence presence = new DiscordRichPresence();
	private static RichPresence instance;
	
	public RichPresence() {
		instance = this;
	}
	
	public static RichPresence getInstance() {
		return instance;
	}
	
	@Override
	public void run() {
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		lib.Discord_Initialize(applicationId, handlers, true, null);
		presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
		String def = MineWarsClient.NAME + " " + MineWarsClient.VERSION;
		presence.details = def;
		//presence.smallImageKey = "logo";
		presence.largeImageKey = "logo";
		lib.Discord_UpdatePresence(presence);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				lib.Discord_Shutdown();
			}
		});
		ServerData current = null;
		while (!Thread.currentThread().isInterrupted()) {
			ServerData n = Minecraft.getMinecraft().getCurrentServerData();
			String text = def;
			if(n != null) {
				text = n.serverIP;
			}
			if(n != current) {
				updatePresence(text);
			}
			current = n;
			lib.Discord_RunCallbacks();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ignored) {
			}
		}
	}
	
	public void updatePresence(String text) {
		presence.details = text;
		presence.startTimestamp = System.currentTimeMillis() / 1000;
		lib.Discord_UpdatePresence(presence);
	}

}
