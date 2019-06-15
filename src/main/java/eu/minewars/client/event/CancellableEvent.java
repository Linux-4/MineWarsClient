package eu.minewars.client.event;

public abstract class CancellableEvent extends Event implements Cancellable {

	private boolean cancel;
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

}
