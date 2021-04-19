package de.aditosoftware.vaadin.addon.historyapi.client.connector;

import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.HistoryAPI;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.DelegatingClientRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.HistoryAPINativeAccessor;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryChangeServerRpc;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the connector for the {@link HistoryAPI} extension. This handles the server-to-client
 * communication for the History API commands.
 */
@Connect(HistoryAPI.class)
public class HistoryAPIConnector extends AbstractExtensionConnector
    implements HistoryChangeAwareConnector {
  public HistoryAPIConnector() {
    // Register the client rpc.
    registerClientRpc();

    // Register the PopState listener.
    registerPopStateListener();
  }

  @Override
  protected void extend(ServerConnector target) {
    // Is not required as this is no component extension.
  }

  @Override
  public @Nullable HistoryChangeServerRpc getHistoryChangeServerRpc() {
    return getRpcProxy(HistoryChangeServerRpc.class);
  }

  /**
   * Will register the client PRC which uses {@link DelegatingClientRpc} to delegate to the native
   * accessor for the HTML5 History API.
   */
  private void registerClientRpc() {
    registerRpc(HistoryAPIClientRpc.class, new DelegatingClientRpc());
  }

  /** Will register a new PopState listener on the {@link HistoryAPINativeAccessor}. */
  private void registerPopStateListener() {
    HistoryAPINativeAccessor.registerPopStateListener(this::handleHistoryChange);
  }
}
