package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.HistoryLink;

@Connect(HistoryLink.class)
public class HistoryLinkConnector extends AbstractComponentConnector {
  @Override
  public HistoryLinkWidget getWidget () {
    return (HistoryLinkWidget) super.getWidget();
  }
}
