package de.aditosoftware.vaadin.addon.historyapi.client.link.rpc;

import com.vaadin.shared.communication.ServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.link.event.ClientLinkClickEvent;

public interface HistoryLinkServerRpc extends ServerRpc {
  void onLinkClick (ClientLinkClickEvent event);
}
