package de.aditosoftware.vaadin.addon.historyapi.client.accessor;

import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeOrigin;

import java.util.function.Consumer;

/**
 * Represents an accessor which uses native GWT code to access the HTML5 History API without the GWT
 * layer.
 */
public class HistoryAPINativeAccessor {

  public static native void pushState (String pState, String pTitle, String pURL) /*-{
    $wnd.history.pushState(JSON.parse(pState), pTitle, pURL);
  }-*/;

  public static native void replaceState (String pState, String pTitle, String pURL) /*-{
    $wnd.history.replaceState(JSON.parse(pState), pTitle, pURL);
  }-*/;

  public static native void go (int pDelta) /*-{
    $wnd.history.go(pDelta);
  }-*/;

  public static native void back () /*-{
    $wnd.history.back()
  }-*/;

  public static native void forward () /*-{
    $wnd.history.forward()
  }-*/;

  /**
   * Will register an PopState listener on the current window. The given
   * callback will be called when the PopState event has been triggered.
   *
   * @param listenerCallback The callback which receives the event.
   */
  public static native void registerPopStateListener (Consumer<ClientHistoryChangeEvent> listenerCallback) /*-{
    $wnd.addEventListener('popstate', function (ev) {
      @HistoryAPINativeAccessor::processEvent(*)(listenerCallback, $doc.location.href, null)
    }.bind(this))
  }-*/;

  /**
   * Will create a new PopStateEvent using the given URI and state.
   *
   * @param uri   The URI for which the event occurred.
   * @param state The state of the history.
   */
  private static void processEvent (Consumer<ClientHistoryChangeEvent> callback, String uri, String state) {
    callback.accept(new ClientHistoryChangeEvent(uri, state, ClientHistoryChangeOrigin.POPSTATE));
  }
}

