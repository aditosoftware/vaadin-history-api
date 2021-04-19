package de.aditosoftware.vaadin.addon.historyapi.event;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

/** Describes the listener interface for a {@link HistoryChangeEvent}. */
public interface HistoryChangeListener {
  Method METHOD =
      ReflectTools.findMethod(
          HistoryChangeListener.class, "historyChange", HistoryChangeEvent.class);

  void historyChange(HistoryChangeEvent changeEvent);
}
