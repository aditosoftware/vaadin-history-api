package de.aditosoftware.vaadin.addon.historyapi.event;

import com.vaadin.util.ReflectTools;
import java.lang.reflect.Method;

/**
 * Describes an listener for {@link PopStateEvent}.
 */
public interface PopStateListener {

  Method METHOD = ReflectTools.findMethod(PopStateListener.class, "popState", PopStateEvent.class);

  void popState(PopStateEvent popStateEvent);
}