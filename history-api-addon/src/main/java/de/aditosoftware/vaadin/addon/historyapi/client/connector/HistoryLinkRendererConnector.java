package de.aditosoftware.vaadin.addon.historyapi.client.connector;

import com.vaadin.client.connectors.grid.AbstractGridRendererConnector;
import com.vaadin.shared.ui.Connect;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeOrigin;
import de.aditosoftware.vaadin.addon.historyapi.client.renderer.HistoryLinkRenderer;
import de.aditosoftware.vaadin.addon.historyapi.client.renderer.HistoryLinkRendererState;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryChangeServerRpc;
import elemental.json.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the connector
 */
@Connect(de.aditosoftware.vaadin.addon.historyapi.HistoryLinkRenderer.class)
public class HistoryLinkRendererConnector
    extends AbstractGridRendererConnector<JsonObject>
    implements HistoryChangeAwareConnector {
  @Override
  protected HistoryLinkRenderer createRenderer () {
    return new HistoryLinkRenderer(this::handleAnchorClick);
  }

  @Override
  public HistoryLinkRendererState getState () {
    return (HistoryLinkRendererState) super.getState();
  }

  private void handleAnchorClick (String uri) {
    handleHistoryChange(new ClientHistoryChangeEvent(uri, null, ClientHistoryChangeOrigin.ANCHOR));
  }

  @Override
  public @NotNull HistoryChangeServerRpc getHistoryChangeServerRpc () {
    return getRpcProxy(HistoryChangeServerRpc.class);
  }
}
