package eu.minewars.client.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import eu.minewars.client.MineWarsClient;

public class RichPresence extends Thread {

	@Override
	public void run() {
		DiscordRPC lib = DiscordRPC.INSTANCE;
		String applicationId = "589785332329742337";
		String steamId = null;
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		handlers.ready = (user) -> System.out.println("Ready!");
		lib.Discord_Initialize(applicationId, handlers, true, steamId);
		DiscordRichPresence presence = new DiscordRichPresence();
		presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
		presence.details = MineWarsClient.NAME;
		presence.smallImageKey = "logo";
		presence.largeImageKey = "logo";
		lib.Discord_UpdatePresence(presence);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				lib.Discord_Shutdown();
			}
		});
		while (!Thread.currentThread().isInterrupted()) {
			lib.Discord_RunCallbacks();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ignored) {
			}
		}
	}

}
