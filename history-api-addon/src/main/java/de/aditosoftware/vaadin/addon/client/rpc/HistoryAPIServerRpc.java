package de.aditosoftware.vaadin.addon.client.rpc;

import com.vaadin.shared.communication.ServerRpc;
import de.aditosoftware.vaadin.addon.client.event.PopStateEvent;

/**
 * Describes the client-to-server communication.
 */
public interface HistoryAPIServerRpc extends ServerRpc {
    /**
     * Will trigger a PopState event on the server-side using the given PopStateEvent.
     *
     * @param pPopStateEvent The event payload.
     */
    void onPopState(PopStateEvent pPopStateEvent);
}

