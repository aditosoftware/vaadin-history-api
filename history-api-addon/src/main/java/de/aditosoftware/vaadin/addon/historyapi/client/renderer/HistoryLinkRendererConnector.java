package de.aditosoftware.vaadin.addon.historyapi.client.renderer;

import com.vaadin.client.connectors.grid.AbstractGridRendererConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.client.link.event.ClientLinkClickEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.link.rpc.HistoryLinkServerRpc;
import elemental.json.JsonObject;

@Connect(de.aditosoftware.vaadin.addon.historyapi.renderer.HistoryLinkRenderer.class)
public class HistoryLinkRendererConnector extends AbstractGridRendererConnector<JsonObject> {
  @Override
  protected HistoryLinkRenderer createRenderer () {
    return new HistoryLinkRenderer(this::handleAnchorClick);
  }

  @Override
  public HistoryLinkRendererState getState () {
    return (HistoryLinkRendererState) super.getState();
  }

  private void handleAnchorClick (String uri) {
    this.getRpcProxy(HistoryLinkServerRpc.class).onLinkClick(new ClientLinkClickEvent(uri));
  }
}
