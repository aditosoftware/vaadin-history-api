package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;

public class HistoryLinkWidget extends SimplePanel {
  public HistoryLinkWidget () {
    super(DOM.createAnchor());

    setStylePrimaryName("v-history-link-wrapper");
  }
}
