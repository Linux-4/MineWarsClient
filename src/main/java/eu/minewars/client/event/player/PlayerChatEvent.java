package eu.minewars.client.event.player;

import eu.minewars.client.event.CancellableEvent;
import net.minecraft.client.entity.EntityPlayerSP;

public class PlayerChatEvent extends CancellableEvent {

	private EntityPlayerSP player;
	private String message;
	
	public PlayerChatEvent(EntityPlayerSP player, String message) {
		this.player = player;
		this.message = message;
	}
	
	public EntityPlayerSP getPlayer() {
		return player;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
