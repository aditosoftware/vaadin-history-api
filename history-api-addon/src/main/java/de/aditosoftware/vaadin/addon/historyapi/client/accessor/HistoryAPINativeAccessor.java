package de.aditosoftware.vaadin.addon.historyapi.client.accessor;

import java.util.function.Consumer;

/**
 * Represents an accessor which uses native GWT code to access the HTML5 History API without the GWT
 * layer.
 */
public class HistoryAPINativeAccessor {

  public native void pushState(String pState, String pTitle, String pURL) /*-{
    $wnd.history.pushState(JSON.parse(pState), pTitle, pURL);
  }-*/;

  public native void replaceState(String pState, String pTitle, String pURL) /*-{
    $wnd.history.replaceState(JSON.parse(pState), pTitle, pURL);
  }-*/;

  public native void go(int pDelta) /*-{
    $wnd.history.go(pDelta);
  }-*/;

  public native void back() /*-{
    $wnd.history.back()
  }-*/;

  public native void forward() /*-{
    $wnd.history.forward()
  }-*/;

  /**
   * Will register an PopState listener on the current window. The given callback will be called
   * when the PopState event has been triggered.
   *
   * @param listenerCallback The callback which receives the event.
   */
  public native void registerPopStateListener(Consumer<ClientPopStateEvent> listenerCallback) /*-{
//    $wnd.addEventListener('popstate', function (ev) {
//      this.@HistoryAPINativeAccessor::createPopStateEvent(*)(listenerCallback, $doc.location.href,
//          ev.state)
//    }.bind(this))

    $wnd.addEventListener('hashchange', function (ev) {
      this.@HistoryAPINativeAccessor::createPopStateEvent(*)(listenerCallback, $doc.location.href, null)
    }.bind(this))
  }-*/;

  /**
   * Will create a new PopStateEvent using the given URI and state.
   *
   * @param pUri   The URI for which the event occurred.
   * @param pState The state of the history.
   * @return The PopState event.
   */
  private void createPopStateEvent(Consumer<ClientPopStateEvent> callback, String pUri,
      String pState) {
    ClientPopStateEvent event = new ClientPopStateEvent(pUri, pState);
    callback.accept(event);
  }
}

