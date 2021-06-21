package de.aditosoftware.vaadin.addon.historyapi.client.connector;

import com.vaadin.client.ServerConnector;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryChangeServerRpc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Describes a {@link ServerConnector} which can process a {@link ClientHistoryChangeEvent}. */
public interface HistoryChangeAwareConnector extends ServerConnector {
  /**
   * Will return the proxy implementation for the {@link HistoryChangeServerRpc}.
   *
   * @return The proxy implementation.
   */
  @Nullable
  HistoryChangeServerRpc getHistoryChangeServerRpc();

  /**
   * Will handle a {@link ClientHistoryChangeEvent}. This will check if a {@link
   * HistoryChangeServerRpc} is available, if so it will send it to the server-side through it.
   *
   * @param event The history change event to handle.
   */
  default void handleHistoryChange(@NotNull ClientHistoryChangeEvent event) {
    // Fetch the history change server rpc.
    HistoryChangeServerRpc historyChangeServerRpc = getHistoryChangeServerRpc();

    // If there is no HistoryAPIConnector given, the event will be piped
    // through the current rpc.
    if (historyChangeServerRpc != null) {
      getHistoryChangeServerRpc().onHistoryChange(event);
    }
  }
}
