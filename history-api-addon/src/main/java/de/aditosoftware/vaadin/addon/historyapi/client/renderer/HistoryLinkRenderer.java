package de.aditosoftware.vaadin.addon.historyapi.client.renderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.renderers.WidgetRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import de.aditosoftware.vaadin.addon.historyapi.client.util.HistoryLinkUtil;
import elemental.json.JsonObject;

import java.util.function.Consumer;

public class HistoryLinkRenderer extends WidgetRenderer<JsonObject, SimplePanel> {
  private static final String WRAPPER_STYLE_NAME = "v-history-link-renderer-wrapper";
  private static final String PRIMARY_STYLE_NAME = "v-history-link-renderer";
  private static final String DATA_URI = "uri";
  private static final String DATA_TEXT = "text";
  private final Consumer<String> clickCallback;

  public HistoryLinkRenderer (Consumer<String> clickCallback) {
    this.clickCallback = clickCallback;
  }

  @Override
  public SimplePanel createWidget () {
    SimplePanel panel = new SimplePanel();
    panel.setStylePrimaryName(WRAPPER_STYLE_NAME);
    return panel;
  }

  @Override
  public void render (RendererCellReference cell, JsonObject data, SimplePanel widget) {
    if (!data.hasKey(DATA_URI) && data.hasKey(DATA_TEXT)) {
      widget.setWidget(null);
      // If there is no URI, but TEXT.
      widget.getElement().setInnerText(data.get(DATA_TEXT).asString());
    } else if (data.hasKey(DATA_URI) && data.hasKey(DATA_TEXT)) {
      Anchor anchor = new Anchor(data.get(DATA_TEXT).asString(), data.get(DATA_URI).asString());
      anchor.setStylePrimaryName(PRIMARY_STYLE_NAME);
      anchor.addClickHandler(event -> handleAnchorClick(anchor.getHref(), event));

      widget.setWidget(anchor);
    }
  }

  /**
   * Will handle a ClickEvent on a created anchor element.
   *
   * @param uri   The URI of the anchor.
   * @param event The click event.
   */
  private void handleAnchorClick (String uri, ClickEvent event) {
    if (HistoryLinkUtil.handleAnchorClick(uri, event))
      clickCallback.accept(uri);
  }
}
