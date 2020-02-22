package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkConstants;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkState;

public class HistoryLink extends AbstractComponent {
  public HistoryLink (String pCaption, Resource pResource) {
    setCaption(pCaption);
    setResource(pResource);
  }

  @Override
  protected HistoryLinkState getState () {
    return (HistoryLinkState) super.getState();
  }

  @Override
  protected HistoryLinkState getState (boolean markAsDirty) {
    return (HistoryLinkState) super.getState(markAsDirty);
  }

  public void setResource (Resource pResource) {
    setResource(HistoryLinkConstants.HREF_RESOURCE, pResource);
  }

  public String getCaption () {
    return getState(false).caption;
  }

  public void setCaption (String pCaption) {
    getState().caption = pCaption;
  }
}
