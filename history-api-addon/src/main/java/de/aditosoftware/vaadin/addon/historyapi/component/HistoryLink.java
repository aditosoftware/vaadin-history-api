package de.aditosoftware.vaadin.addon.historyapi.component;

import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkState;
import de.aditosoftware.vaadin.addon.historyapi.client.link.rpc.HistoryLinkServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.event.LinkClickEvent;
import de.aditosoftware.vaadin.addon.historyapi.event.LinkClickListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

/**
 * Represents a link, which wraps the given component into an A-Tag.
 */
public class HistoryLink extends AbstractSingleComponentContainer implements Component.Focusable {
  public HistoryLink (Component component, URI uri) {
    setContent(component);
    setURI(uri);

    registerServerRPC();
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

  public Registration addLinkClickListener (@NotNull LinkClickListener linkClickListener) {
    return super
        .addListener(LinkClickEvent.class, linkClickListener,
            LinkClickListener.METHOD);
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

  private void registerServerRPC () {
    registerRpc(event -> fireEvent(new LinkClickEvent(this, URI.create(event.linkURI))), HistoryLinkServerRpc.class);
  }
}
