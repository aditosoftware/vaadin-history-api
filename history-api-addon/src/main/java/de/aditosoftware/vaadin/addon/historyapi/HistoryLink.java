package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
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
  private final HistoryAPI historyAPI;
  private transient HistoryLinkClickCallback clickCallback;

  public HistoryLink(@NotNull Component component, @NotNull URI uri) {
    this(component, uri, null);
  }

  public HistoryLink(
      @NotNull Component component, @NotNull URI uri, @Nullable HistoryAPI historyAPI) {
    this.historyAPI = historyAPI;
    setContent(component);
    setURI(uri);

    registerRpc(new ServiceRpcImpl(), HistoryLinkChangeServiceRpc.class);
  }

  public HistoryLink(@NotNull String caption, @NotNull URI uri, @Nullable HistoryAPI historyAPI) {
    this.historyAPI = historyAPI;
    setContent(null);
    setURI(uri);
    setCaption(caption);

    registerRpc(new ServiceRpcImpl(), HistoryLinkChangeServiceRpc.class);
  }

  @Override
  protected HistoryLinkState getState() {
    return (HistoryLinkState) super.getState();
  }

  @Override
  protected HistoryLinkState getState(boolean markAsDirty) {
    return (HistoryLinkState) super.getState(markAsDirty);
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
   * Will set the callback which will be called when the user clicks on the link.
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

  private class ServiceRpcImpl implements HistoryLinkChangeServiceRpc {
    @Override
    public void onHistoryChange(ClientHistoryChangeEvent historyChangeEvent) {
      HistoryChangeEvent event =
          new HistoryChangeEvent(
              this,
              URI.create(historyChangeEvent.getURI()),
              null,
              null,
              HistoryChangeOrigin.ANCHOR);

      if (historyAPI != null) historyAPI.handleExternalHistoryChangeEvent(event);

      fireEvent(event);
    }

    @Override
    public void onClick() {
      if (clickCallback != null) {
        HistoryLinkClickCallbackUtil.executeCallback(
            clickCallback, URI.create(getState(false).uri), historyAPI);
      }
    }
  }
}
