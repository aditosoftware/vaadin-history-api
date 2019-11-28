package de.aditosoftware.vaadin.addon.client.accessor;

import de.aditosoftware.vaadin.addon.client.event.PopStateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HistoryAPINativeAccessor {
    private List<Consumer<PopStateEvent>> popStateEventListener;

    public HistoryAPINativeAccessor() {
        popStateEventListener = new ArrayList<>();

        registerPopstateListener();
    }

    public void addPopStateEventListener(Consumer<PopStateEvent> pPopStateEventListener) {
        popStateEventListener.add(pPopStateEventListener);
    }

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
     * Will register a PopState Event on the current window. When a popstate event has been triggered it will call
     * {@link this#handleOnPopstate(PopStateEvent)}.
     */
    public native void registerPopstateListener() /*-{
        $wnd.addEventListener('popstate', function (ev) {
            var event = this.@HistoryAPINativeAccessor::createPopStateEvent(*)($doc.location, ev.state)
            this.@HistoryAPINativeAccessor::handleOnPopstate(*)(event);
        }.bind(this))
    }-*/;

    private void handleOnPopstate(PopStateEvent pEvent) {
        popStateEventListener.forEach(it -> it.accept(pEvent));
    }

    private PopStateEvent createPopStateEvent(String pUri, String pState) {
        return new PopStateEvent(pUri, pState);
    }
}

