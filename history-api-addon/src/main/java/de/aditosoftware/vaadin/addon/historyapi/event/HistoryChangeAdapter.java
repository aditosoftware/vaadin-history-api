package de.aditosoftware.vaadin.addon.historyapi.event;

import com.vaadin.event.MethodEventSource;
import com.vaadin.shared.Registration;
import org.jetbrains.annotations.NotNull;

public interface HistoryChangeAdapter extends MethodEventSource {
  @NotNull
  default Registration addHistoryChangeListener(
      @NotNull HistoryChangeListener historyChangeListener) {
    return addListener(
        HistoryChangeEvent.class, historyChangeListener, HistoryChangeListener.METHOD);
  }
}
