package de.aditosoftware.vaadin.addon;

import com.google.gson.Gson;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.HistoryAPI;
import de.aditosoftware.vaadin.addon.client.event.PopStateEvent;
import de.aditosoftware.vaadin.addon.client.rpc.HistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.client.rpc.HistoryAPIServerRpc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HistoryAPIExtension extends AbstractExtension implements HistoryAPI {
    private transient Gson gson = new Gson();
    private List<Consumer<PopStateEvent>> popStateListener;

    public HistoryAPIExtension(UI pUI) {
        extend(pUI);
        popStateListener = new ArrayList<>();

        registerRpc((PopStateEvent it) -> {
            this.popStateListener.forEach(i -> i.accept(it));
        }, HistoryAPIServerRpc.class);
    }

    @Override
    public void go(int pDelta) {
        getProxy().go(pDelta);
    }

    @Override
    public void back() {
        getProxy().back();
    }

    @Override
    public void forward() {
        getProxy().forward();
    }

    @Override
    public void pushState(Object pState, String pTitle, String pURL) {
        getProxy().pushState(encodedState(pState), pTitle, pURL);
    }

    @Override
    public void pushState(String pState, String pTitle, String pURL) {
        pushState((Object) pState, pTitle, pURL);
    }

    @Override
    public void replaceState(Object pState, String pTitle, String pURL) {
        getProxy().replaceState(encodedState(pState), pTitle, pURL);
    }

    @Override
    public void replaceState(String pState, String pTitle, String pURL) {
        replaceState((Object) pState, pTitle, pURL);
    }

    @Override
    public void addPopStateListener(Consumer<PopStateEvent> pPopStateEvent) {
        popStateListener.add(pPopStateEvent);
    }

    private HistoryAPIClientRpc getProxy() {
        return getRpcProxy(HistoryAPIClientRpc.class);
    }

    private String encodedState(Object pState) {
        return gson.toJson(pState);
    }
}
