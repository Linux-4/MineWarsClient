package eu.minewars.client.event.server;

import eu.minewars.client.event.CancellableEvent;
import net.minecraft.client.multiplayer.ServerData;

public class JoinServerEvent extends CancellableEvent {

	private ServerData server;
	
	public JoinServerEvent(ServerData server) {
		this.server = server;
	}
	
	public ServerData getServer() {
		return server;
	}
	
	public void setServer(ServerData server) {
		this.server = server;
	}
	
}
