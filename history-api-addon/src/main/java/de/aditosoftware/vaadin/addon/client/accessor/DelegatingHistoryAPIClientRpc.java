package de.aditosoftware.vaadin.addon.client.accessor;

import de.aditosoftware.vaadin.addon.client.rpc.HistoryAPIClientRpc;

public class DelegatingHistoryAPIClientRpc implements HistoryAPIClientRpc {
    private final HistoryAPINativeAccessor accessor;

    public DelegatingHistoryAPIClientRpc(HistoryAPINativeAccessor pAccessor) {
        accessor = pAccessor;
    }

    @Override
    public void go(int pDelta) {
        accessor.go(pDelta);
    }

    @Override
    public void back() {
        accessor.back();
    }

    @Override
    public void forward() {
        accessor.forward();
    }

    @Override
    public void pushState(String pState, String pTitle, String pURL) {
        accessor.pushState(pState, pTitle, pURL);
    }

    @Override
    public void replaceState(String pState, String pTitle, String pURL) {
        accessor.replaceState(pState, pTitle, pURL);
    }
}
