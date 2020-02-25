package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkState;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryChangeServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeAdapter;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeOrigin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

/**
 * Represents a link, which wraps the given component into an A-Tag.
 */
public class HistoryLink extends AbstractSingleComponentContainer implements Component.Focusable, HistoryChangeAdapter {
  private final HistoryAPI historyAPI;

  public HistoryLink (@NotNull Component component, @NotNull URI uri) {
    this(component, uri, null);
  }

  public HistoryLink (@NotNull Component component, @NotNull URI uri, @Nullable HistoryAPI historyAPI) {
    this.historyAPI = historyAPI;
    setContent(component);
    setURI(uri);

    registerRpc(this::handleHistoryChangeEvent, HistoryChangeServerRpc.class);
  }

  public HistoryLink (@NotNull String caption, @NotNull URI uri, @Nullable HistoryAPI historyAPI) {
    this.historyAPI = historyAPI;
    setContent(null);
    setURI(uri);
    setCaption(caption);

    registerRpc(this::handleHistoryChangeEvent, HistoryChangeServerRpc.class);
  }

  @Override
  protected HistoryLinkState getState () {
    return (HistoryLinkState) super.getState();
  }

  @Override
  protected HistoryLinkState getState (boolean markAsDirty) {
    return (HistoryLinkState) super.getState(markAsDirty);
  }

  /**
   * Will return the {@link URI} to which the link currently points.
   *
   * @return The URI.
   */
  @Nullable
  public URI setURI () {
    return URI.create(getState().uri);
  }

  /**
   * Will set the {@link URI} to which the link currently points.
   *
   * @param pURI The new URI for the Link.
   */
  public void setURI (URI pURI) {
    getState().uri = pURI.toString();
  }

  @Override
  public void focus () {
    super.focus();
  }

  @Override
  public int getTabIndex () {
    return getState(false).tabIndex;
  }

  @Override
  public void setTabIndex (int tabIndex) {
    getState().tabIndex = tabIndex;
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
}
