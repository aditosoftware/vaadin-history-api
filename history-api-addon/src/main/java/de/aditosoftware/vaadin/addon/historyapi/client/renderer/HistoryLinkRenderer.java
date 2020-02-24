package de.aditosoftware.vaadin.addon.historyapi.client.renderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.vaadin.client.renderers.WidgetRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.HistoryAPINativeAccessor;
import elemental.json.JsonObject;

import java.util.function.Consumer;

public class HistoryLinkRenderer extends WidgetRenderer<JsonObject, Anchor> {
  private final Consumer<String> clickCallback;

  public HistoryLinkRenderer (Consumer<String> clickCallback) {
    this.clickCallback = clickCallback;
  }

  @Override
  public Anchor createWidget () {
    Anchor anchor = new Anchor();
    anchor.setStylePrimaryName("v-history-link-renderer");
    anchor.addClickHandler(event -> handleAnchorClick(anchor.getHref(), event));
    return anchor;
  }

  @Override
  public void render (RendererCellReference cell, JsonObject data, Anchor widget) {
    if (!data.hasKey("uri") || !data.hasKey("text")) {
      widget.setHref((String) null);
      widget.setText(null);
    } else {
      widget.setHref(data.get("uri").asString());
      widget.setText(data.get("text").asString());
    }
  }

  /**
   * Will handle a ClickEvent on a created anchor element.
   *
   * @param uri   The URI of the anchor.
   * @param event The click event.
   */
  private void handleAnchorClick (String uri, ClickEvent event) {
    if (event.isMetaKeyDown()
        || event.isAltKeyDown()
        || event.isControlKeyDown()
        || event.isShiftKeyDown())
      return;

    event.preventDefault();

    HistoryAPINativeAccessor.pushState(null, null, uri);
    clickCallback.accept(uri);
  }
}
