package de.aditosoftware.vaadin.addon.historyapi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.server.AbstractExtension;
import com.vaadin.server.Extension;
import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIClientRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryChangeServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeAdapter;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeOrigin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the implements of {@link HistoryAPI} as Vaadin UI extension. This extension can only
 * be used on an {@link UI}. This is intended to mimic the actual HTMl5 History API. See
 * https://html.spec.whatwg.org/multipage/history.html#the-history-interface
 *
 * <p>You may use {@link HistoryAPI#forUI(UI)} to instantiate a new extension for the given UI.
 */
public class HistoryAPI extends AbstractExtension implements HistoryChangeAdapter {
  private static final transient Gson gson = new Gson();

  /**
   * Constructor for this extension. You may use {@link HistoryAPI#forUI(UI)} instead.
   *
   * @param pUI The UI on which this extension should be registered.
   */
  private HistoryAPI(@NotNull UI pUI) {
    extend(pUI);
    registerServerRpc();
  }

  /**
   * Will return a {@link HistoryAPI} instance of the given {@link UI}. If there is already an
   * instance registered on the UI, it will be reused.
   *
   * @param pUI The UI to extend.
   * @return The HistoryAPI instance.
   */
  public static HistoryAPI forUI(@NotNull UI pUI) {
    // Load the current extensions of the given UI.
    Collection<Extension> extensions = pUI.getExtensions();

    // If there are extensions available on the UI, we need to check if there is a matching one.
    if (extensions != null && !extensions.isEmpty()) {
      // Filter the extensions for a HistoryAPI.
      Optional<Extension> optionalExtension =
          extensions.stream().filter(it -> it instanceof HistoryAPI).findFirst();

      // If a HistoryAPI instance was found, then just return it.
      if (optionalExtension.isPresent() && optionalExtension.get() instanceof HistoryAPI) {
        return (HistoryAPI) optionalExtension.get();
      }
    }

    // As no existing HistoryAPI instance could be found, we need to create a new one here.
    return new HistoryAPI(pUI);
  }

  /**
   * Goes back or forward the specified number of steps in the history. If zero is given it will
   * stay on this page. If the delta is out of range of the history it will do nothing.
   *
   * @param pDelta The positive or negative delta.
   */
  public void go(int pDelta) {
    getProxy().go(pDelta);
  }

  /**
   * Will go back one step in the history. (Equals to {@link this#go(int)} when called with `-1`)
   */
  public void back() {
    getProxy().back();
  }

  /**
   * Will go forward one step in the history. (Equals to {@link this#go(int)} when called with `1`)
   */
  public void forward() {
    getProxy().forward();
  }

  /**
   * Pushes the given state, title and URL onto the history.
   *
   * @param pURL The URL.
   */
  public void pushState(@NotNull String pURL) {
    pushState(pURL, (String) null, null);
  }

  /**
   * Pushes the given state, title and URL onto the history.
   *
   * @param pURL The URL.
   * @param pState The state, which will be encoded using a JSON serializer.
   */
  public void pushState(@NotNull String pURL, @Nullable Map<String, String> pState) {
    pushState(pURL, pState, null);
  }

  /**
   * Pushes the given state, title and URL onto the history.
   *
   * @param pState The state, which will be encoded using a JSON serializer.
   * @param pTitle The title, which will mostly not picked up by most browsers.
   * @param pURL The URL.
   */
  public void pushState(
      @NotNull String pURL, @Nullable Map<String, String> pState, @Nullable String pTitle) {
    getProxy().pushState(encodeMap(pState), pTitle, pURL);
  }

  /**
   * Pushes the given state, title, and URL onto the history.
   *
   * @param pURL the URL.
   * @param pState The state.
   */
  public void pushState(@NotNull String pURL, @Nullable String pState) {
    pushState(pURL, pState, null);
  }

  /**
   * Pushes the given state, title, and URL onto the history.
   *
   * @param pState The state.
   * @param pTitle The title, which will mostly not picked up by most browser.
   * @param pURL the URL.
   */
  public void pushState(@NotNull String pURL, @Nullable String pState, @Nullable String pTitle) {
    getProxy().pushState(encodeString(pState), pTitle, pURL);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pURL The URL.
   */
  public void replaceState(@NotNull String pURL) {
    replaceState(pURL, (String) null, null);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pURL The URL.
   * @param pState The state, which will be encoded using a JSON serializer.
   */
  public void replaceState(@NotNull String pURL, @Nullable Map<String, String> pState) {
    replaceState(pURL, pState, null);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pState The state, which will be encoded using a JSON serializer.
   * @param pTitle The title, which will mostly not picked up by most browsers.
   * @param pURL The URL.
   */
  public void replaceState(
      @NotNull String pURL, @Nullable Map<String, String> pState, @Nullable String pTitle) {
    getProxy().replaceState(encodeMap(pState), pTitle, pURL);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pURL The URL.
   * @param pState The state.
   */
  public void replaceState(@NotNull String pURL, @Nullable String pState) {
    replaceState(pURL, pState, null);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pState The state.
   * @param pTitle The title, which will mostly not picked up by most browsers
   * @param pURL The URL.
   */
  public void replaceState(@NotNull String pURL, @Nullable String pState, @Nullable String pTitle) {
    getProxy().replaceState(encodeString(pState), pTitle, pURL);
  }

  /**
   * Will return the RPC proxy for the {@link HistoryAPIClientRpc}.
   *
   * @return The RPC proxy.
   */
  @NotNull
  private HistoryAPIClientRpc getProxy() {
    return getRpcProxy(HistoryAPIClientRpc.class);
  }

  /**
   * Will encode the given key/value Map into an JSON string using GSON.
   *
   * @param pMap The map to encode.
   * @return The encoded map.
   */
  @NotNull
  private String encodeMap(@NotNull Map<String, String> pMap) {
    return gson.toJson(pMap);
  }

  /**
   * Will encoding the given string into an JSON string using GSON.
   *
   * @param pString The string to encode.
   * @return The encoded string.
   */
  @NotNull
  private String encodeString(@NotNull String pString) {
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
   *     decoded.
   */
  @NotNull
  private HistoryChangeEvent createHistoryChangeEvent(@NotNull ClientHistoryChangeEvent event) {
    // Try to parse the given string URI.
    URI eventURI = URI.create(event.getURI());

    // Try to parse the state of the event into an string/string map using GSON.
    Map<String, String> stateMap = null;
    try {
      stateMap = gson.fromJson(event.getState(), buildStringStringMapType());
    } catch (Exception ignored) {
    }

    return new HistoryChangeEvent(
        this, eventURI, event.getState(), stateMap, HistoryChangeOrigin.POPSTATE);
  }

  /**
   * Will build a Type which represents an string/string map.
   *
   * @return The type.
   */
  @NotNull
  private static Type buildStringStringMapType() {
    return new TypeToken<Map<String, String>>() {}.getType();
  }

  /** Will register the server RPC, which will process the client-side PopState events. */
  private void registerServerRpc() {
    registerRpc(this::handleClientPopStateEvent, HistoryChangeServerRpc.class);
  }

  /**
   * Will handle incoming client PopState events. It will convert the client-side event into an
   * server-side event and trigger the event listeners.
   *
   * @param clientEvent The incoming client server-event.
   */
  private void handleClientPopStateEvent(@NotNull ClientHistoryChangeEvent clientEvent) {
    HistoryChangeEvent serverEvent = createHistoryChangeEvent(clientEvent);

    fireEvent(serverEvent);
  }

  /**
   * Will process a {@link HistoryChangeEvent} from an external source. This call normally
   * originates from {@link HistoryLink} or {@link HistoryLinkRenderer}.
   *
   * @param event The event to process.
   */
  void handleExternalHistoryChangeEvent(HistoryChangeEvent event) {
    fireEvent(event);
  }
}
