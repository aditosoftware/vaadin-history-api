package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;
import de.aditosoftware.vaadin.addon.historyapi.client.connector.HistoryLinkConnector;

/**
 * Describes the widget for the {@link HistoryLinkConnector}.
 */
public class HistoryLinkWidget extends SimplePanel {
  public HistoryLinkWidget () {
    super(DOM.createAnchor());

    // noinspection GWTStyleCheck
    setStylePrimaryName("v-history-link-wrapper");
  }
}
