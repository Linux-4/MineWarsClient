package eu.minewars.client.event;

public interface Cancellable {
	/**
	 *
	 * @return True if Cancelled
	 */
	public boolean isCancelled();

	/**
	 *
	 * @param cancel Set Cancel status
	 */
	public void setCancelled(boolean cancel);

}
