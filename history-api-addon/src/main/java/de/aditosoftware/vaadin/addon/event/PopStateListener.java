package de.aditosoftware.vaadin.addon.event;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

public interface PopStateListener {
    Method METHOD = ReflectTools.findMethod(PopStateListener.class, "popState", PopStateEvent.class);

    void popState(PopStateEvent popStateEvent);
}
