package de.aditosoftware.vaadin.addon.historyapi;

import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeEvent;
import org.jetbrains.annotations.Nullable;

public interface HistoryAPIAware {
  /**
   * Will return an instance of the {@link HistoryAPI} or null if not available.
   *
   * @return The instance of the HistoryAPI.
   */
  @Nullable
  HistoryAPI getHistoryAPI ();

  default void handleHistoryChangeEvent (HistoryChangeEvent event) {
    HistoryAPI historyAPI = getHistoryAPI();

    if (historyAPI != null)
      historyAPI.handleExternalHistoryChangeEvent(event);
  }
}
