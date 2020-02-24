package de.aditosoftware.vaadin.addon.historyapi.renderer;

import com.vaadin.shared.Registration;
import com.vaadin.ui.renderers.AbstractRenderer;
import de.aditosoftware.vaadin.addon.historyapi.client.link.rpc.HistoryLinkServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.event.LinkClickEvent;
import de.aditosoftware.vaadin.addon.historyapi.event.LinkClickListener;
import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

/**
 * Represents a column renderer, which represents a link.
 */
public class HistoryLinkRenderer extends AbstractRenderer<Object, HistoryLinkData> {
  public HistoryLinkRenderer () {
    super(HistoryLinkData.class, "");

    registerServerRPC();
  }

  @Override
  public JsonValue encode (HistoryLinkData value) {
    if (value == null)
      return super.encode(value);
    else {
      JsonObject object = Json.createObject();

      object.put("text", value.getText());
      object.put("uri", value.getURI().toString());

      return object;
    }
  }

  public Registration addLinkClickListener (@NotNull LinkClickListener linkClickListener) {
    return super
        .addListener(LinkClickEvent.class, linkClickListener,
            LinkClickListener.METHOD);
  }

  private void registerServerRPC () {
    registerRpc(event -> fireEvent(new LinkClickEvent(this, URI.create(event.linkURI))), HistoryLinkServerRpc.class);
  }
}
