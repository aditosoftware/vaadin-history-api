package de.aditosoftware.vaadin.addon.historyapi.client.rpc;

import com.vaadin.shared.communication.ServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;

/**
 * Describes a client-to-server rpc which is capable to sending a
 * {@link ClientHistoryChangeEvent} from the client to the server.
 */
public interface HistoryChangeServerRpc extends ServerRpc {
  /**
   * Will trigger a HistoryChang event on the server-side using the given
   * client-side event.
   *
   * @param historyChangeEvent The history change event.
   */
  void onHistoryChange (ClientHistoryChangeEvent historyChangeEvent);
}
