package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkState;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryLinkChangeServerRpc;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeAdapter;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.event.HistoryChangeOrigin;
import de.aditosoftware.vaadin.addon.historyapi.util.HistoryLinkClickCallback;
import de.aditosoftware.vaadin.addon.historyapi.util.HistoryLinkClickCallbackUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

/** Represents a link, which wraps the given component into an A-Tag. */
public class HistoryLink extends AbstractSingleComponentContainer
    implements Component.Focusable, HistoryChangeAdapter {
  /**
   * Defines the callback, which will be called when the user clicks on the component. If it's null,
   * the default behavior of the link will be used. Use {@link
   * #setClickCallback(HistoryLinkClickCallback)} to update this property.
   */
  private transient HistoryLinkClickCallback clickCallback;

  /**
   * Constructor which accepts a {@link Component} which will be wrapped by an anchor tag.
   *
   * @param component Component to wrap in an anchor tag.
   * @param uri URI which shall be used for the link.
   */
  public HistoryLink(@NotNull Component component, @Nullable URI uri) {
    setContent(component);
    setURI(uri);

    registerDefaultRpc();
  }

  /**
   * Constructor which accepts a {@link String} as caption.
   *
   * @param caption Caption to display.
   * @param uri URI which shall be used for the link.
   */
  public HistoryLink(@NotNull String caption, @Nullable URI uri) {
    setContent(null);
    setCaption(caption);
    setURI(uri);

    registerDefaultRpc();
  }

  /**
   * Constructor which accepts a {@link Component} which will be wrapped by an anchor tag. This
   * constructor does not set an {@link URI} on the anchor tag.
   *
   * @param component Component to wrap in an anchor tag.
   */
  public HistoryLink(@NotNull Component component) {
    this(component, null);
  }

  /**
   * Constructor which accepts a {@link String} as caption. This constructor does not set an {@link
   * URI} on the anchor tag.
   *
   * @param caption Caption to display.
   */
  public HistoryLink(@NotNull String caption) {
    this(caption, null);
  }

  @Override
  protected HistoryLinkState getState() {
    return (HistoryLinkState) super.getState();
  }

  @Override
  protected HistoryLinkState getState(boolean markAsDirty) {
    return (HistoryLinkState) super.getState(markAsDirty);
  }

  @Override
  public void focus() {
    super.focus();
  }

  @Override
  public int getTabIndex() {
    return getState(false).tabIndex;
  }

  @Override
  public void setTabIndex(int tabIndex) {
    getState().tabIndex = tabIndex;
  }

  /**
   * Will return the {@link URI} to which the link currently points.
   *
   * @return The URI.
   */
  @Nullable
  public URI getURI() {
    final String current = getState().uri;

    // If there is currently not URI set, then just return null.
    if (current == null) {
      return null;
    }

    // Parse the String representation of the URI into an actual URI object.
    return URI.create(current);
  }

  /**
   * Will set the {@link URI} to which the link currently points. The URI may be null, if the anchor
   * shall not set an "href" attribute.
   *
   * @param uri The new URI for the Link.
   */
  public void setURI(@Nullable URI uri) {
    String newURI = null;

    // If the new URI is not null, then get the string representation and use it as new state.
    if (uri != null) {
      newURI = uri.toString();
    }

    // Update the state with the new URI string representation, this might be null.
    getState().uri = newURI;
  }

  /**
   * Will return if any click on the link shall open a new tab instead of pushing the link on the
   * current tab.
   *
   * @return The state of the option.
   */
  public boolean isOpenNewTab() {
    return getState(false).openNewTab;
  }

  /**
   * Will set if any click on the link shall open a new tab instead of pushing the link on the
   * current tab.
   *
   * @param pOpenNewTab The state of the option.
   */
  public void setOpenNewTab(boolean pOpenNewTab) {
    getState().openNewTab = pOpenNewTab;
  }

  /**
   * Will set the callback which will be called when the user clicks on the link. You may pass null
   * to reset the current click callback.
   *
   * @param pCallback The click callback.
   */
  public void setClickCallback(@Nullable HistoryLinkClickCallback pCallback) {
    clickCallback = pCallback;
    getState().hasClickCallback = clickCallback != null;
  }

  /**
   * Will return if there is a click callback set.
   *
   * @return If a click callback is set.
   */
  public boolean hasClickCallback() {
    return clickCallback != null;
  }

  /** Will register the server-side rpc for this component. */
  private void registerDefaultRpc() {
    registerRpc(new ServerRpcImpl(), HistoryLinkChangeServerRpc.class);
  }

  /**
   * Will return the {@link HistoryAPI} for the current {@link UI}. This may return null if the
   * component is not attached to any UI.
   *
   * @return The current HistoryAPI or null.
   */
  @Nullable
  private HistoryAPI getHistoryAPI() {
    UI ui = getUI();
    // If the component is currently not attached to an UI, just return null.
    if (ui == null) return null;

    return HistoryAPI.forUI(ui);
  }

  /** Implementation of the default server rpc for this component. */
  private class ServerRpcImpl implements HistoryLinkChangeServerRpc {
    @Override
    public void onHistoryChange(ClientHistoryChangeEvent historyChangeEvent) {
      // Build the server-side event based on the client-side event.
      HistoryChangeEvent event =
          new HistoryChangeEvent(
              this,
              URI.create(historyChangeEvent.getURI()),
              null,
              null,
              HistoryChangeOrigin.ANCHOR);

      // If the HistoryAPI is available, pass the server-side event to the HistoryAPI.
      HistoryAPI historyAPI = getHistoryAPI();
      if (historyAPI != null) historyAPI.handleExternalHistoryChangeEvent(event);

      // Fire the event on this component.
      fireEvent(event);
    }

    @Override
    public void onClick() {
      // Load the current HistoryAPI.
      HistoryAPI historyAPI = getHistoryAPI();

      // If the click callback is available, and the HistoryAPI is available then execute the
      // callback.
      if (clickCallback != null && historyAPI != null) {
        HistoryLinkClickCallbackUtil.executeCallback(
            clickCallback, URI.create(getState(false).uri), historyAPI);
      }
    }
  }
}
