package eu.minewars.client.event;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * EventManager Class
 *
 */
public class EventManager {

	private static volatile List<EventListener> listeners = new ArrayList<EventListener>();

	private static void callEvent(HashMap<EventListener, Method> listeners, Event event) {
		for (final EventListener listener : listeners.keySet()) {
			try {
				listeners.get(listener).invoke(listener, event);
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			} catch (final IllegalArgumentException e) {
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Call a {@link Event}
	 *
	 * @param       <T> The {@link Event}
	 *
	 * @param event The {@link Event} to call
	 * @return The called {@link Event}
	 */
	public static <T extends Event> T callEvent(T event) {
		final HashMap<EventListener, Method> high = new HashMap<EventListener, Method>();
		final HashMap<EventListener, Method> normal = new HashMap<EventListener, Method>();
		final HashMap<EventListener, Method> low = new HashMap<EventListener, Method>();
		final HashMap<EventListener, Method> lowest = new HashMap<EventListener, Method>();
		for (final EventListener listener : EventManager.listeners) {
			for (final Method m : listener.getClass().getDeclaredMethods()) {
				if (m.isAnnotationPresent(EventHandler.class)) {
					if (m.getParameterTypes().length == 1) {
						if (m.getParameterTypes()[0].equals(event.getClass())) {
							m.setAccessible(true);
							final EventHandler annotation = m.getAnnotation(EventHandler.class);
							switch (annotation.priority()) {
							case HIGHEST:
								try {
									m.invoke(listener, event);
								} catch (final IllegalAccessException e) {
									e.printStackTrace();
								} catch (final InvocationTargetException e) {
									e.printStackTrace();
								}
								break;
							case HIGH:
								high.put(listener, m);
								break;
							case NORMAL:
								normal.put(listener, m);
								break;
							case LOW:
								low.put(listener, m);
								break;
							case LOWEST:
								lowest.put(listener, m);
								break;
							default:
								normal.put(listener, m);
								break;
							}
						}
					}
				}
			}

			EventManager.callEvent(high, event);
			EventManager.callEvent(normal, event);
			EventManager.callEvent(low, event);
			EventManager.callEvent(lowest, event);
		}

		return event;
	}

	/**
	 * Register a {@link EventListener}
	 *
	 * @param listener The {@link EventListener} to register
	 */
	public static void registerListener(EventListener listener) {
		EventManager.listeners.add(listener);
	}

	/**
	 * Unregister all listeners registered by the plugin loaded from given jar file
	 *
	 * @param file The plugin jar file
	 */
	public static void unregisterByPluginFile(File file) {
		for (final EventListener listener : EventManager.listeners) {
			try {
				if (listener.getClass().getProtectionDomain().getCodeSource().getLocation()
						.sameFile(file.toURI().toURL())) {
					EventManager.listeners.remove(listener);
				}
			} catch (final MalformedURLException e) {
				return;
			}
		}
	}

	/**
	 * Unregister a {@link EventListener}
	 *
	 * @param listener The {@link EventListener} to unregister
	 */
	public static void unregisterListener(EventListener listener) {
		if (EventManager.listeners.contains(listener)) {
			EventManager.listeners.remove(listener);
		}
	}

}
