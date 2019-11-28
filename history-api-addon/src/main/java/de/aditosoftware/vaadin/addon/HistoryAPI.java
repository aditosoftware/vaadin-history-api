package de.aditosoftware.vaadin.addon;

import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.client.event.PopStateEvent;

import java.util.function.Consumer;

/**
 * Describes the interface for the History.
 * See https://html.spec.whatwg.org/multipage/history.html#the-history-interface
 */
public interface HistoryAPI {
    /**
     * Will create a new HistoryAPI instance for the given {@link UI}. The Extension will automatically extend with the
     * given UI.
     *
     * @param pUI The UI to extend.
     * @return The HistoryAPI instance.
     */
    static HistoryAPI forUI(UI pUI) {
        return new HistoryAPIExtension(pUI);
    }

    /**
     * Goes back or forward the specified number of steps in the history.
     * If zero is given it will stay on this page.
     * If the delta is out of range of the history it will do nothing.
     *
     * @param pDelta The positiv or negative delta.
     */
    void go(int pDelta);

    /**
     * Will go back one step in the history.
     * (Equals to {@link this#go(int)} when called with `-1`)
     */
    void back();

    /**
     * Will go forward one step in the history.
     * (Equals to {@link this#go(int)} when called with `1`)
     */
    void forward();

    /**
     * Pushes the given state, title and URL onto the history.
     *
     * @param pState The state, which will be encoded using a JSON serializer.
     * @param pTitle The title, which will mostly not picked up by most browsers.
     * @param pURL   The URL.
     */
    void pushState(Object pState, String pTitle, String pURL);

    /**
     * Pushes the given state, title, and URL onto the history.
     *
     * @param pState The state.
     * @param pTitle The title, which will mostly not picked up by most browser.
     * @param pURL   the URL.
     */
    void pushState(String pState, String pTitle, String pURL);

    /**
     * Will replace the current state with the given state, title and URL.
     *
     * @param pState The state, which will be encoded using a JSON serializer.
     * @param pTitle The title, which will mostly not picked up by most browsers.
     * @param pURL   The URL.
     */
    void replaceState(Object pState, String pTitle, String pURL);

    /**
     * Will replace the current state with the given state, title and URL.
     *
     * @param pState The state.
     * @param pTitle The title, which will mostly not picked up by most browsers
     * @param pURL   The URL.
     */
    void replaceState(String pState, String pTitle, String pURL);

    /**
     * Will register an listener which will be called when the history changes on the client-side.
     *
     * @param pPopStateEvent The consumer for the popstate event.
     */
    void addPopStateListener(Consumer<PopStateEvent> pPopStateEvent);
}
