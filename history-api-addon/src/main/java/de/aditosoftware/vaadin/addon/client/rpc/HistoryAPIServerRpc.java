package de.aditosoftware.vaadin.addon.client.rpc;

import com.vaadin.shared.communication.ServerRpc;
import de.aditosoftware.vaadin.addon.client.event.PopStateEvent;

@FunctionalInterface
public interface HistoryAPIServerRpc extends ServerRpc {
    void onPopState(PopStateEvent pPopStateEvent);
}

