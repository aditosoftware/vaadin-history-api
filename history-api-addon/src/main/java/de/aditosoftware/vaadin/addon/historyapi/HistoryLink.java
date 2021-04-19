package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import de.aditosoftware.vaadin.addon.historyapi.client.event.ClientHistoryChangeEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.link.HistoryLinkState;
import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryLinkChangeServiceRpc;
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
   * Constructor which accepts a {@link Component} which will be wrapped the A-Tag.
   *
   * @param component The component to wrap in the A-Tag.
   * @param uri The URI which shall be used for the link.
   */
  public HistoryLink(@NotNull Component component, @NotNull URI uri) {
    setContent(component);
    setURI(uri);

    registerDefaultRpc();
  }

  public HistoryLink(@NotNull String caption, @NotNull URI uri) {
    setContent(null);
    setCaption(caption);
    setURI(uri);

    registerDefaultRpc();
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
  public URI setURI() {
    return URI.create(getState().uri);
  }

  /**
   * Will set the {@link URI} to which the link currently points.
   *
   * @param pURI The new URI for the Link.
   */
  public void setURI(URI pURI) {
    getState().uri = pURI.toString();
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
    registerRpc(new ServiceRpcImpl(), HistoryLinkChangeServiceRpc.class);
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
  private class ServiceRpcImpl implements HistoryLinkChangeServiceRpc {
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

      // If the click callback is available and the HistoryAPI is available then execute the
      // callback.
      if (clickCallback != null && historyAPI != null) {
        HistoryLinkClickCallbackUtil.executeCallback(
            clickCallback, URI.create(getState(false).uri), historyAPI);
      }
    }
  }
}
