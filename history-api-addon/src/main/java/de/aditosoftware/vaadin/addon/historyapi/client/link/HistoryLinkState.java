package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.vaadin.shared.ui.AbstractSingleComponentContainerState;
import de.aditosoftware.vaadin.addon.historyapi.client.connector.HistoryLinkConnector;

/**
 * Describes the state for the {@link HistoryLinkConnector}.
 */
public class HistoryLinkState extends AbstractSingleComponentContainerState {
  public int tabIndex;
  public String uri;
}
