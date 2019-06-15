package eu.minewars.client.event.player;

import eu.minewars.client.event.CancellableEvent;
import net.minecraft.client.entity.AbstractClientPlayer;

public class PlayerDownloadCapeEvent extends CancellableEvent {
	
	private AbstractClientPlayer player;
	private String url;
	
	public PlayerDownloadCapeEvent(AbstractClientPlayer player, String url) {
		this.player = player;
		this.url = url;
	}
	
	public AbstractClientPlayer getPlayer() {
		return player;
	}
	
	public String getURL() {
		return url;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
}
