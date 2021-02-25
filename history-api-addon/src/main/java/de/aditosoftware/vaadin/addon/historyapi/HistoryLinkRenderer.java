package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.ui.renderers.AbstractRenderer;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.renderer.HistoryLinkRendererState;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryChangeServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeAdapter;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeOrigin;
import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

/**
 * Represents a renderer for a grid cell. The renderer is capable of showing a
 * anchor-tag which wraps a given text.
 */
public class HistoryLinkRenderer extends AbstractRenderer<Object, HistoryLinkRenderer.Data> implements HistoryChangeAdapter {
  private final HistoryAPI historyAPI;

  /**
   * Constructor for the renderer.
   */
  public HistoryLinkRenderer () {
    this(null);
  }

  /**
   * Constructor for the renderer.
   */
  public HistoryLinkRenderer (@Nullable HistoryAPI historyAPI) {
    super(HistoryLinkRenderer.Data.class, "");
    this.historyAPI = historyAPI;

    registerRpc(this::handleHistoryChangeEvent, HistoryChangeServerRpc.class);
  }

  @Override
  protected HistoryLinkRendererState getState () {
    return (HistoryLinkRendererState) super.getState();
  }

  @Override
  protected HistoryLinkRendererState getState (boolean markAsDirty) {
    return (HistoryLinkRendererState) super.getState(markAsDirty);
  }

  @Override
  public JsonValue encode (HistoryLinkRenderer.Data value) {
    if (value == null)
      // Delegate null handling to super-class.
      return super.encode(value);
    else {
      JsonObject object = Json.createObject();

      // If the text property is not null it shall be added to the object.
      if (value.getText() != null)
        object.put("text", value.getText());

      // If the uri property is not null it shall be added to the object.
      if (value.getURI() != null)
        object.put("uri", value.getURI().toString());

      return object;
    }
  }

  /**
   * Will handle an incoming {@link ClientHistoryChangeEvent}. This will create
   * a new {@link HistoryChangeEvent} and inform all listener. In addition it
   * will send it to the {@link HistoryAPI} if available.
   *
   * @param clientEvent The client event to handle.
   */
  private void handleHistoryChangeEvent (@NotNull ClientHistoryChangeEvent clientEvent) {
    HistoryChangeEvent event = new HistoryChangeEvent(this, URI.create(clientEvent.getURI()), null, null, HistoryChangeOrigin.ANCHOR);

    if (historyAPI != null)
      historyAPI.handleExternalHistoryChangeEvent(event);

    fireEvent(event);
  }

  /**
   * Will return if any click on the link shall open a new tab instead of pushing the link on the current tab.
   *
   * @return The state of the option.
   */
  public boolean isOpenNewTab() {
    return getState(false).openNewTab;
  }

  /**
   * Will set if any click on the link shall open a new tab instead of pushing the link on the current tab.
   *
   * @param pOpenNewTab The state of the option.
   */
  public void setOpenNewTab(boolean pOpenNewTab) {
    getState().openNewTab = pOpenNewTab;
  }

  /**
   * Represents a data class which holds the required data for the
   * {@link HistoryLinkRenderer}.
   */
  public static class Data {
    private final String text;
    private final URI uri;

    public Data (@Nullable String text, @Nullable URI uri) {
      this.text = text;
      this.uri = uri;
    }

    @Nullable
    public String getText () {
      return text;
    }

    @Nullable
    public URI getURI () {
      return uri;
    }
  }
}
