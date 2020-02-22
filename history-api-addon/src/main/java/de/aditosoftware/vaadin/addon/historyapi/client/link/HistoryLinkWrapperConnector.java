package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.HistoryLinkWrapper;

import java.util.logging.Level;
import java.util.logging.Logger;

@Connect(HistoryLinkWrapper.class)
public class HistoryLinkWrapperConnector extends AbstractSingleComponentContainerConnector {
  @Override
  public void onStateChanged (StateChangeEvent stateChangeEvent) {
    super.onStateChanged(stateChangeEvent);

    // The link has been saved as resource to the state.
    if (stateChangeEvent.hasPropertyChanged("resource")) {
      String href = getResourceUrl(HistoryLinkConstants.HREF_RESOURCE);
      if (href == null)
        getWidget().getElement().removeAttribute("href");
      else
        getWidget().getElement().setAttribute("href", href);
    }

    if (stateChangeEvent.hasPropertyChanged("tabIndex"))
      getWidget().getElement().setTabIndex(getState().tabIndex);

    Logger.getGlobal().log(Level.INFO, "Loading wrapper");
    Logger.getGlobal().log(Level.INFO, getState().historyApiConnectorID);
  }

  @Override
  public HistoryLinkWrapperWidget getWidget () {
    return (HistoryLinkWrapperWidget) super.getWidget();
  }

  @Override
  public HistoryLinkWrapperState getState () {
    return (HistoryLinkWrapperState) super.getState();
  }

  @Override
  public boolean delegateCaptionHandling () {
    return true;
  }

  @Override
  public void onConnectorHierarchyChange (ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
    getWidget().setWidget(getChildComponents().get(0).getWidget());
  }

  @Override
  public void updateCaption (ComponentConnector connector) {

  }
}
