package de.aditosoftware.vaadin.addon.historyapi.client.renderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.renderers.TextRenderer;
import com.vaadin.client.renderers.WidgetRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.HistoryAPINativeAccessor;
import elemental.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class HistoryLinkRenderer extends WidgetRenderer<JsonObject, Anchor> {
  private static final String PRIMARY_STYLE_NAME = "v-history-link-renderer";
  private static final String DATA_URI = "uri";
  private static final String DATA_TEXT = "text";
  private final Renderer<String> fallbackRenderer;
  private final Consumer<String> clickCallback;

  public HistoryLinkRenderer (Consumer<String> clickCallback) {
    this.clickCallback = clickCallback;
    fallbackRenderer = createFallbackRenderer();
  }

  @Override
  public Anchor createWidget () {
    Anchor anchor = new Anchor();
    anchor.setStylePrimaryName(PRIMARY_STYLE_NAME);
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

  /**
   * Will create a TextRenderer, which can be used as fallback to represent
   * just text.
   *
   * @return The created renderer.s
   */
  @NotNull
  private Renderer<String> createFallbackRenderer () {
    return new TextRenderer();
  }
}
