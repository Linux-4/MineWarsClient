package eu.minewars.client.plugin;

import java.io.File;

public abstract class Plugin {

	private boolean enabled = false;
	private File dataFolder;
	private PluginDescription description;
	
	protected Plugin() {}
	
	@SuppressWarnings("unused")
	private Plugin(File dataFolder, PluginDescription description) {
		this.dataFolder = dataFolder;
		this.description = description;
	}
	
	public void enable() {
		enabled = true;
		this.onEnable();
	}
	
	public void disable() {
		enabled = false;
		this.onEnable();
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public abstract void onEnable();
	
	public abstract void onDisable();
	
	public PluginDescription getDescription() {
		return description;
	}
	
	public File getDataFolder() {
		return dataFolder;
	}
	
}
