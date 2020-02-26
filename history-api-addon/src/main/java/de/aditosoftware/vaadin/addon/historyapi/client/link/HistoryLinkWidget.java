package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.ui.Icon;
import de.aditosoftware.vaadin.addon.historyapi.client.connector.HistoryLinkConnector;

/**
 * Describes the widget for the {@link HistoryLinkConnector}. This is just a
 * {@link SimplePanel} with an {@link Anchor} as wrapping element.
 */
public class HistoryLinkWidget extends SimplePanel {
  public Element captionElement = DOM.createSpan();
  public Icon icon;

  public HistoryLinkWidget () {
    super(DOM.createAnchor());

    // noinspection GWTStyleCheck
    setStylePrimaryName("v-history-link");
  }
}
