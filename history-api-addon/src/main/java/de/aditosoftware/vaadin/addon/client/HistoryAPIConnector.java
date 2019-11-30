package de.aditosoftware.vaadin.addon.client;

import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.client.accessor.DelegatingHistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.client.accessor.HistoryAPINativeAccessor;
import de.aditosoftware.vaadin.addon.client.event.PopStateEvent;
import de.aditosoftware.vaadin.addon.client.rpc.HistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.client.rpc.HistoryAPIServerRpc;
import de.aditosoftware.vaadin.addon.HistoryAPIExtension;

@Connect(HistoryAPIExtension.class)
public class HistoryAPIConnector extends AbstractExtensionConnector {
    private transient HistoryAPINativeAccessor nativeAccessor;

    public HistoryAPIConnector() {
        nativeAccessor = new HistoryAPINativeAccessor();
        nativeAccessor.addPopStateEventListener(this::handleNativePopStateEvent);

        // Register the Client RPC.
        registerClientRpc(nativeAccessor);
    }

    @Override
    protected void extend(ServerConnector target) {
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
    }

    @Override
    public HistoryAPIState getState() {
        return (HistoryAPIState) super.getState();
    }

    /**
     * Will register the ClientRPC, which fully delegates the implementation to the native history API accessor.
     *
     * @param pNativeAccessor Instance of the native accessor.
     */
    private void registerClientRpc(HistoryAPINativeAccessor pNativeAccessor) {
        registerRpc(HistoryAPIClientRpc.class, new DelegatingHistoryAPIClientRpc(pNativeAccessor));
    }

    private void handleNativePopStateEvent(PopStateEvent pPopStateEvent) {
        getRpcProxy(HistoryAPIServerRpc.class).onPopState(pPopStateEvent);
    }
}
