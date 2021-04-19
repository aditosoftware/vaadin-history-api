package de.aditosoftware.vaadin.addon.historyapi.client.connector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.VCaption;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.client.ui.Icon;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.HistoryLink;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeOrigin;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkState;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkWidget;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryLinkChangeServiceRpc;
import de.aditosoftware.vaadin.addon.historyapi.client.util.HistoryLinkUtil;
import org.jetbrains.annotations.NotNull;

/** Represents the connector for the {@link HistoryLink} component. */
@Connect(HistoryLink.class)
public class HistoryLinkConnector extends AbstractSingleComponentContainerConnector
    implements HistoryChangeAwareConnector {
  @Override
  public HistoryLinkWidget getWidget() {
    return (HistoryLinkWidget) super.getWidget();
  }

  @Override
  public HistoryLinkState getState() {
    return (HistoryLinkState) super.getState();
  }

  @Override
  protected void init() {
    super.init();

    // Add a click handler to the widget.
    getWidget().addDomHandler(this::handleElementClick, ClickEvent.getType());
  }

  @Override
  public void onStateChanged(StateChangeEvent stateChangeEvent) {
    super.onStateChanged(stateChangeEvent);

    if (stateChangeEvent.hasPropertyChanged("uri"))
      if (getState().uri == null) {
        getWidget().getElement().removeAttribute("href");
      } else {
        getWidget().getElement().setAttribute("href", getState().uri);
      }

    if (stateChangeEvent.hasPropertyChanged("openNewTab")) {
      if (getState().openNewTab) getWidget().getElement().setAttribute("target", "_blank");
      else getWidget().getElement().setAttribute("target", "");
    }

    if (stateChangeEvent.hasPropertyChanged("tabIndex"))
      getWidget().getElement().setTabIndex(getState().tabIndex);

    // Update the caption element.
    if (stateChangeEvent.hasPropertyChanged("caption")
        || stateChangeEvent.hasPropertyChanged("captionAsHtml"))
      VCaption.setCaptionText(getWidget().captionElement, getState());

    // Set the icon widget to null if it's set.
    if (getWidget().icon != null) getWidget().icon = null;

    // Set the icon widget if there is a icon on the resources.
    Icon icon = getIcon();
    if (icon != null) getWidget().icon = icon;

    // Update the layout.
    updateLayout();
  }

  @Override
  public boolean delegateCaptionHandling() {
    return false;
  }

  @Override
  public void onConnectorHierarchyChange(
      ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
    updateLayout();
  }

  @Override
  public void updateCaption(ComponentConnector connector) {}

  @Override
  public @NotNull HistoryLinkChangeServiceRpc getHistoryChangeServerRpc() {
    return getRpcProxy(HistoryLinkChangeServiceRpc.class);
  }

  private void updateLayout() {
    if (getChildComponents().isEmpty()) {
      getWidget().setWidget(null);
      getWidget().getElement().removeAllChildren();

      // Attach the caption element.
      getWidget().getElement().appendChild(getWidget().captionElement);

      // Attach icon if available.
      if (getWidget().icon != null) {
        if (!getWidget().getElement().isOrHasChild(getWidget().icon.getElement()))
          getWidget()
              .getElement()
              .insertBefore(getWidget().icon.getElement(), getWidget().captionElement);
      } else {
        getWidget().getElement().removeChild(getWidget().icon.getElement());
      }
    } else {
      // If there is a children component it will be set as widget of the panel.
      getWidget().setWidget(getChildComponents().get(0).getWidget());
    }
  }

  /**
   * Will be called when the element of the widget has been clicked by the user.
   *
   * @param event The event of the click.
   */
  private void handleElementClick(ClickEvent event) {
    if (!getState().openNewTab && HistoryLinkUtil.handleAnchorClick(getState().uri, event)) {
      if (getState().hasClickCallback) {
        getHistoryChangeServerRpc().onClick();
      } else {
        handleHistoryChange(
            new ClientHistoryChangeEvent(getState().uri, null, ClientHistoryChangeOrigin.ANCHOR));
      }
    }
  }
}
