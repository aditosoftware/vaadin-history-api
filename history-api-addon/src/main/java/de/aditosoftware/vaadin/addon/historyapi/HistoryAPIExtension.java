package de.aditosoftware.vaadin.addon.historyapi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.server.AbstractExtension;
import com.vaadin.shared.Registration;
import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.ClientPopStateEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.event.PopStateEvent;
import de.aditosoftware.vaadin.addon.historyapi.event.PopStateListener;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the implements of {@link HistoryAPI} as Vaadin UI extension. This extension can only
 * be used on an {@link UI}. You may use {@link HistoryAPI#forUI(UI)} to instantiate a new
 * extension.
 */
public class HistoryAPIExtension extends AbstractExtension implements HistoryAPI {

  private transient Gson gson = new Gson();

  /**
   * Constructor for this extension. You may use {@link HistoryAPI#forUI(UI)} instead.
   *
   * @param pUI The UI on which this extension should be registered.
   */
  public HistoryAPIExtension(UI pUI) {
    extend(pUI);
    registerServerRpc();
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
  public void pushState(@Nullable Map<String, String> pState, @Nullable String pTitle,
      @NotNull String pURL) {
    getProxy().pushState(encodeMap(pState), pTitle, pURL);
  }

  @Override
  public void pushState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
    getProxy().pushState(encodeString(pState), pTitle, pURL);
  }

  @Override
  public void replaceState(@Nullable Map<String, String> pState, @Nullable String pTitle,
      @NotNull String pURL) {
    getProxy().replaceState(encodeMap(pState), pTitle, pURL);
  }

  @Override
  public void replaceState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
    getProxy().pushState(encodeString(pState), pTitle, pURL);
  }

  @NotNull
  @Override
  public Registration addPopStateListener(@NotNull PopStateListener popStateListener) {
    return super
        .addListener(PopStateEvent.class, popStateListener,
            PopStateListener.METHOD);
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

  /**
   * Will create a new server-side PopState event from the given client-side PopState event. If the
   * given state in the client-side event can not be decoded using GSON it will not set the stateMap
   * property, but the state property.
   *
   * @param event The actual client-side PopState event.
   * @return The created server-side PopState event.
   * @throws IllegalArgumentException If any part the given client-side PopState event can not be
   *                                  decoded.
   */
  private PopStateEvent createServerPopStateEvent(@NotNull ClientPopStateEvent event) {
    // Try to parse the given string URI.
    URI eventURI = URI.create(event.getUri());

    // Try to parse the state of the event into an string/string map using GSON.
    Map<String, String> stateMap = null;
    try {
      stateMap = gson
          .fromJson(event.getState(), buildStringStringMapType());
    } catch (Exception ignored) {
    }

    return new PopStateEvent(this, eventURI, event.getState(), stateMap);
  }

  /**
   * Will build a Type which represents an string/string map.
   *
   * @return The type.
   */
  private static Type buildStringStringMapType() {
    return new TypeToken<Map<String, String>>() {
    }.getType();
  }

  /**
   * Will register the server RPC, which will process the client-side PopState events.
   */
  private void registerServerRpc() {
    registerRpc(this::handleClientPopStateEvent, HistoryAPIServerRpc.class);
  }

  /**
   * Will handle incoming client PopState events. It will convert the client-side event into an
   * server-side event and trigger the event listeners.
   *
   * @param clientEvent The incoming client server-event.
   */
  private void handleClientPopStateEvent(ClientPopStateEvent clientEvent) {
    PopStateEvent serverEvent = createServerPopStateEvent(clientEvent);

    fireEvent(serverEvent);
  }
}
