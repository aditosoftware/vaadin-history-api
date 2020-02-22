package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.server.Extension;
import com.vaadin.shared.Registration;
import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.historyapi.event.PopStateListener;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Describes all capabilities of the History API. This is intended to mimic the actual HTML5 History
 * API. See https://html.spec.whatwg.org/multipage/history.html#the-history-interface
 */
public interface HistoryAPI extends Extension {

  /**
   * Will create a new HistoryAPI instance for the given {@link UI}. The Extension will
   * automatically extend with the given UI.
   *
   * @param pUI The UI to extend.
   * @return The HistoryAPI instance.
   */
  static HistoryAPI forUI(@NotNull UI pUI) {
    return new HistoryAPIExtension(pUI);
  }

  /**
   * Goes back or forward the specified number of steps in the history. If zero is given it will
   * stay on this page. If the delta is out of range of the history it will do nothing.
   *
   * @param pDelta The positive or negative delta.
   */
  void go(int pDelta);

  /**
   * Will go back one step in the history. (Equals to {@link this#go(int)} when called with `-1`)
   */
  void back();

  /**
   * Will go forward one step in the history. (Equals to {@link this#go(int)} when called with `1`)
   */
  void forward();


  /**
   * Pushes the given state, title and URL onto the history.
   *
   * @param pURL The URL.
   */
  default void pushState(@NotNull String pURL) {
    pushState(pURL, (String) null, null);
  }

  /**
   * Pushes the given state, title and URL onto the history.
   *
   * @param pURL   The URL.
   * @param pState The state, which will be encoded using a JSON serializer.
   */
  default void pushState(@NotNull String pURL, @Nullable Map<String, String> pState) {
    pushState(pURL, pState, null);
  }

  /**
   * Pushes the given state, title and URL onto the history.
   *
   * @param pState The state, which will be encoded using a JSON serializer.
   * @param pTitle The title, which will mostly not picked up by most browsers.
   * @param pURL   The URL.
   */
  void pushState(@NotNull String pURL, @Nullable Map<String, String> pState,
      @Nullable String pTitle);

  /**
   * Pushes the given state, title, and URL onto the history.
   *
   * @param pURL   the URL.
   * @param pState The state.
   */
  default void pushState(@NotNull String pURL, @Nullable String pState) {
    pushState(pURL, pState, null);
  }

  /**
   * Pushes the given state, title, and URL onto the history.
   *
   * @param pState The state.
   * @param pTitle The title, which will mostly not picked up by most browser.
   * @param pURL   the URL.
   */
  void pushState(@NotNull String pURL, @Nullable String pState, @Nullable String pTitle);

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pURL The URL.
   */
  default void replaceState(@NotNull String pURL) {
    replaceState(pURL, (String) null, null);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pURL   The URL.
   * @param pState The state, which will be encoded using a JSON serializer.
   */
  default void replaceState(@NotNull String pURL, @Nullable Map<String, String> pState) {
    replaceState(pURL, pState, null);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pState The state, which will be encoded using a JSON serializer.
   * @param pTitle The title, which will mostly not picked up by most browsers.
   * @param pURL   The URL.
   */
  void replaceState(@NotNull String pURL, @Nullable Map<String, String> pState,
      @Nullable String pTitle);

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pURL   The URL.
   * @param pState The state.
   */
  default void replaceState(@NotNull String pURL, @Nullable String pState) {
    replaceState(pURL, pState, null);
  }

  /**
   * Will replace the current state with the given state, title and URL.
   *
   * @param pState The state.
   * @param pTitle The title, which will mostly not picked up by most browsers
   * @param pURL   The URL.
   */
  void replaceState(@NotNull String pURL, @Nullable String pState, @Nullable String pTitle);

  /**
   * Will register an listener which will be called when the history changes on the client-side.
   *
   * @param popStateListener The listener which will be executed.
   * @return The registration which can unregister the listener.
   */
  @NotNull
  Registration addPopStateListener(@NotNull PopStateListener popStateListener);
}
