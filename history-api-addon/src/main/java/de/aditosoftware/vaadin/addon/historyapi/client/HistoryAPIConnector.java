package de.aditosoftware.vaadin.addon.historyapi.client;

import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.HistoryAPIExtension;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.ClientPopStateEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.DelegatingClientRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.HistoryAPINativeAccessor;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIServerRpc;

@Connect(HistoryAPIExtension.class)
public class HistoryAPIConnector extends AbstractExtensionConnector {

  private transient HistoryAPINativeAccessor nativeAccessor;

  public HistoryAPIConnector() {
    // Create one native accessor for the connector.
    nativeAccessor = new HistoryAPINativeAccessor();

    // Register the Client RPC.
    registerClientRpc(nativeAccessor);

    registerPopStateListener(nativeAccessor);

  }

  @Override
  protected void extend(ServerConnector target) {
    // Is not required as this is no component extension.
  }

  /**
   * Will register the client PRC which uses {@link DelegatingClientRpc} to delegate to the native
   * accessor for the HTML5 History API.
   *
   * @param pNativeAccessor Instance of the native accessor.
   */
  private void registerClientRpc(HistoryAPINativeAccessor pNativeAccessor) {
    registerRpc(HistoryAPIClientRpc.class, new DelegatingClientRpc(pNativeAccessor));
  }

  /**
   * Will send the given PopState event to the server.
   *
   * @param pClientPopStateEvent The PopState event.
   */
  private void sendPopStateEvent(ClientPopStateEvent pClientPopStateEvent) {
    getRpcProxy(HistoryAPIServerRpc.class).onPopState(pClientPopStateEvent);
  }

  /**
   * Will register a new PopState listener using the given native accessor.
   *
   * @param accessor The native accessor on which the listener should be registered.
   */
  private void registerPopStateListener(HistoryAPINativeAccessor accessor) {
    accessor.registerPopStateListener(this::sendPopStateEvent);
  }
}
