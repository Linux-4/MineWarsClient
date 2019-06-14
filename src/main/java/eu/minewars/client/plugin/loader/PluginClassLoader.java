package eu.minewars.client.plugin.loader;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {

	public PluginClassLoader(URL[] url) {
		super(url);
	}

	public Class<?> loadPluginClass(String clazz) throws ClassNotFoundException {
		return Class.forName(clazz, true, this);
	}

}
