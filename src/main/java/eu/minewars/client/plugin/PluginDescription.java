package eu.minewars.client.plugin;

public class PluginDescription {

	private String name;
	private String author;
	private String version;
	private String main;
	
	public PluginDescription(String name, String author, String version, String main) {
		this.name = name;
		this.author = author;
		this.version = version;
		this.main = main;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getMain() {
		return main;
	}
	
}
