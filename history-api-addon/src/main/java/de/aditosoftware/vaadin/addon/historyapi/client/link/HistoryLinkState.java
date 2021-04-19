package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.vaadin.shared.ui.AbstractSingleComponentContainerState;
import de.aditosoftware.vaadin.addon.historyapi.client.connector.HistoryLinkConnector;

/**
 * Describes the state for the {@link HistoryLinkConnector}.
 */
public class HistoryLinkState extends AbstractSingleComponentContainerState {
    public int tabIndex;

    /**
     * Defines the actual URI which shall be opened by this link.
     */
    public String uri;

    /**
     * Defines if any click shall open a new tab instead of pushing the link on this tab.
     */
    public boolean openNewTab = false;

    /**
     * Defines if a local callback for click is set on the component. If the user clicks on the link, the callback
     * will internally be called instead of redirecting on the client-side. It's intended as a callback and not a
     * listener, because the callback may provide a return value.
     */
    public boolean hasClickCallback = false;
}
