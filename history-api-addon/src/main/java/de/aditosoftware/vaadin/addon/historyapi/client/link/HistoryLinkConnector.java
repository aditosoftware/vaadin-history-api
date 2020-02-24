package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.google.gwt.event.dom.client.ClickEvent;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.HistoryAPINativeAccessor;
import de.aditosoftware.vaadin.addon.historyapi.client.link.event.ClientLinkClickEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.link.rpc.HistoryLinkServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.component.HistoryLink;

@Connect(HistoryLink.class)
public class HistoryLinkConnector extends AbstractSingleComponentContainerConnector {
  @Override
  public HistoryLinkWidget getWidget () {
    return (HistoryLinkWidget) super.getWidget();
  }

  @Override
  public HistoryLinkState getState () {
    return (HistoryLinkState) super.getState();
  }

  @Override
  protected void init () {
    super.init();

    // Add a click handler to the widget.
    getWidget().addDomHandler(this::handleElementClick, ClickEvent.getType());
  }

  @Override
  public void onStateChanged (StateChangeEvent stateChangeEvent) {
    super.onStateChanged(stateChangeEvent);

    if (stateChangeEvent.hasPropertyChanged("uri")) {
      String newURI = getState().uri;
      if (newURI == null)
        getWidget().getElement().removeAttribute("href");
      else
        getWidget().getElement().setAttribute("href", newURI);
    }

    if (stateChangeEvent.hasPropertyChanged("tabIndex"))
      getWidget().getElement().setTabIndex(getState().tabIndex);
  }

  @Override
  public boolean delegateCaptionHandling () {
    return false;
  }

  @Override
  public void onConnectorHierarchyChange (ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
    getWidget().setWidget(getChildComponents().get(0).getWidget());
  }

  @Override
  public void updateCaption (ComponentConnector connector) {
  }

  /**
   * Will be called when the element of the widget has been clicked by the user.
   *
   * @param event The event of the click.
   */
  private void handleElementClick (ClickEvent event) {
    if (event.isMetaKeyDown()
        || event.isAltKeyDown()
        || event.isControlKeyDown()
        || event.isShiftKeyDown())
      return;

    event.preventDefault();

    HistoryAPINativeAccessor.pushState(null, null, getState().uri);
    getRpcProxy(HistoryLinkServerRpc.class).onLinkClick(createLinkClickEvent());

  }

  private ClientLinkClickEvent createLinkClickEvent () {
    return new ClientLinkClickEvent(getState().uri);
  }
}
