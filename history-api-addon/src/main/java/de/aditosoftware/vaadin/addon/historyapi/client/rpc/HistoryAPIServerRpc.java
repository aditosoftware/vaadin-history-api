package de.aditosoftware.vaadin.addon.historyapi.client.rpc;

import com.vaadin.shared.communication.ServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.ClientPopStateEvent;

/**
 * Describes the client-to-server communication.
 */
public interface HistoryAPIServerRpc extends ServerRpc {

  /**
   * Will trigger a PopState event on the server-side using the given PopStateEvent.
   *
   * @param pClientPopStateEvent The event payload.
   */
  void onPopState(ClientPopStateEvent pClientPopStateEvent);
}

