package de.aditosoftware.vaadin.addon;

import com.google.gson.Gson;
import com.vaadin.server.AbstractExtension;
import com.vaadin.shared.Registration;
import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.client.event.PopStateEvent;
import de.aditosoftware.vaadin.addon.client.rpc.HistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.client.rpc.HistoryAPIServerRpc;
import de.aditosoftware.vaadin.addon.event.PopStateListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

public class HistoryAPIExtension extends AbstractExtension implements HistoryAPI {
    private transient Gson gson = new Gson();

    public HistoryAPIExtension(UI pUI) {
        extend(pUI);
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
    public void pushState(@Nullable Map<String, String> pState, @Nullable String pTitle, @NotNull String pURL) {
        getProxy().pushState(encodeMap(pState), pTitle, pURL);
    }

    @Override
    public void pushState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
        getProxy().pushState(encodeString(pState), pTitle, pURL);
    }

    @Override
    public void replaceState(@Nullable Map<String, String> pState, @Nullable String pTitle, @NotNull String pURL) {
        getProxy().replaceState(encodeMap(pState), pTitle, pURL);
    }

    @Override
    public void replaceState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
        getProxy().pushState(encodeString(pState), pTitle, pURL);
    }

    @NotNull
    @Override
    public Registration addPopStateListener(@NotNull PopStateListener popStateListener) {
        return super.addListener(de.aditosoftware.vaadin.addon.event.PopStateEvent.class, popStateListener, PopStateListener.METHOD);
    }

    /**
     * Will return the RPC proxy for the {@link HistoryAPIClientRpc}.
     *
     * @return The RPC proxy.
     */
    private HistoryAPIClientRpc getProxy() {
        return getRpcProxy(HistoryAPIClientRpc.class);
    }

    /**
     * Will encode the given key/value Map into an JSON string using GSON.
     *
     * @param pMap The map to encode.
     * @return The encoded map.
     */
    private String encodeMap(Map<String, String> pMap) {
        return gson.toJson(pMap);
    }

    /**
     * Will encoding the given string into an JSON string using GSON.
     *
     * @param pString The string to encode.
     * @return The encoded string.
     */
    private String encodeString(String pString) {
        return gson.toJson(pString);
    }
}
