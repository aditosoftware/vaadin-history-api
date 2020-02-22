package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkConstants;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkWrapperState;

public class HistoryLinkWrapper extends AbstractSingleComponentContainer implements Component.Focusable {
  public HistoryLinkWrapper (Resource pResource, Component pComponent, HistoryAPI pHistoryAPI) {
    setResource(pResource);
    setContent(pComponent);
    getState().historyApiConnectorID = pHistoryAPI.getConnectorId();
  }

  @Override
  protected HistoryLinkWrapperState getState () {
    return (HistoryLinkWrapperState) super.getState();
  }

  @Override
  protected HistoryLinkWrapperState getState (boolean markAsDirty) {
    return (HistoryLinkWrapperState) super.getState(markAsDirty);
  }

  public void setResource (Resource pResource) {
    setResource(HistoryLinkConstants.HREF_RESOURCE, pResource);
  }

  @Override
  public void focus () {
    super.focus();
  }

  @Override
  public int getTabIndex () {
    return getState().tabIndex;
  }

  @Override
  public void setTabIndex (int tabIndex) {
    getState().tabIndex = tabIndex;
  }
}
