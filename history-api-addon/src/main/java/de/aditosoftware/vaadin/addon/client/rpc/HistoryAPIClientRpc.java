package de.aditosoftware.vaadin.addon.client.rpc;

import com.vaadin.shared.communication.ClientRpc;

public interface HistoryAPIClientRpc extends ClientRpc {
    void go(int pDelta);

    void back();

    void forward();

    void pushState(String pState, String pTitle, String pURL);

    void replaceState(String pState, String pTitle, String pURL);
}
