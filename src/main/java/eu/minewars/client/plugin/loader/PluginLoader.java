package eu.minewars.client.plugin.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.minewars.client.MineWarsClient;
import eu.minewars.client.plugin.Plugin;
import eu.minewars.client.plugin.PluginDescription;

public class PluginLoader {

	private File folder;
	private HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
	private static PluginLoader instance;
	private static final Logger LOGGER = LogManager.getLogger();

	public PluginLoader(File folder) {
		this.folder = folder;
		folder.mkdirs();
		loadPlugins();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				unloadPlugins();
			}
		});
	}
	
	public static void init(File folder) {
		PluginLoader.instance = new PluginLoader(folder);
	}
	
	public static PluginLoader getInstance() {
		return instance;
	}
	
	public static Collection<Plugin> getPlugins() {
		return instance.plugins.values();
	}
	
	public static Plugin getPlugin(String name) {
		return instance.plugins.get(name);
	}

	protected void loadPlugins() {
		LOGGER.info(MineWarsClient.PREFIX + "Loading plugins..");
		for (File f : folder.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".jar")) {
				loadPlugin(f);
			}
		}
	}

	protected void unloadPlugins() {
		LOGGER.info(MineWarsClient.PREFIX + "Disabling plugins..");
		for (Plugin p : plugins.values()) {
			p.disable();
			LOGGER.info("Disabled plugin " + p.getDescription().getName() + " v"
					+ p.getDescription().getVersion() + " by " + p.getDescription().getAuthor());
		}

		plugins.clear();
	}

	@SuppressWarnings({ "resource", "unchecked" })
	protected void loadPlugin(File f) {
		try {
			JarFile jar = new JarFile(f);
			ZipEntry desc = jar.getEntry("plugin.json");
			if (desc != null && !desc.isDirectory()) {
				PluginDescription pDesc = parseDescription(jar.getInputStream(desc));
				if (pDesc != null) {
					try {
						Class<?> main = new PluginClassLoader(new URL[] { f.toURI().toURL() })
								.loadPluginClass(pDesc.getMain());
						try {
							Class<? extends Plugin> pMain = (Class<? extends Plugin>) main;
							try {
								Constructor<? extends Plugin> constructor = pMain.getConstructor();
								constructor.setAccessible(true);
								new File(folder, pDesc.getName()).mkdirs();
								Plugin p = constructor.newInstance();
								Method init = Plugin.class.getDeclaredMethod("init", File.class, PluginDescription.class);
								init.setAccessible(true);
								init.invoke(p, new File(folder, pDesc.getName()), pDesc);
								LOGGER.info(MineWarsClient.PREFIX + "Loaded plugin "
										+ p.getDescription().getName() + " v" + p.getDescription().getVersion() + " by "
										+ p.getDescription().getAuthor());
								p.enable();
								plugins.put(pDesc.getName(), p);
								LOGGER.info(MineWarsClient.PREFIX + "Enabled plugin "
										+ p.getDescription().getName() + " v " + p.getDescription().getVersion()
										+ " by " + p.getDescription().getAuthor());
							} catch (NoSuchMethodException | SecurityException | InstantiationException
									| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								// Will never happen
								e.printStackTrace();
							}
						} catch (ClassCastException ex) {
							LOGGER.error(MineWarsClient.PREFIX + "Main class " + pDesc.getMain() + " of " + f
									+ " does not extend " + Plugin.class.getSimpleName());
						}
					} catch (ClassNotFoundException e) {
						LOGGER.error(
								MineWarsClient.PREFIX + "Could not find main class " + pDesc.getMain() + " in " + f);
					}
				} else {
					LOGGER.error(MineWarsClient.PREFIX + f + " contains invalid plugin.json!");
				}
			} else {
				LOGGER.error(MineWarsClient.PREFIX + f + " does not contain plugin.json!");
			}
		} catch (IOException e) {
			LOGGER.error(MineWarsClient.PREFIX + "Invalid JAR File: " + f);
		}
	}

	protected PluginDescription parseDescription(InputStream in) {
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		String text = "";
		String line;
		try {
			while ((line = r.readLine()) != null) {
				text = text + line;
			}
			JsonObject json = (JsonObject) new JsonParser().parse(text);
			return new PluginDescription(json.get("name").getAsString(), json.get("author").getAsString(),
					json.get("version").getAsString(), json.get("main").getAsString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
