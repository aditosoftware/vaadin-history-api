package de.aditosoftware.vaadin.addon.historyapi.client.renderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.renderers.WidgetRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import de.aditosoftware.vaadin.addon.historyapi.client.util.HistoryLinkUtil;
import elemental.json.JsonObject;

import java.util.Objects;
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
      // There is only text but no link. Clear widget and set inner text.

      // Load the current data of the text.
      String dataText = data.get(DATA_TEXT).asString();

      // Clear the current widget if there is any.
      if (widget.getWidget() != null) {
        widget.setWidget(null);
      }

      // Update the inner text of the widget element.
      widget.getElement().setInnerText(dataText);
    } else if (data.hasKey(DATA_URI) && data.hasKey(DATA_TEXT)) {
      // There is text and a link. Create or incrementally update the widget.

      // Load the data.
      String dataText = data.get(DATA_TEXT).asString();
      String dataURI = data.get(DATA_URI).asString();

      // Load the existing widget if it exists.
      Widget existing = widget.getWidget();
      if (existing instanceof Anchor) {
        // There is an existing Anchor Widget, therefore we can incrementally
        // update the widget.
        Anchor anchor = (Anchor) existing;

        // Check for equality and update the data if needed.
        if (!Objects.equals(dataText, anchor.getText()))
          anchor.setText(data.get(DATA_TEXT).asString());

        if (!Objects.equals(dataURI, anchor.getHref()))
          anchor.setHref(data.get(DATA_URI).asString());
      } else {
        // Either there is no widget or the current widget is no Anchor,
        // therefore create a new Anchor Widget, initialize it and set it as widget.
        Anchor anchor = new Anchor(dataText, dataURI);
        anchor.setStylePrimaryName(PRIMARY_STYLE_NAME);
        anchor.addClickHandler(event -> handleAnchorClick(anchor.getHref(), event));
        widget.setWidget(anchor);
      }
    } else {
      // What?
      widget.setWidget(null);
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
