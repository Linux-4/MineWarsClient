package eu.minewars.client.plugin;

import java.io.File;

import eu.minewars.client.event.EventManager;
import eu.minewars.client.plugin.gui.PluginGUI;

public abstract class Plugin {

	private boolean enabled = false;
	private File dataFolder;
	private PluginDescription description;
	
	public Plugin() {}
	
	@SuppressWarnings("unused")
	private final void init(File dataFolder, PluginDescription description) {
		this.dataFolder = dataFolder;
		this.description = description;
	}
	
	public void enable() {
		enabled = true;
		this.onEnable();
	}
	
	public void disable() {
		enabled = false;
		this.onDisable();
		EventManager.unregisterByPluginFile(
				new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()));
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
	
	public abstract PluginGUI getGUI();
	
}
