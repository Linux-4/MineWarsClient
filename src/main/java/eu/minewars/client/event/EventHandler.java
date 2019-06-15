package eu.minewars.client.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Annotation for Methods which should be handled by the {@link EventManager}
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

	/**
	 *
	 * @return The {@link EventPriority} of this Method
	 */
	EventPriority priority() default EventPriority.NORMAL;

}
